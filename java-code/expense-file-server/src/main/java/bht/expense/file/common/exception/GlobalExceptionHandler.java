package bht.expense.file.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 姚轶文
 * @date 2020/7/21 17:55
 */

@RestControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ErrorResponseDto customExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        CustomException exception = (CustomException) e;
        return new ErrorResponseDto(exception.getCode(), exception.getMessage());
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runtimeExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        RuntimeException exception = (RuntimeException) e;
        return new ResponseEntity<Object>(new ErrorResponseDto(400, exception.getMessage()), HttpStatus.OK);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            //return new ResponseEntity<>(new ErrorResponseDto(status.value(), exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()), status);
            return new ResponseEntity<>(new ErrorResponseDto(status.value(), exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()), HttpStatus.OK);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
            logger.error("参数转换失败，方法：" + exception.getParameter().getMethod().getName() + "，参数：" + exception.getName()
                    + ",信息：" + exception.getLocalizedMessage());
            //return new ResponseEntity<>(new ErrorResponseDto(status.value(),"参数转换失败"), status);
            return new ResponseEntity<>(new ErrorResponseDto(status.value(),"参数转换失败"), HttpStatus.OK);
        }
        //return new ResponseEntity<>(new ErrorResponseDto(status.value(), "参数转换失败"), status);
        return new ResponseEntity<>(new ErrorResponseDto(status.value(), ex.getMessage()), HttpStatus.OK);
    }



}
