package server.knucd.complaint.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import server.knucd.complaint.entity.Category;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateComplaintForm {

    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    private String title;
    @NotNull
    private String content;

    private MultipartFile file;

    @NotNull
    private Category category;
}
