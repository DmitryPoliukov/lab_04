package com.epam.esm.repository.dao;

import com.epam.esm.repository.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Order create(Order order);

    List<Order> readAllByUserId(int userId, int page, int size);

    Optional<Order> readOrder(int orderId);
}
