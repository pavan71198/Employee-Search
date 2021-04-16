package com.example.empsearch.service;

import com.example.empsearch.dto.EmployeeRequestDto;
import com.example.empsearch.dto.EmployeeResponseDto;

import java.util.List;

public interface EmployeeSearchService {

    String ingestData(List<EmployeeRequestDto> employeeRequestDtoList);
    EmployeeResponseDto findById(String id);
    List<EmployeeResponseDto> findAll();
    List<EmployeeResponseDto> searchByName(String query);
    List<EmployeeResponseDto> searchByInterest(String query);
    List<EmployeeResponseDto> searchByAll(String query);
    String clearData();
}
