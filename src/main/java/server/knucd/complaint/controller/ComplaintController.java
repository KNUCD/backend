package server.knucd.complaint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.knucd.complaint.dto.ComplaintDTO;
import server.knucd.complaint.dto.CreateComplaintForm;
import server.knucd.complaint.entity.Category;
import server.knucd.complaint.entity.Complaint;
import server.knucd.complaint.service.ComplaintService;
import server.knucd.utils.api.ApiUtil;
import server.knucd.utils.api.ApiUtil.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @PostMapping("/complaint")
    public ApiSuccessResult<Long> create(@RequestBody @Valid CreateComplaintForm form) throws IOException{
        Long complaintId = complaintService.save(form);
        return ApiUtil.success(complaintId);
    }

    @GetMapping("/complaint/{id}")
    public ApiSuccessResult<ComplaintDTO> findById(@PathVariable Long id) {
        Complaint complaint = complaintService.findById(id);
        return ApiUtil.success(new ComplaintDTO(complaint));
    }

    @GetMapping("/complaint")
    public ApiSuccessResult<List<ComplaintDTO>> findAll(@RequestParam(name = "category") Category category) {
        List<Complaint> complaints = complaintService.findAllByCategory(category);
        return ApiUtil.success(ComplaintDTO.makeList(complaints));
    }

    @GetMapping("/complaint1")
    public ApiSuccessResult<List<ComplaintDTO>> findAll(@RequestParam(name = "category") Category category,
                                      @RequestParam(name = "page") int page,
                                      @RequestParam(name = "size") int size) {
        List<Complaint> complaints = complaintService.findAllByCategory(category, page, size);
        return ApiUtil.success(ComplaintDTO.makeList(complaints));
    }

    @DeleteMapping("/complaint/{id}")
    public ApiSuccessResult<String> delete(@PathVariable Long id) {
        complaintService.deleteById(id);
        return ApiUtil.success("삭제되었습니다.");
    }
}
