package server.knucd.complaint.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.knucd.base.BaseEntity;
import server.knucd.member.entity.Member;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Complaint extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    private String writerName;

    private String writerImg;

    @Embedded
    private Location location;

    private String title;

    private String content;

    private String file;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public Complaint(Member writer, Double latitude, Double longitude, String title, String content,
                     String file, Category category, String writerImg) {
        this.writer = writer;
        this.writerName = writer.getName();
        this.location = new Location(latitude, longitude);
        this.title = title;
        this.content = content;
        this.file = file;
        this.category = category;
        this.writerImg = writerImg;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
