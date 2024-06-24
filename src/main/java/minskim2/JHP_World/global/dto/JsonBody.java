package minskim2.JHP_World.global.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class JsonBody<T> implements ResponseBody {
    @NotNull
    private final int status;
    @NotBlank
    private final String message;
    private final T data;
}
