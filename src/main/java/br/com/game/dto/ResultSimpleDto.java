package br.com.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultSimpleDto implements Serializable{
    public static final long serialVersionUID = 1L;

    private int code;
    private int httpCode;
    private String message;
}
