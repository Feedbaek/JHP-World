package minskim2.JHP_World.global.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minskim2.JHP_World.config.properties.GithubProperties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j(topic = "GitHubFileUtil")
@Component
@RequiredArgsConstructor
public class GitHubFileUtil implements FileUtil {

    private final GithubProperties properties;

    private final RestClient restClient = RestClient.builder()
            .baseUrl("https://github.com")  // base URL 설정
            .build();

    // cookie user_session, __Host-user_session_same_site
    private String userSession;
    private String hostUserSessionSameSite;


    /**
     * GitHub 로그인
     * */
    public void loginGitHub() {
        // HTML 파싱해서 authenticity_token 가져오기
        ResponseEntity<String> respHtml = restClient.get().uri("/login")
                .retrieve()
                .toEntity(String.class);

        String html = respHtml.getBody();
        String authenticityToken = getAuthenticityToken(html, "input[name=authenticity_token]");

        // multipart/form-data로 전송할 form-data 생성
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("login", properties.getId());
        formData.add("password", properties.getPassword());
        formData.add("authenticity_token", authenticityToken);
        List<String> cookies = respHtml.getHeaders().get("Set-Cookie");

        // 로그인 요청
        ResponseEntity<Void> res = restClient.post().uri("/session")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .header(HttpHeaders.COOKIE, (cookies == null) ? "" : String.join("; ", cookies))
                .body(formData)
                .retrieve()
                .toBodilessEntity();

        // 쿠키 저장
        cookies = res.getHeaders().get("Set-Cookie");
        if (cookies == null) {
            throw new RuntimeException("로그인에 실패했습니다.");
        }

        // 쿠키 중 user_session, __Host-user_session_same_site 저장
        cookies.forEach(cookie -> {
            if (cookie.startsWith("user_session")) {
                int endIdx = cookie.indexOf(";");  // user_session=...; 형태에서 ; 인덱스
                userSession = cookie.substring(13, endIdx);  // user_session= 부터 ; 전까지
            } else if (cookie.startsWith("__Host-user_session_same_site")) {
                int endIdx = cookie.indexOf(";");  // __Host-user_session_same_site=...; 형태에서 ; 인덱스
                hostUserSessionSameSite = cookie.substring(30, endIdx);  // __Host-user_session_same_site= 부터 ; 전까지
            }
        });
        log.debug("깃허브 로그인 성공");
    }

    /**
     * GitHub 에 파일 업로드
     * */
    public String uploadGitHub(MultipartFile file, String contentType) throws IOException {
        // PR 페이지에서 authenticity_token 가져오기
        String html = restClient.get().uri(properties.getRepository() + "/pull/1")
                .header(HttpHeaders.COOKIE,
                        "user_session=" + userSession + "; __Host-user_session_same_site=" + hostUserSessionSameSite)
                .retrieve()
                .body(String.class);

        String authenticityToken = getAuthenticityToken(html, "input[class=js-data-upload-policy-url-csrf]");
        String repositoryId = getRepositoryId(html, "file-attachment[class=js-upload-markdown-image is-default]");

        // 파일 업로드 정보 가져오기
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("name", file.getOriginalFilename());
        formData.add("size", String.valueOf(file.getSize()));
        formData.add("content_type", contentType);
        formData.add("authenticity_token", authenticityToken);
        formData.add("repository_id", repositoryId);

        Map<String, Object> jsonMap = (Map<String, Object>) restClient.post().uri("/upload/policies/assets")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .header(HttpHeaders.COOKIE,
                        "user_session=" + userSession + "; __Host-user_session_same_site=" + hostUserSessionSameSite)
                .body(formData)
                .retrieve()
                .toEntity(Map.class)
                .getBody();

        // CDN 서버 파일 업로드 
        String fileUrl = uploadFileToCDN(file, jsonMap);
        // 코멘트에 파일 링크 첨부해서 작성
        writeComment(html, fileUrl);
        log.debug("깃허브 파일 업로드 최종 성공");

        return fileUrl;
    }

    private String uploadFileToCDN(MultipartFile file, Map<String, Object> jsonMap) throws IOException {
        // multipart/form-data로 전송할 form-data 생성
        MultiValueMap<String, Object> formData = getFormData(file, jsonMap);

        // 파일 업로드 요청
        String uploadUrl = (String) jsonMap.get("upload_url");
        ResponseEntity<Void> res = RestClient.builder().build().post().uri(uploadUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .header(HttpHeaders.COOKIE,
                        "user_session=" + userSession + "; __Host-user_session_same_site=" + hostUserSessionSameSite)
                .body(formData)
                .retrieve()
                .toBodilessEntity();

        // 파일 업로드 실패 시 예외 발생
        if (!res.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("파일 업로드에 실패했습니다.");
        }

        String assetUploadUrl = (String) jsonMap.get("asset_upload_url");
        MultiValueMap<String, String> assetAuthenticityToken = new LinkedMultiValueMap<>();
        assetAuthenticityToken.add("authenticity_token", (String) jsonMap.get("asset_upload_authenticity_token"));

        ResponseEntity<Map> assetRes = restClient.put().uri(assetUploadUrl)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .header(HttpHeaders.COOKIE,
                        "user_session=" + userSession + "; __Host-user_session_same_site=" + hostUserSessionSameSite)
                .body(assetAuthenticityToken)
                .retrieve()
                .toEntity(Map.class);

        // 에셋 등록 실패 시 예외 발생
        if (!assetRes.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("에셋 등록에 실패했습니다.");
        }
        log.debug("CDN 파일 업로드 성공");
        return (String) assetRes.getBody().get("href");
    }

    private static MultiValueMap<String, Object> getFormData(MultipartFile file, Map<String, Object> jsonMap) throws IOException {
        // multipart/form-data로 전송할 form-data 생성
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        Map<String, String> form = (Map<String, String>) jsonMap.get("form");

        // form-data에 필요한 key-value 쌍 추가
        form.forEach(formData::add);

        // 파일 추가
        InputStreamResource resource = new InputStreamResource(file.getInputStream()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
            @Override
            public long contentLength() {
                return file.getSize();
            }
        };
        formData.add("file", resource);

        return formData;
    }

    private String getAuthenticityToken(String html, String cssQuery) {
        // Jsoup을 이용해 HTML 파싱해서 authenticity_token 가져오기
        Document document = Jsoup.parse(html);
        Element tokenElement = document.selectFirst(cssQuery);
        if (tokenElement == null) {
            throw new RuntimeException("인증 토큰을 찾을 수 없습니다.");
        }
        // authenticity_token 의 value 속성을 가져옴
        return tokenElement.attr("value");
    }

    private String getRepositoryId(String html, String cssQuery) {
        // Jsoup을 이용해 HTML 파싱해서 repository_id 가져오기
        Document document = Jsoup.parse(html);
        Element tokenElement = document.selectFirst(cssQuery);
        if (tokenElement == null) {
            throw new RuntimeException("리포지토리 id를 찾을 수 없습니다.");
        }
        // authenticity_token 의 value 속성을 가져옴
        return tokenElement.attr("data-upload-repository-id");
    }

    /**
     * 코멘트에 파일 링크 첨부
     * */
    public void writeComment(String html, String fileUrl) {
        // PR 페이지에서 authenticity_token 가져오기
        String authenticityToken = getAuthenticityToken(html, "form[id=new_comment_form] input[name=authenticity_token]");

        // 코멘트 작성 정보 가져오기
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("comment[body]", "[과제](" + fileUrl + ")를 업로드했습니다.");
        formData.add("issue", "1");
        formData.add("authenticity_token", authenticityToken);

        // 코멘트 작성 요청
        ResponseEntity<Void> res = restClient.post().uri(properties.getRepository() + "/pull/1/comment?sticky=true")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .header(HttpHeaders.COOKIE,
                        "user_session=" + userSession + "; __Host-user_session_same_site=" + hostUserSessionSameSite)
                .body(formData)
                .retrieve()
                .toBodilessEntity();

        // 코멘트 작성 실패 시 예외 발생
        if (!res.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("코멘트 작성에 실패했습니다.");
        }
        log.debug("코멘트 작성 성공");
    }


    @Override
    public String upload(MultipartFile file, String contentType) throws IOException {
        loginGitHub();
        return uploadGitHub(file, contentType);
    }

    @Override
    public void delete(String filePath) {
    }
}
