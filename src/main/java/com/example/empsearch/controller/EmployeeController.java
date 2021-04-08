package com.example.empsearch.controller;

import com.example.empsearch.dto.EmployeeRequestDto;
import com.example.empsearch.dto.EmployeeResponseDto;
import com.example.empsearch.service.EmployeeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    private EmployeeSearchService employeeSearchService;

    @PostMapping(path="/ingest")
    List<String> ingestData(@RequestBody List<EmployeeRequestDto> employeeRequestDtoList){
        return employeeSearchService.ingestData(employeeRequestDtoList);
    }

    @GetMapping(path="/search")
    List<EmployeeResponseDto> search(@RequestBody String query){
        return employeeSearchService.searchEmployee(query);
    }

    @DeleteMapping(path="/clear")
    String clearData(){
        return employeeSearchService.clearData();
    }

}
