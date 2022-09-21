package server.knucd.complaint.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.knucd.member.entity.Member;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Complaint {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    @Embedded
    private Location location;

    private String title;

    private String content;

    private String file;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private Expression expression;

    @Builder
    public Complaint(Member writer, Double latitude, Double longitude, String title, String content,
                     String file, Category category) {
        this.writer = writer;
        this.location = new Location(latitude, longitude);
        this.title = title;
        this.content = content;
        this.file = file;
        this.category = category;
        this.expression = new Expression(0L,0L,0L);
    }
}
