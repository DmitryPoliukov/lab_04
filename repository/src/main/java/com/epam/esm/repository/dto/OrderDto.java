package com.epam.esm.repository.dto;

import com.epam.esm.repository.entity.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
public class OrderDto extends RepresentationModel<OrderDto> {

    private int id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private LocalDateTime createDate;

    @JsonProperty("user")
    private UserDto userDto;

    @JsonProperty("certificate")
    private CertificateDto certificateDto;

    @PositiveOrZero(message = "Order's cost should positive or zero")
    private double cost;

    public OrderDto() {
    }

    public CertificateDto getCertificateDto() {
        return certificateDto;
    }

    public void setCertificateDto(CertificateDto certificateDto) {
        this.certificateDto = certificateDto;
    }

    public Order toEntity() {
        Order entityOrder = new Order();
        entityOrder.setId(this.id);
        entityOrder.setCreateDate(this.getCreateDate());
        entityOrder.setUser(this.getUserDto().toEntity());
        entityOrder.setCertificate(this.getCertificateDto().toEntity());
        entityOrder.setCost(this.getCost());
        return entityOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDto orderDto = (OrderDto) o;

        if (id != orderDto.id) return false;
        if (createDate != null ? !createDate.equals(orderDto.createDate) : orderDto.createDate != null)
            return false;
        if (userDto != null ? !userDto.equals(orderDto.userDto) : orderDto.userDto != null) return false;
        if (cost != orderDto.cost) return false;

        return certificateDto != null
                ? certificateDto.equals(orderDto.certificateDto)
                : orderDto.certificateDto == null;
    }

    @Override
    public int hashCode() {
        int result = 1;
        int prime = 31;
        result = prime * result + id;
        result = prime * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (userDto != null ? userDto.hashCode() : 0);
        result = prime * result + (certificateDto != null ? certificateDto.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", createDate=").append(createDate);
        sb.append(", certificate=").append(certificateDto);
        sb.append(", user=").append(userDto);
        sb.append('}');
        return sb.toString();
    }
}
