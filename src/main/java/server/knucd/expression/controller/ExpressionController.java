package server.knucd.expression.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.knucd.expression.dto.CreateExpressionForm;
import server.knucd.expression.dto.ExpressionDTO;
import server.knucd.expression.service.ExpressionService;
import server.knucd.utils.api.ApiUtil;
import server.knucd.utils.api.ApiUtil.*;


@RestController
@RequiredArgsConstructor
public class ExpressionController {

    private final ExpressionService expressionService;

    @PostMapping("/api/v1/expression")
    public ApiSuccessResult<String> create(@RequestBody CreateExpressionForm form) {
        Long memberId = 1L; // 샘플
        expressionService.save(memberId, form);
        return ApiUtil.success("감정표현이 추가되었습니다.");
    }

    @GetMapping("/api/v1/expression/{id}")
    public ApiSuccessResult<ExpressionDTO> findExpressionsByComplaintId(@PathVariable Long id) {
        Long memberId = 1L; // 샘플
        return  ApiUtil.success(new ExpressionDTO(
                expressionService.countGreatByComplaintId(id),
                expressionService.countLikeByComplaintId(id),
                expressionService.countSadByComplaintId(id),
                expressionService.findMyExpressionByComplaintId(id, memberId)
        ));
    }
}
