package server.knucd;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Operation(summary = "CI/CD 시연영상을 위한 컨트롤러")
    @GetMapping("api/v1/cicd/test")
    public void CICD() {
//        logic here
    }
}
