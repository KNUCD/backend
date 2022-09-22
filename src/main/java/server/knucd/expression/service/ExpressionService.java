package server.knucd.expression.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.knucd.complaint.entity.Complaint;
import server.knucd.complaint.repository.ComplaintRepository;
import server.knucd.exception.NotFoundException;
import server.knucd.expression.dto.CreateExpressionForm;
import server.knucd.expression.entity.Expression;
import server.knucd.expression.entity.ExpressionType;
import server.knucd.expression.repository.ExpressionRepository;
import server.knucd.member.entity.Member;
import server.knucd.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpressionService {

    private final ExpressionRepository expressionRepository;
    private final ComplaintRepository complaintRepository;
    private final MemberRepository memberRepository;

    // 캐싱 필요
    @Transactional
    public void save(Long memberId, CreateExpressionForm form) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));
        Complaint complaint = complaintRepository.findById(form.getComplaintId()).orElseThrow(() -> new NotFoundException("민원이 존재하지 않습니다."));
        expressionRepository.findMyExpressionByComplaintId(form.getComplaintId(), memberId).ifPresent(expressionRepository::delete);

        Expression expression = Expression.builder()
                .complaint(complaint)
                .follower(member)
                .type(form.getType())
                .build();

        expressionRepository.save(expression);
    }

    public Long countGreatByComplaintId(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new NotFoundException("민원이 존재하지 않습니다."));
        return expressionRepository.countGreatByComplaintId(complaintId);
    }

    public Long countLikeByComplaintId(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new NotFoundException("민원이 존재하지 않습니다."));
        return expressionRepository.countLikeByComplaintId(complaintId);
    }

    public Long countSadByComplaintId(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new NotFoundException("민원이 존재하지 않습니다."));
        return expressionRepository.countSadByComplaintId(complaintId);
    }

    public ExpressionType findMyExpressionByComplaintId(Long complaintId, Long memberId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new NotFoundException("민원이 존재하지 않습니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));
        Expression expression = expressionRepository.findMyExpressionByComplaintId(complaintId, memberId)
                .orElse(null);
        return expression == null ? null : expression.getType();
    }
}
