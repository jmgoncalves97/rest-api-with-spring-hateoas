package com.example.payroll.exception;

public class EmployeeNotFoundException extends RuntimeException {
    
    public EmployeeNotFoundException(Long id) {
        super(String.format("Employee with id %s not found", id));
    }
}
