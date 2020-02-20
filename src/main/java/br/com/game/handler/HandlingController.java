package br.com.game.handler;

import br.com.game.dto.ExceptionDto;
import br.com.game.exception.GameException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(0)
public class HandlingController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> generalException(Exception e){
        return new ResponseEntity<>(new ExceptionDto(1, 500, e.getLocalizedMessage(), e),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GameException.class)
    public ResponseEntity<ExceptionDto> customException(GameException e){
        return new ResponseEntity<>(new ExceptionDto(3, e.getHttpStatus().value(), e.getMessage(), e),
                e.getHttpStatus());
    }
}
