package minskim2.JHP_World.domain.file.service;

import lombok.RequiredArgsConstructor;
import minskim2.JHP_World.domain.file.dto.FileDto;
import minskim2.JHP_World.domain.file.entity.File;
import minskim2.JHP_World.domain.file.repository.FileRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public void deleteFile(Long fileId) {
        fileRepository.deleteById(fileId);
    }

    public FileDto createFile(String name, String url, String ext) {
        File file = fileRepository.save(File.builder()
                .name(name)
                .url(url)
                .ext(ext)
                .build());

        return FileDto.builder()
                .id(file.getId())
                .name(file.getName())
                .url(file.getUrl())
                .ext(file.getExt())
                .build();
    }
}
