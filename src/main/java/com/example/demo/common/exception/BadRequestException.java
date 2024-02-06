package com.example.demo.common.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends IllegalArgumentException {
    public BadRequestException(String msg) {
        super(msg);
    }

    public static BadRequestException of(String msg) {
        return new BadRequestException(msg);
    }
}
