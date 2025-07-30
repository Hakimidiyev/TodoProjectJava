package uz.pdp.dto.todo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TodoCreateDto {
    @NotBlank(message = "title can not be null")
    private String title;
    @NotNull(message = "priority can not be null")
    private Integer priority;

}
