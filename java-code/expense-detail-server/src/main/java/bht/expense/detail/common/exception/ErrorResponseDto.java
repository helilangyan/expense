package bht.expense.detail.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 姚轶文
 * @date 2020/7/21 18:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    private int code;
    private String message;

}
