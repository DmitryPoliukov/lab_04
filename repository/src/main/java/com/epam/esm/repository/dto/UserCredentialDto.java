package com.epam.esm.repository.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCredentialDto {

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String token;

    public UserCredentialDto() {
    }

    public UserCredentialDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserCredentialDto(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCredentialDto userCredential = (UserCredentialDto) o;

        if (email != null ? !email.equals(userCredential.email) : userCredential.email != null) return false;
        if (password != null ? !password.equals(userCredential.password) : userCredential.password != null) return false;
        return token != null ? token.equals(userCredential.token) : userCredential.token == null;

    }

    @Override
    public int hashCode() {
        int result = 1;
        int prime = 31;
        result = prime * result + (email != null ? email.hashCode() : 0);
        result = prime * result + (password != null ? password.hashCode() : 0);
        result = prime * result + (token != null ? token.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
