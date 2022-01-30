package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.CertificateDao;
import com.epam.esm.repository.dao.OrderDao;
import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.repository.dao.impl.CertificateDaoImpl;
import com.epam.esm.repository.dao.impl.OrderDaoImpl;
import com.epam.esm.repository.dao.impl.UserDaoImpl;
import com.epam.esm.repository.dto.OrderDto;
import com.epam.esm.repository.entity.Certificate;
import com.epam.esm.repository.entity.Order;
import com.epam.esm.repository.entity.Tag;
import com.epam.esm.repository.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    private OrderDao orderDao = Mockito.mock(OrderDaoImpl.class);
    private UserDao userDao = Mockito.mock(UserDaoImpl.class);
    private CertificateDao certificateDao = Mockito.mock(CertificateDaoImpl.class);

    private OrderServiceImpl orderService = new OrderServiceImpl(userDao, orderDao, certificateDao);
    private static final LocalDateTime UPDATED_DATE = LocalDateTime.parse("2018-08-29T06:12:15.156");

    private static final Order ORDER_1 = new Order(1, UPDATED_DATE,  new User("name1", "sname1"),
            new Certificate(1, "giftCertificate1", "description1", 10.1,
                    1, LocalDateTime.parse("2020-08-29T06:12:15"), LocalDateTime.parse("2020-08-29T06:12:15"), new ArrayList<Tag>()), 15.2);
    private static final Order ORDER_2 = new Order(2, UPDATED_DATE, new User("name1", "sname1"),
            new Certificate(2, "giftCertificate3", "description3", 30.3,
                    3, LocalDateTime.parse("2019-08-29T06:12:15"), LocalDateTime.parse("2019-08-29T06:12:15"), new ArrayList<Tag>()) ,30.4);

    private static final int PAGE = 0;
    private static final int SIZE = 5;


    @Test
    void readAllByUserId() {
        List<Order> orders = Arrays.asList(ORDER_1, ORDER_2);
        when(orderDao.readAllByUserId(ORDER_1.getUser().getId(), PAGE, SIZE)).thenReturn(orders);

        List<Order> actual = orderService.readAllByUserId(ORDER_1.getUser().getId(), PAGE, SIZE).stream()
                .map(OrderDto::toEntity).collect(Collectors.toList());
        List<Order> expected = orders;

        assertEquals(expected, actual);
    }

    @Test
    void readOrder() {
        when(orderDao.readOrder(ORDER_1.getId())).thenReturn(Optional.of(ORDER_1));

        Order actual = orderService.readOrder(ORDER_1.getId()).toEntity();
        Order expected = ORDER_1;

        assertEquals(expected, actual);
    }
}