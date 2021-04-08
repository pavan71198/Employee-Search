package com.example.empsearch.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.empsearch.dto.EmployeeRequestDto;
import com.example.empsearch.dto.EmployeeResponseDto;
import com.example.empsearch.entity.Employee;
import com.example.empsearch.exception.QueryMalformedException;
import com.example.empsearch.util.EmployeeDtoMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.IndicesOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.BulkFailureException;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
public class EmployeeSearchServiceImpl implements EmployeeSearchService{
    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Autowired
    private ElasticsearchRepository<Employee, String> elasticsearchRepository;

    private final EmployeeDtoMapper employeeDtoMapper = new EmployeeDtoMapper();

    private static final Logger log = LoggerFactory.getLogger(EmployeeSearchService.class);

    public List<String> ingestData(List<EmployeeRequestDto> employeeRequestDtoList){
        List <String> idList = new ArrayList<String>();
        List <Employee> employeeList = new ArrayList<Employee>();
        for (EmployeeRequestDto employeeRequestDto : employeeRequestDtoList){
            Employee employee = employeeDtoMapper.toEmployee(employeeRequestDto);
            employeeList.add(employee);
        }
        try {
            elasticsearchRepository.saveAll(employeeList);
        }
        catch (BulkFailureException exception){
            log.error(exception.getFailedDocuments().toString());
        }
        for (Employee employee: employeeList){
            idList.add(employee.getId());
        }
        return idList;
    }

    public List<EmployeeResponseDto> searchEmployee(String query) {
        List<EmployeeResponseDto> searchHitsEmployeeResponseDtoList = new ArrayList<EmployeeResponseDto>();
        int matchCount = 0;
        boolean moreMatches = false;
        Query searchQuery = new StringQuery(query);
        SearchHits<Employee> searchHits;
        try {
            searchHits = elasticsearchTemplate.search(searchQuery, Employee.class, IndexCoordinates.of("companydatabase"));
            for (SearchHit<Employee> searchHit : searchHits) {
                Employee employee = searchHit.getContent();
                searchHitsEmployeeResponseDtoList.add(employeeDtoMapper.toResponseDto(employee));
                matchCount++;
                if (matchCount == 10) {
                    moreMatches = true;
                    break;
                }
            }
        }
        catch (Exception exception) {
//            System.out.println(exception.getMessage());
//            for (StackTraceElement stackTraceElement : exception.getStackTrace()){
//                System.out.println(stackTraceElement);
//            }
            throw new QueryMalformedException();
        }

        return searchHitsEmployeeResponseDtoList;
    }

    public String clearData(){
        try {
            elasticsearchRepository.deleteAll();
            return "success";
        }
        catch (Exception exception){
            return "failed";
        }
    }
}
