package com.example.empsearch.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.UUID;

public class Employee {
    private String Id;
    private String FirstName;
    private String LastName;
    private String Designation;
    private Long Salary;
    private String DateOfJoining;
    private String Address;
    private String Gender;
    private Long Age;
    private String MaritalStatus;
    private String Interests;

    @JsonGetter("id")
    public String getId() {
        return Id;
    }

    @JsonSetter("id")
    public void setId(String Id) {
        this.Id = Id;
    }

    @JsonGetter("FirstName")
    public String getFirstName() {
        return FirstName;
    }

    @JsonSetter("FirstName")
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    @JsonGetter("LastName")
    public String getLastName() {
        return LastName;
    }

    @JsonSetter("LastName")
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    @JsonGetter("Designation")
    public String getDesignation() {
        return Designation;
    }

    @JsonSetter("Designation")
    public void setDesignation(String Designation) {
        this.Designation = Designation;
    }

    @JsonGetter("Salary")
    public Long getSalary() {
        return Salary;
    }

    @JsonSetter("Salary")
    public void setSalary(Long Salary) {
        this.Salary = Salary;
    }

    @JsonGetter("DateOfJoining")
    public String getDateOfJoining() {
        return DateOfJoining;
    }

    @JsonSetter("DateOfJoining")
    public void setDateOfJoining(String DateOfJoining) {
        this.DateOfJoining = DateOfJoining;
    }

    @JsonGetter("Address")
    public String getAddress() {
        return Address;
    }

    @JsonSetter("Address")
    public void setAddress(String Address) {
        this.Address = Address;
    }

    @JsonGetter("Gender")
    public String getGender() {
        return Gender;
    }

    @JsonSetter("Gender")
    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    @JsonGetter("Age")
    public Long getAge() {
        return Age;
    }

    @JsonSetter("Age")
    public void setAge(Long Age) {
        this.Age = Age;
    }

    @JsonGetter("MaritalStatus")
    public String getMaritalStatus() {
        return MaritalStatus;
    }

    @JsonSetter("MaritalStatus")
    public void setMaritalStatus(String MaritalStatus) {
        this.MaritalStatus = MaritalStatus;
    }

    @JsonGetter("Interests")
    public String getInterests() {
        return Interests;
    }

    @JsonSetter("Interests")
    public void setInterests(String Interests) {
        this.Interests = Interests;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "Id='" + Id + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Designation='" + Designation + '\'' +
                ", Salary=" + Salary +
                ", DateOfJoining=" + DateOfJoining +
                ", Address='" + Address + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Age=" + Age +
                ", MaritalStatus='" + MaritalStatus + '\'' +
                ", Interests='" + Interests + '\'' +
                '}';
    }

    public Employee(String FirstName, String LastName, String Designation, Long Salary, String DateOfJoining, String Address, String Gender, Long Age, String MaritalStatus, String Interests) {
        this.Id = UUID.randomUUID().toString();
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Designation = Designation;
        this.Salary = Salary;
        this.DateOfJoining = DateOfJoining;
        this.Address = Address;
        this.Gender = Gender;
        this.Age = Age;
        this.MaritalStatus = MaritalStatus;
        this.Interests = Interests;
    }
}
