package minskim2.JHP_World.domain.file.dto;

import lombok.*;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FileDto {

    private final Long id;
    private final String name;
    private final String url;
    private final String ext;
}
