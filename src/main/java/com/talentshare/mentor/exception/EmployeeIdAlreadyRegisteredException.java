package com.talentshare.mentor.exception;

public class EmployeeIdAlreadyRegisteredException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public EmployeeIdAlreadyRegisteredException(String message) {
        super(message);
    }
	
}
