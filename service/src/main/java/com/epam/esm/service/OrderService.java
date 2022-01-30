package com.epam.esm.service;

import com.epam.esm.repository.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto create(OrderDto orderDto);

    List<OrderDto> readAllByUserId(int userId, int page, int size);

    OrderDto readOrder(int orderId);


}
