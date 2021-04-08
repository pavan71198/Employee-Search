package com.example.empsearch.util;

import com.example.empsearch.dto.EmployeeRequestDto;
import com.example.empsearch.dto.EmployeeResponseDto;
import com.example.empsearch.entity.Employee;

public class EmployeeDtoMapper {
    public EmployeeDtoMapper() {

    }

    public EmployeeResponseDto toResponseDto(Employee employee){
        return new EmployeeResponseDto(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getDesignation(), employee.getSalary(), employee.getDateOfJoining(), employee.getAddress(), employee.getGender(), employee.getAge(), employee.getMaritalStatus(), employee.getInterests());
    }

    public Employee toEmployee(EmployeeRequestDto employeeRequestDto){
        return new Employee(employeeRequestDto.getFirstName(), employeeRequestDto.getLastName(), employeeRequestDto.getDesignation(), employeeRequestDto.getSalary(), employeeRequestDto.getDateOfJoining(), employeeRequestDto.getAddress(), employeeRequestDto.getGender(), employeeRequestDto.getAge(), employeeRequestDto.getMaritalStatus(), employeeRequestDto.getInterests());
    }
}
