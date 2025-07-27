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
    private Long userId;
    private LocalDateTime created_at;
    private boolean deleted;

    public Todo(String title, Integer priority, Long userId) {
        this.title = title;
        this.priority = priority;
        this.userId = userId;
        this.created_at = LocalDateTime.now();
    }
}
