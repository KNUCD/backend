package server.knucd.complaint.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.knucd.complaint.dto.CreateComplaintForm;
import server.knucd.complaint.entity.Category;
import server.knucd.complaint.entity.Complaint;
import server.knucd.expression.entity.Expression;
import server.knucd.complaint.repository.ComplaintRepository;
import server.knucd.exception.NotFoundException;
import server.knucd.expression.repository.ExpressionRepository;
import server.knucd.file.service.FileService;
import server.knucd.member.entity.Member;
import server.knucd.member.repository.MemberRepository;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ExpressionRepository expressionRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;

    @Transactional
    public Long save(CreateComplaintForm form) throws IOException {
        // sample member
        Member member = Member.builder()
                .name("테스트")
                .phone("010-1234-5678")
                .email("123@abc.com")
                .address("대구 북구 대학로 80")
                .build();
        memberRepository.save(member);

        Complaint complaint = Complaint.builder()
                .writer(member)
                .title(form.getTitle())
                .content(form.getContent())
                .category(form.getCategory())
                .latitude(form.getLatitude())
                .longitude(form.getLongitude())
                .build();

        complaintRepository.save(complaint);

        if(form.getFile() != null) {
            String path = fileService.uploadPNG(complaint.getId() + "", form.getFile().getBytes());
            complaint.setFile(path);
        }
        return complaint.getId();
    }

    public List<Complaint> findAllByCategory(Category category) {
        if(category.equals(Category.ALL)) return complaintRepository.findAll();
        return complaintRepository.findAllByCategory(category);
    }

    public List<Complaint> findAllByCategory(Category category, int page, int size) {
        if(category.equals(Category.ALL)) return complaintRepository.findAll(page, size);
        return  complaintRepository.findAllByCategory(category, page, size);
    }

    public Complaint findById(Long id) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new NotFoundException("민원이 존재하지 않습니다."));
        return complaint;
    }

    @Transactional
    public void deleteById(Long id) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new NotFoundException("민원이 존재하지 않습니다."));
        List<Expression> expressions = expressionRepository.findAllByComplaintId(id);
        for(Expression expression : expressions) expressionRepository.delete(expression);

        complaintRepository.delete(complaint);
    }

}
