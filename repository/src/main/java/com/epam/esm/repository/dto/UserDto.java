package com.epam.esm.repository.dto;

import com.epam.esm.repository.entity.Role;
import com.epam.esm.repository.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class UserDto extends RepresentationModel<UserDto> {

    private int id;

    @Size(min = 1,max = 45, message = "User name length should be from 1 to 45")
    private String name;

    @Size(min = 1,max = 45, message = "User surname length should be from 1 to 45")
    private String surname;

    private String email;

    private String password;

    private Role role;

    @JsonIgnore
    private List<OrderDto> ordersDto = new ArrayList<>();

    public UserDto() {}

    public User toEntity() {
        User entityUser = new User();
        entityUser.setId(this.id);
        entityUser.setName(this.name);
        entityUser.setSurname(this.surname);
        entityUser.setEmail(this.email);
        entityUser.setPassword(this.password);
        entityUser.setRole(this.role);
        return entityUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<OrderDto> getOrdersDto() {
        return ordersDto;
    }

    public void setOrdersDto(List<OrderDto> ordersDto) {
        this.ordersDto = ordersDto;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (id != userDto.id) return false;
        if (name != null ? !name.equals(userDto.name) : userDto.name != null) return false;
        if (surname != null ? !surname.equals(userDto.surname) : userDto.surname != null) return false;
        if (email != null ? !email.equals(userDto.email) : userDto.email != null) return false;
        if (password != null ? !password.equals(userDto.password) : userDto.password != null) return false;
        if (role != null ? !role.equals(userDto.role) : userDto.role != null) return false;
        return ordersDto != null ? ordersDto.equals(userDto.ordersDto) : userDto.ordersDto == null;

    }

    @Override
    public int hashCode() {
        int result = 1;
        int prime = 31;
        result = prime * result + id;
        result = prime * result + (name != null ? name.hashCode() : 0);
        result = prime * result + (surname != null ? surname.hashCode() : 0);
        result = prime * result + (email != null ? email.hashCode() : 0);
        result = prime * result + (password != null ? password.hashCode() : 0);
        result = prime * result + (role != null ? role.hashCode() : 0);
        result = prime * result + (ordersDto != null ? ordersDto.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", role='").append(role).append('\'');
        sb.append(", orders=").append(ordersDto);
        sb.append('}');
        return sb.toString();
    }

}
