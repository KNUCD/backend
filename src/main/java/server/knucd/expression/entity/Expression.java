package server.knucd.expression.entity;

import lombok.*;
import server.knucd.base.BaseEntity;
import server.knucd.complaint.entity.Complaint;
import server.knucd.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expression extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Complaint complaint;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member follower;

    @Enumerated(EnumType.STRING)
    private ExpressionType type;

    @Builder
    public Expression(Complaint complaint, Member follower, ExpressionType type) {
        this.complaint = complaint;
        this.follower = follower;
        this.type = type;
    }

    public void updateType(ExpressionType type) {
        this.type = type;
    }
}
