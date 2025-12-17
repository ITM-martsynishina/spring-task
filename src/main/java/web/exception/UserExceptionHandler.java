package web.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFound(UserNotFoundException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(DataBaseException.class)
    public String dataBaseException(DataBaseException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        return "error";
    }
}
