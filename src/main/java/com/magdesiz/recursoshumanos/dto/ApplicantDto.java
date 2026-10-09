package com.magdesiz.recursoshumanos.dto;

public class ApplicantDto {

    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private int age;

    public ApplicantDto(int id, String firstName, String lastName, String address, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }
}
