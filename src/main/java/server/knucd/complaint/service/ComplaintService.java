package server.knucd.complaint.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.knucd.complaint.dto.CreateComplaintForm;
import server.knucd.complaint.entity.Category;
import server.knucd.complaint.entity.Complaint;
import server.knucd.complaint.entity.Location;
import server.knucd.complaint.repository.ComplaintRepository;
import server.knucd.member.entity.Member;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public void save(CreateComplaintForm form) {
        // sample member
        Member member = Member.builder()
                .name("테스트")
                .phone("010-1234-5678")
                .email("123@abc.com")
                .address("대구 북구 대학로 80")
                .build();

        // 요기에서 파일 스토리지에 저장하고 path 추출
        String path = "/image.png";

        Complaint complaint = Complaint.builder()
                .writer(member)
                .title(form.getTitle())
                .content(form.getContent())
                .file(path)
                .category(form.getCategory())
                .build();

        complaintRepository.save(complaint);
    }

    public List<Complaint> findAllByCategory(Category category) {
        List<Complaint> complaints = complaintRepository.findAllByCategory(category);
        return complaints;
    }

    public List<Complaint> findAllByCategory(Category category, int page, int size) {
        List<Complaint> complaints = complaintRepository.findAllByCategory(category, page, size);
        return complaints;
    }

    // 감정 표현 추가 기능 구현 예정

    public void deleteById(Long id) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow();
        complaintRepository.delete(complaint);
    }

}
