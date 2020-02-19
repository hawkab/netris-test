package ru.alarh.netris.exception;

public class WrongTypeException extends Exception {
    
	private static final long serialVersionUID = 1;

    public WrongTypeException() { super(); }

    public WrongTypeException(String s) { super(s); }
    
}