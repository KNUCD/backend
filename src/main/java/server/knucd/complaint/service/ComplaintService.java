package server.knucd.complaint.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.knucd.complaint.dto.CreateComplaintForm;
import server.knucd.complaint.entity.Category;
import server.knucd.complaint.entity.Complaint;
import server.knucd.complaint.repository.ComplaintRepository;
import server.knucd.exception.NotFoundException;
import server.knucd.expression.repository.ExpressionRepository;
import server.knucd.file.service.FileService;
import server.knucd.member.entity.Member;
import server.knucd.member.repository.MemberRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ExpressionRepository expressionRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;

    @Transactional
    public Long save(CreateComplaintForm form, Long kakaoId) throws IOException {
        Member member = memberRepository.findByKakaoId(kakaoId).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));

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

    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }

    public List<Complaint> findAllByCategoryAndRange(Category category, Double lat1, Double long1, Double lat2, Double long2) {
        if(category.equals(Category.ALL)) return complaintRepository.findAllByRange(lat1, long1, lat2, long2);
        return complaintRepository.findAllByCategoryAndRange(category, lat1, long1, lat2, long2);
    }

    public List<Complaint> findAllByCategoryAndRange(Category category, Double lat1, Double long1, Double lat2, Double long2, int page, int size) {
        if(category.equals(Category.ALL)) return complaintRepository.findAllByRange(lat1, long1, lat2, long2, page, size);
        return  complaintRepository.findAllByCategoryAndRange(category, lat1, long1, lat2, long2, page, size);
    }

    public List<Complaint> findAllByCategory(Category category) {
        if(category.equals(Category.ALL)) return complaintRepository.findAll();
        return complaintRepository.findAllByCategory(category);
    }

    public Complaint findById(Long id) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new NotFoundException("민원이 존재하지 않습니다."));
        return complaint;
    }

    @Transactional
    public void deleteById(Long id, Long kakaoId) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new NotFoundException("민원이 존재하지 않습니다."));
        if(!Objects.equals(complaint.getWriter().getKakaoId(), kakaoId)) throw new IllegalStateException("삭제 권한이 없습니다.");
        expressionRepository.deleteAllByComplaintId(id);
        complaintRepository.delete(complaint);
    }

}
