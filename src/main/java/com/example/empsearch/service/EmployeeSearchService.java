package com.example.empsearch.service;

import com.example.empsearch.dto.EmployeeRequestDto;
import com.example.empsearch.dto.EmployeeResponseDto;

import java.util.List;

public interface EmployeeSearchService {

    List<String> ingestData(List<EmployeeRequestDto> employeeRequestDtoList);
    List<EmployeeResponseDto> searchEmployee(String query);
    String clearData();
}
