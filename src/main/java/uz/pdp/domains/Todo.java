package uz.pdp.domains;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

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
    private List<AuthUser> authUser;
    private LocalDateTime created_at;


}
