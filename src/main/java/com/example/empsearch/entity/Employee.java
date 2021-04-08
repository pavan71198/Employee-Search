package com.example.empsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "companydatabase")
public class Employee {
    @Id
    private String Id;
    @Field (type = FieldType.Text, name="FirstName")
    private String FirstName;
    @Field (type = FieldType.Text, name="LastName")
    private String LastName;
    @Field (type = FieldType.Text, name="Designation")
    private String Designation;
    @Field (type = FieldType.Long, name="Salary")
    private Long Salary;
    @Field (type = FieldType.Date, name="DateOfJoining")
    private String DateOfJoining;
    @Field (type = FieldType.Text, name="Address")
    private String Address;
    @Field (type = FieldType.Text, name="Gender")
    private String Gender;
    @Field (type = FieldType.Long, name="Age")
    private Long Age;
    @Field (type = FieldType.Text, name="MaritalStatus")
    private String MaritalStatus;
    @Field (type = FieldType.Text, name="Interests")
    private String Interests;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String Designation) {
        this.Designation = Designation;
    }

    public Long getSalary() {
        return Salary;
    }

    public void setSalary(Long Salary) {
        this.Salary = Salary;
    }

    public String getDateOfJoining() {
        return DateOfJoining;
    }

    public void setDateOfJoining(String DateOfJoining) {
        this.DateOfJoining = DateOfJoining;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public Long getAge() {
        return Age;
    }

    public void setAge(Long Age) {
        this.Age = Age;
    }

    public String getMaritalStatus() {
        return MaritalStatus;
    }

    public void setMaritalStatus(String MaritalStatus) {
        this.MaritalStatus = MaritalStatus;
    }

    public String getInterests() {
        return Interests;
    }

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
