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
import server.knucd.utils.redis.RedisUtil;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpressionService {

    private final ExpressionRepository expressionRepository;
    private final ComplaintRepository complaintRepository;
    private final MemberRepository memberRepository;

    private final RedisUtil redis;

    @Transactional
    public void save(Long memberId, CreateExpressionForm form) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));
        Complaint complaint = complaintRepository.findById(form.getComplaintId()).orElseThrow(() -> new NotFoundException("민원이 존재하지 않습니다."));

        Expression expression = Expression.builder()
                .complaint(complaint)
                .follower(member)
                .type(form.getType())
                .build();
        expressionRepository.save(expression, complaint.getId(), memberId);
    }

    public Long countExpressionByComplaintId(Long complaintId, ExpressionType type) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new NotFoundException("민원이 존재하지 않습니다."));
        return expressionRepository.countExpressionByComplaintId(complaintId, type);
    }

    public ExpressionType findMyExpressionByComplaintId(Long complaintId, Long memberId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new NotFoundException("민원이 존재하지 않습니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));
        return expressionRepository.findMyExpressionByComplaintId(complaintId, memberId);
    }
}
