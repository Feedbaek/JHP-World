package minskim2.JHP_World.global.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUtil {

    /**
     * 파일 업로드
     * @param file 파일
     * @return 파일 경로
     * */
    String upload(MultipartFile file, String contentType) throws IOException;

    /**
     * 파일 삭제
     * @param filePath 파일 경로
     * */
    void delete(String filePath);
}
