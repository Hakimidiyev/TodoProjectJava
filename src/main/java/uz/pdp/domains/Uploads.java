package uz.pdp.domains;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Uploads {
    private Long id;
    private String originalName;
    private String generatedName;
    private Long size;
    private String mimeType;
    private String path;
}
