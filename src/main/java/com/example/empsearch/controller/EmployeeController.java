package com.example.empsearch.controller;

import com.example.empsearch.dto.EmployeeRequestDto;
import com.example.empsearch.dto.EmployeeResponseDto;
import com.example.empsearch.entity.Employee;
import com.example.empsearch.service.EmployeeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeSearchService employeeSearchService;

    @PostMapping
    String ingestData(@RequestBody List<EmployeeRequestDto> employeeRequestDtoList){
        return employeeSearchService.ingestData(employeeRequestDtoList);
    }

    @GetMapping("/{id}")
    EmployeeResponseDto findById(@PathVariable String id){
        return employeeSearchService.findById(id);
    }

    @GetMapping("/all")
    List<EmployeeResponseDto> findAll(){
        return employeeSearchService.findAll();
    }

    @GetMapping("/search/name")
    List<EmployeeResponseDto> searchByName(@RequestParam String query){
        return employeeSearchService.searchByName(query);
    }

    @GetMapping("/search/interest")
    List<EmployeeResponseDto> searchByInterest(@RequestParam String query){
        return employeeSearchService.searchByInterest(query);
    }

    @GetMapping("/search/all")
    List<EmployeeResponseDto> searchByAll(@RequestParam String query){
        return employeeSearchService.searchByAll(query);
    }

    @DeleteMapping
    String clearData(){
        return employeeSearchService.clearData();
    }

}
