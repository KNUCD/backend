package server.knucd.complaint.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Complaint {

    @Id @GeneratedValue
    private Long id;

//    private Member writer;

    private Location location;

    private String title;

    private String content;

    private String file;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Expression expression;
}
