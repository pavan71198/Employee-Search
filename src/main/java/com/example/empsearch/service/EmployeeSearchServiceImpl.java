package com.example.empsearch.service;

import com.example.empsearch.dto.EmployeeRequestDto;
import com.example.empsearch.dto.EmployeeResponseDto;
import com.example.empsearch.entity.Employee;
import com.example.empsearch.exception.EmployeeNotFoundException;
import com.example.empsearch.util.EmployeeDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.watcher.WatchStatusDateParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EmployeeSearchServiceImpl implements EmployeeSearchService{
    @Autowired
    private RestHighLevelClient elasticsearchClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${empsearch.es.index}")
    private String esIndex;

    private final EmployeeDtoMapper employeeDtoMapper = new EmployeeDtoMapper();

    private static final Logger log = LoggerFactory.getLogger(EmployeeSearchService.class);

    public String ingestData(List<EmployeeRequestDto> employeeRequestDtoList){
        Map <String, Employee> idEmployeeMap = new HashMap<>();
        BulkRequest bulkRequest = new BulkRequest();
        for (EmployeeRequestDto employeeRequestDto : employeeRequestDtoList){
            Employee employee = employeeDtoMapper.toEmployee(employeeRequestDto);
            idEmployeeMap.put(employee.getId(), employee);
            IndexRequest request = new IndexRequest(esIndex);
            request.id(employee.getId());
            request.source(convertEmployeeToMap(employee), XContentType.JSON);
            bulkRequest.add(request);
        }
        BulkResponse bulkResponse;
        try {
            bulkResponse = elasticsearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()){
                for (BulkItemResponse bulkItemResponse: bulkResponse){
                    if (bulkItemResponse.isFailed()){
                        log.error(bulkItemResponse.getFailure().toString());
                        log.info(idEmployeeMap.get(bulkItemResponse.getId()).toString());
                    }
                }
                return "failure";
            }
            else{
                return "success";
            }

        }
        catch (IOException exception){
            log.error(exception.getMessage());
            for (StackTraceElement stackTraceElement : exception.getStackTrace()){
                log.info(stackTraceElement.toString());
            }
            return "failure";
        }
    }

    public List<EmployeeResponseDto> searchByName(String query) {
        SearchRequest nameSearchRequest = new SearchRequest(esIndex);
        MultiMatchQueryBuilder nameMatchQueryBuilder = QueryBuilders.multiMatchQuery(query, "FirstName", "LastName");
        SearchSourceBuilder nameSearchSourceBuilder = new SearchSourceBuilder();
        nameSearchSourceBuilder.query(nameMatchQueryBuilder);
        nameSearchRequest.source(nameSearchSourceBuilder);
        return searchRequestToDto(nameSearchRequest);
    }

    public List<EmployeeResponseDto> searchByInterest(String query) {
        SearchRequest interestSearchRequest = new SearchRequest(esIndex);
        MultiMatchQueryBuilder interestMatchQueryBuilder = QueryBuilders.multiMatchQuery(query, "Interests");
        SearchSourceBuilder interestSearchSourceBuilder = new SearchSourceBuilder();
        interestSearchSourceBuilder.query(interestMatchQueryBuilder);
        interestSearchRequest.source(interestSearchSourceBuilder);
        return searchRequestToDto(interestSearchRequest);
    }

    public List<EmployeeResponseDto> searchByAll(String query) {
        SearchRequest allSearchRequest = new SearchRequest(esIndex);
        String numQuery = query.replaceAll("[^0-9]","");
        String dateQuery = query.replaceAll("[^0-9\\-]","");
        boolean dateQueryValid = true;
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.parse(dateQuery);
        }
        catch (ParseException exception){
            dateQueryValid = false;
        }
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().minimumShouldMatch(1);
        MultiMatchQueryBuilder textMatchQueryBuilder = QueryBuilders.multiMatchQuery(query,
                "FirstName",
                "LastName",
                "Designation",
                "Address",
                "Gender",
                "MaritalStatus",
                "Interests");
        boolQueryBuilder = boolQueryBuilder.should(textMatchQueryBuilder);
        if (!numQuery.isEmpty()){
            TermQueryBuilder ageQueryBuilder = QueryBuilders.termQuery("Age", numQuery);
            boolQueryBuilder = boolQueryBuilder.should(ageQueryBuilder);
            TermQueryBuilder salaryQueryBuilder = QueryBuilders.termQuery("Salary", numQuery);
            boolQueryBuilder = boolQueryBuilder.should(salaryQueryBuilder);
        }
        if (dateQueryValid){
            TermQueryBuilder dateQueryBuilder = QueryBuilders.termQuery("DateOfJoining", dateQuery);
            boolQueryBuilder = boolQueryBuilder.should(dateQueryBuilder);
        }
        SearchSourceBuilder allSearchSourceBuilder = new SearchSourceBuilder();
        allSearchSourceBuilder.query(boolQueryBuilder);
        allSearchRequest.source(allSearchSourceBuilder);
        return searchRequestToDto(allSearchRequest);
    }

    private List<EmployeeResponseDto> searchRequestToDto(SearchRequest searchRequest){
        List<EmployeeResponseDto> searchHitsEmployeeResponseDtoList = new ArrayList<>();
        SearchResponse searchResponse;
        try {
            searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            for (StackTraceElement stackTraceElement : exception.getStackTrace()){
                log.info(stackTraceElement.toString());
            }
            return searchHitsEmployeeResponseDtoList;
        }
        SearchHits searchHits = searchResponse.getHits();
        for (SearchHit searchHit: searchHits.getHits()){
            searchHitsEmployeeResponseDtoList.add(employeeDtoMapper.toResponseDto(convertMapToEmployee(searchHit.getSourceAsMap())));
        }
        return searchHitsEmployeeResponseDtoList;
    }

    public EmployeeResponseDto findById(String id) {
        GetRequest getRequest = new GetRequest(esIndex);
        getRequest.id(id);
        try{
            UUID uuid = UUID.fromString(id);
        }
        catch(IllegalArgumentException exception){
            throw new EmployeeNotFoundException();
        }
        try {
            GetResponse getResponse = elasticsearchClient.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()){
                return employeeDtoMapper.toResponseDto(convertMapToEmployee(getResponse.getSourceAsMap()));
            }
            else{
                throw new EmployeeNotFoundException(id);
            }
        }
        catch (IOException exception){
            log.error(exception.getMessage());
            for (StackTraceElement stackTraceElement : exception.getStackTrace()){
                log.info(stackTraceElement.toString());
            }
            throw new EmployeeNotFoundException(id);
        }
    }

    public List<EmployeeResponseDto> findAll() {
        List<EmployeeResponseDto> allEmployeeResponseDtoList = new ArrayList<>();
        SearchRequest allSearchRequest = new SearchRequest(esIndex);
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        SearchSourceBuilder allSearchSourceBuilder = new SearchSourceBuilder();
        allSearchSourceBuilder.query(matchAllQueryBuilder);
        allSearchRequest.source(allSearchSourceBuilder);
        return searchRequestToDto(allSearchRequest);
    }

    public String clearData(){
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(esIndex);
        deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
        try {
            BulkByScrollResponse bulkResponse = elasticsearchClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
            if (bulkResponse.getBulkFailures().isEmpty()){
                return "success";
            }
            else{
                return "failure";
            }
        }
        catch (IOException exception){
            log.error(exception.getMessage());
            for (StackTraceElement stackTraceElement : exception.getStackTrace()){
                log.info(stackTraceElement.toString());
            }
            return "failure";
        }
    }

    private Map<String, Object> convertEmployeeToMap(Employee employee) {
        return objectMapper.convertValue(employee, Map.class);
    }

    private Employee convertMapToEmployee(Map<String, Object> map){
        return objectMapper.convertValue(map,Employee.class);
    }
}
