package com.example.empsearch.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String id) {
        super("Employee document not found: " + id);
    }
    public EmployeeNotFoundException() {
        super("Employee id invalid.");
    }
}