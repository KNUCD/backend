package server.knucd.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import server.knucd.utils.api.ApiUtil.*;
import server.knucd.utils.api.ApiUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * Dispatcher Servlet 이후 레이어에서 발생한 에러 핸들링
 * @see ExceptionHandler
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected ResponseEntity<Object> handleExceptionInternal
            (Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        ApiErrorResult<String> error = ApiUtil.error(status.value(), "알 수 없는 오류 서버팀에 문의해주세요");
        return super.handleExceptionInternal(ex, error, headers, status, request);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<?> handleNotFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        ApiErrorResult<String> error = ApiUtil.error(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        ApiErrorResult<String> error = ApiUtil.error(HttpServletResponse.SC_BAD_REQUEST, "Method Argument Not Valid");
        return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleNormalException(Exception e) {
        logger.error(e.getMessage(), e);
        ApiErrorResult<String> error = ApiUtil.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "알 수 없는 오류 서버팀에 문의해주세요");
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(error);
    }
}
