package server.knucd.complaint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.knucd.complaint.entity.Complaint;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PinDTO {

    private Long id;

    private Double latitude;

    private Double longitude;

    public static List<PinDTO> makeList(List<Complaint> complaints) {
        List<PinDTO> pinDTOs = new ArrayList<>();
        for(Complaint complaint : complaints)
            pinDTOs.add(new PinDTO(complaint.getId(), complaint.getLocation().getLatitude(), complaint.getLocation().getLongitude()));
        return pinDTOs;
    }
}
