package server.knucd.complaint.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.knucd.complaint.dto.ComplaintDTO;
import server.knucd.complaint.dto.CreateComplaintForm;
import server.knucd.complaint.dto.PinDTO;
import server.knucd.complaint.entity.Category;
import server.knucd.complaint.entity.Complaint;
import server.knucd.complaint.service.ComplaintService;
import server.knucd.utils.api.ApiUtil;
import server.knucd.utils.api.ApiUtil.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Tag(name = "민원 관리")
@RestController
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @Operation(summary = "민원 생성", description = "Access Token")
    @PostMapping("/api/v1/complaint")
    public ApiSuccessResult<Long> create(@Valid CreateComplaintForm form,
                                         HttpServletRequest req) throws IOException{
        Long kakaoId = (Long) req.getAttribute("kakaoId");
        Long complaintId = complaintService.save(form, kakaoId);
        return ApiUtil.success(complaintId);
    }

    @Operation(summary = "민원 조회")
    @GetMapping("/api/v1/complaint/{id}")
    public ApiSuccessResult<ComplaintDTO> findById(@PathVariable Long id) {
        Complaint complaint = complaintService.findById(id);
        return ApiUtil.success(new ComplaintDTO(complaint));
    }

    @Operation(summary = "카테고리 별 민원 전체 조회(범위)")
    @GetMapping("/api/v1/complaint")
    public ApiSuccessResult<List<ComplaintDTO>> findAll(@RequestParam(name = "category") Category category,
                                                        @RequestParam(name = "ha") Double lat1,
                                                        @RequestParam(name = "qa") Double long1,
                                                        @RequestParam(name = "oa") Double lat2,
                                                        @RequestParam(name = "pa") Double long2) {
        List<Complaint> complaints = complaintService.findAllByCategoryAndRange(category, lat1, long1, lat2, long2);
        return ApiUtil.success(ComplaintDTO.makeList(complaints));
    }

    @Operation(summary = "페이징이 가능한 카테고리 별 민원 전체 조회(범위)")
    @GetMapping("/api/v1/complaint1")
    public ApiSuccessResult<List<ComplaintDTO>> findAll(@RequestParam(name = "category") Category category,
                                                        @RequestParam(name = "ha") Double lat1,
                                                        @RequestParam(name = "qa") Double long1,
                                                        @RequestParam(name = "oa") Double lat2,
                                                        @RequestParam(name = "pa") Double long2,
                                                        @RequestParam(name = "page") int page,
                                                        @RequestParam(name = "size") int size) {
        List<Complaint> complaints = complaintService.findAllByCategoryAndRange(category, lat1, long1, lat2, long2, page, size);
        return ApiUtil.success(ComplaintDTO.makeList(complaints));
    }

    @Operation(summary = "전체 핀 조회")
    @GetMapping("/api/v1/complaint/pin")
    public ApiSuccessResult<List<PinDTO>> findAllPins(@RequestParam(name = "category") Category category) {
        List<Complaint> complaints = complaintService.findAllByCategory(category);
        return ApiUtil.success(PinDTO.makeList(complaints));
    }

    @Operation(summary = "민원 삭제", description = "Access Token")
    @DeleteMapping("/api/v1/complaint/{id}")
    public ApiSuccessResult<String> delete(@PathVariable Long id,
                                           HttpServletRequest req) {
        Long kakaoId = (Long) req.getAttribute("kakaoId");
        complaintService.deleteById(id, kakaoId);
        return ApiUtil.success("삭제되었습니다.");
    }
}
