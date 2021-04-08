package com.example.empsearch.exception;

public class QueryMalformedException extends RuntimeException {
    public QueryMalformedException() {
        super("Query string not according to format");
    }
}