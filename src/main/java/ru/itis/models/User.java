package ru.itis.models;

import lombok.*;
import ru.itis.dto.UserDto;

@EqualsAndHashCode
@Builder
@ToString
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String fullAbout;
    private String shortAbout;
    private String city;
    private String gender;
    private Integer age;
    private Integer countLikes;
    private String email;
    private String hashPassword;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getCountLikes() {
        return countLikes;
    }

    public void setCountLikes(Integer countLikes) {
        this.countLikes = countLikes;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getShortAbout() {
        return shortAbout;
    }

    public void setShortAbout(String shortAbout) {
        this.shortAbout = shortAbout;
    }

    public String getFullAbout() {
        return fullAbout;
    }

    public void setFullAbout(String fullAbout) {
        this.fullAbout = fullAbout;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserDto toUserDto() {
        return UserDto.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .age(this.age)
                .countLikes(this.countLikes)
                .city(this.city)
                .fullAbout(this.fullAbout)
                .shortAbout(this.shortAbout)
                .gender(this.gender)
                .id(this.id)
                .role(this.role)
                .build();
    }
}

