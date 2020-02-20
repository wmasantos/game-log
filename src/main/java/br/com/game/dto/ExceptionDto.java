package br.com.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDto implements Serializable {
    public static final long serialVersionUID = 1L;

    private int errorCode;
    private int httpCode;
    private String developerMessage;
    private Exception exception;
}
