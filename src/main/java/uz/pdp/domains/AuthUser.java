package uz.pdp.domains;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthUser {
    private Long id;
    private String username;
    private String password;
    private List<AuthRole>roles;
    private Long userImageId;

}
