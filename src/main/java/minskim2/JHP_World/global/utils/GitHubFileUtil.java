package minskim2.JHP_World.global.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.config.properties.GithubProperties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.kohsuke.github.GHRef;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
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

@Component
@RequiredArgsConstructor
public class GitHubFileUtil implements FileUtil {

    private final GitHub gitHub;
    private final GithubProperties properties;

    private final RestClient restClient = RestClient.builder()
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.TEXT_HTML_VALUE)
            .baseUrl("https://github.com")  // base URL 설정
            .build();

    // cookie user_session, __Host-user_session_same_site
    private String userSession;
    private String hostUserSessionSameSite;

    private GHRepository repo;
    private GHRef mainBranch;
    private GHRef PRBranch;


    @PostConstruct
    public void init () {
        try {
            repo = gitHub.getRepository(properties.getRepository());
            mainBranch = repo.getRef("heads/main");
            PRBranch = repo.getRef(properties.getBranch());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * GitHub 로그인
     * */
    public void loginGitHub() {
        // HTML 파싱해서 authenticity_token 가져오기
        String html = restClient.get().uri("/login")
                .retrieve()
                .body(String.class);

        String authenticityToken = getAuthenticityToken(html, "input[name=authenticity_token]");
        System.out.println("authenticity_token: " + authenticityToken);

        // multipart/form-data로 전송할 form-data 생성
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("login", properties.getId());
        formData.add("password", properties.getPassword());
        formData.add("authenticity_token", authenticityToken);

        // 로그인 요청
        ResponseEntity<Void> res = restClient.post().uri("/session")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .body(formData)
                .retrieve()
                .toBodilessEntity();

        // 쿠키 저장
        List<String> cookies = res.getHeaders().get("Set-Cookie");
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
    }

    /**
     * GitHub 에 파일 업로드
     * */
    public String uploadGitHub(MultipartFile file) throws IOException {
        // PR 페이지에서 authenticity_token 가져오기
        String html = restClient.get().uri(repo.getHtmlUrl() + "/pull/1")
                .header(HttpHeaders.COOKIE,
                        "user_session=" + userSession + "; __Host-user_session_same_site=" + hostUserSessionSameSite)
                .retrieve()
                .body(String.class);

        String authenticityToken = getAuthenticityToken(html, "input[class=js-data-upload-policy-url-csrf]");
        System.out.println("authenticity_token: " + authenticityToken);

        // 파일 업로드 정보 가져오기
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("name", file.getOriginalFilename());
        formData.add("size", String.valueOf(file.getSize()));
        formData.add("content_type", "application/pdf");
        formData.add("authenticity_token", authenticityToken);
        formData.add("repository_id", String.valueOf(repo.getId()));

        Map<String, Object> jsonMap = (Map<String, Object>) restClient.post().uri("/upload/policies/assets")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .header(HttpHeaders.COOKIE,
                        "user_session=" + userSession + "; __Host-user_session_same_site=" + hostUserSessionSameSite)
                .body(formData)
                .retrieve()
                .toEntity(Map.class)
                .getBody();

        // CDN 서버 파일 업로드 
        uploadFileToCDN(file, jsonMap);

        // file url 가져오기
        Map<String, Object> asset = (Map<String, Object>) jsonMap.get("asset");
        return (String) asset.get("href");
    }

    private void uploadFileToCDN(MultipartFile file, Map<String, Object> jsonMap) throws IOException {
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


    @Override
    public String upload(MultipartFile file) throws IOException {
        loginGitHub();
        return uploadGitHub(file);
    }

    @Override
    public void delete(String filePath) {
    }
}
