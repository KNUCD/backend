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

    private Location location;

    private String title;

    private String content;

    private String file;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Expression expression;

    @Builder
    public Complaint(Member writer, Location location, String title, String content,
                     String file, Category category, Expression expression) {
        this.writer = writer;
        this.location = location;
        this.title = title;
        this.content = content;
        this.file = file;
        this.category = category;
        this.expression = expression;
    }
}
