package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.CertificateDao;
import com.epam.esm.repository.dao.OrderDao;
import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.repository.dto.OrderDto;
import com.epam.esm.repository.entity.Certificate;
import com.epam.esm.repository.entity.Order;
import com.epam.esm.repository.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.IncorrectParameterException;
import com.epam.esm.service.exception.ResourceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final UserDao userDao;
    private final OrderDao orderDao;
    private final CertificateDao certificateDao;

    public OrderServiceImpl(
            UserDao userDao, OrderDao orderDao, CertificateDao certificateDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.certificateDao = certificateDao;
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        if (orderDto == null) {
            throw new IncorrectParameterException("Null parameter in create order");
        }
        Optional<User> optionalUser = userDao.read(orderDto.getUserDto().getId());
        if (optionalUser.isEmpty()) {
            ResourceException.notFoundWithUser(orderDto.getUserDto().getId());
        }

        Optional<Certificate> optionalCertificate = certificateDao.read(orderDto.getCertificateDto().getId());
        if (optionalCertificate.isEmpty()) {
            ResourceException.notFoundWithCertificateId(orderDto.getCertificateDto().getId());
        }
        orderDto.setCreateDate(LocalDateTime.now());
        Order order = orderDto.toEntity();
        return orderDao.create(order).toDto();
    }

    @Override
    public List<OrderDto> readAllByUserId(int userId, int page, int size) {
        Optional<User> optionalUser = userDao.read(userId);
        if (optionalUser.isEmpty()) {
            ResourceException.notFoundWithUser(userId);
        }

        return orderDao.readAllByUserId(userId, page, size).stream()
                .map(Order::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto readOrder(int orderId) {
        Optional<Order> order = orderDao.readOrder(orderId);
        return order.orElseThrow(ResourceException.notFoundWithOrder(orderId)).toDto();
    }
}
