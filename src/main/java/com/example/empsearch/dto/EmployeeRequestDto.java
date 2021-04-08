package com.example.empsearch.dto;

public class EmployeeRequestDto {
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
        return "EmployeeRequestDto{" +
                "FirstName='" + FirstName + '\'' +
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

    public EmployeeRequestDto(String FirstName, String LastName, String Designation, Long Salary, String DateOfJoining, String Address, String Gender, Long Age, String MaritalStatus, String Interests) {
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
