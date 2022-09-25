package server.knucd.expression.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.knucd.expression.entity.ExpressionType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpressionDTO {

    private Long goodCount;

    private Long badCount;

    private Long amazingCount;
}
