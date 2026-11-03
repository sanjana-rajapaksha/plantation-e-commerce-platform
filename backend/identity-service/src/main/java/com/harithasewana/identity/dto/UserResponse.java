package com.harithasewana.identity.dto;

public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    /*Setters: Used when converting User entity into UserResponse.
      Getters: Used by Jackson to read the DTO properties and generate JSON.*/
}
