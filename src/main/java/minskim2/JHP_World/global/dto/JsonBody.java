package minskim2.JHP_World.global.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import minskim2.JHP_World.global.enums.SuccessStatus;

@Getter
@AllArgsConstructor(staticName = "of")
public class JsonBody<T> implements ResponseBody {
    @NotNull
    private final int status;
    @NotBlank
    private final String message;
    private final T data;

    public static <T> JsonBody<T> success(SuccessStatus status, T data) {
        return JsonBody.of(status.getStatus(), status.getMessage(), data);
    }
}
