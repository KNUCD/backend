package server.knucd.expression.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.knucd.expression.entity.ExpressionType;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExpressionForm {

    @NotNull private Long complaintId;
    @NotNull private ExpressionType type;
}
