package server.knucd.expression.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.knucd.expression.dto.CreateExpressionForm;
import server.knucd.expression.dto.ExpressionDTO;
import server.knucd.expression.entity.ExpressionType;
import server.knucd.expression.service.ExpressionService;
import server.knucd.utils.api.ApiUtil;
import server.knucd.utils.api.ApiUtil.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
public class ExpressionController {

    private final ExpressionService expressionService;

    @PostMapping("/api/v1/expression")
    public ApiSuccessResult<String> create(@RequestBody CreateExpressionForm form,
                                           HttpServletRequest req) {
        Long kakaoId = (Long) req.getAttribute("kakaoId");
        expressionService.save(kakaoId, form);
        return ApiUtil.success("감정표현이 추가되었습니다.");
    }

    @GetMapping("/api/v1/expression/{id}")
    public ApiSuccessResult<ExpressionDTO> findExpressionsByComplaintId(@PathVariable Long id) {
        return  ApiUtil.success(new ExpressionDTO(
                expressionService.countExpressionByComplaintId(id, ExpressionType.GOOD),
                expressionService.countExpressionByComplaintId(id, ExpressionType.BAD),
                expressionService.countExpressionByComplaintId(id, ExpressionType.AMAZING)
        ));
    }

    @GetMapping("/api/v1/expression/{id}/me")
    public ApiSuccessResult<ExpressionType> findMyExpressByComplaintId(@PathVariable Long id,
                                                                       HttpServletRequest req) {
        Long kakaoId = (Long) req.getAttribute("kakaoId");
        return ApiUtil.success(expressionService.findMyExpressionByComplaintId(id, kakaoId));
    }
}
