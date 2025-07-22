package com.paywastex.exception;

public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException(){super("Too many attempts – slow down");}
}
