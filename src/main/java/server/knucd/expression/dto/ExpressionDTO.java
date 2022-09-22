package server.knucd.expression.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.knucd.expression.entity.ExpressionType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpressionDTO {

    private Long greatCount;

    private Long likeCount;

    private Long sadCount;

    private ExpressionType myExpression;
}
