package uz.pdp.domains;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Todo {
    private Long id;
    private String title;
    private Integer priority;
    private LocalDateTime created_at;


}
