package server.knucd.complaint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.knucd.complaint.entity.Category;
import server.knucd.complaint.entity.Complaint;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintDTO {

    private Long id;

    private String title;

    private String content;

    private String file;

    private String writerName;

    private String writerImg;

    private Category category;

    private LocalDateTime createdDate;

    private Double latitude;

    private Double longitude;

    public ComplaintDTO(Complaint complaint) {
        this.id = complaint.getId();
        this.title = complaint.getTitle();
        this.content = complaint.getContent();
        this.file = complaint.getFile();
        this.writerName = complaint.getWriterName();
        this.category = complaint.getCategory();
        this.createdDate = complaint.getCreatedDate();
        this.latitude = complaint.getLocation().getLatitude();
        this.longitude = complaint.getLocation().getLongitude();
        this.writerImg = complaint.getWriterImg();
    }

    public static List<ComplaintDTO> makeList(List<Complaint> complaints) {
        List<ComplaintDTO> complaintDTOS = new ArrayList<>();
        for(Complaint complaint : complaints) complaintDTOS.add(new ComplaintDTO(complaint));
        return complaintDTOS;
    }
}
