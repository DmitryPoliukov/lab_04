package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.repository.dto.CertificateDto;
import com.epam.esm.repository.dto.OrderDto;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class {@code OrderHateoasAdder} is implementation of interface {@link HateoasAdder}
 * and intended to work with {@link OrderDto} objects.
 *
 * @author Dmitry Poliukov
 */
@Component
public class OrderHateoasAdder implements HateoasAdder<OrderDto> {

    private static final Class<OrderController> CONTROLLER = OrderController.class;
    @Override
    public void addLinks(OrderDto orderDto) {
        orderDto.add(linkTo(methodOn(CONTROLLER).createOrder(orderDto)).withRel("new"));
    }
}
