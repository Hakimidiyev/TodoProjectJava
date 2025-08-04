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
    @NotBlank(message = "title.null")
    private String title;
    @NotNull(message = "priority.null")
    private Integer priority;

}