package com.epam.esm.repository.entity;

import com.epam.esm.repository.dto.OrderDto;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Audited
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    @Column(name = "cost")
    private double cost;

    public Order() {}

    public Order(int id, LocalDateTime createDate, User user, Certificate certificate, double cost) {
        this.id = id;
        this.createDate = createDate;
        this.user = user;
        this.certificate = certificate;
        this.cost = cost;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public OrderDto toDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(this.id);
        orderDto.setCreateDate(this.createDate);
        orderDto.setUserDto(this.user.toDto());
        orderDto.setCertificateDto(this.certificate.toDto());
        orderDto.setCost(this.cost);
        return orderDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (createDate != null ? !createDate.equals(order.createDate) : order.createDate != null)
            return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (cost != order.cost) return false;

        return certificate != null
                ? certificate.equals(order.certificate)
                : order.certificate == null;
    }

    @Override
    public int hashCode() {
        int result = 1;
        int prime = 31;
        result = prime * result + id;
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        //result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (certificate != null ? certificate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", createDate=").append(createDate);
        sb.append(", certificate=").append(certificate);
        sb.append('}');
        return sb.toString();
    }




}
