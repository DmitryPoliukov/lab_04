package com.epam.esm.controller;

import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.repository.dto.CertificateDto;
import com.epam.esm.repository.dto.OrderDto;
import com.epam.esm.repository.dto.TagDto;
import com.epam.esm.repository.dto.UserDto;
import com.epam.esm.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class {@code OrderController} is an endpoint of the API which allows to perform operations on orders.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/orders".
 * So that {@code OrderController} is accessed by sending request to /orders.
 *
 * @author Dmitry Poliukov
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final HateoasAdder<OrderDto> hateoasAdder;
    private final HateoasAdder<UserDto> userDtoHateoasAdder;
    private final HateoasAdder<CertificateDto> certificateDtoHateoasAdder;
    private final HateoasAdder<TagDto> tagDtoHateoasAdder;

    public OrderController(OrderService orderService,
                           HateoasAdder<OrderDto> hateoasAdder,
                           HateoasAdder<UserDto> userDtoHateoasAdder,
                           HateoasAdder<CertificateDto> certificateDtoHateoasAdder,
                           HateoasAdder<TagDto> tagDtoHateoasAdder) {
        this.orderService = orderService;
        this.hateoasAdder = hateoasAdder;
        this.userDtoHateoasAdder = userDtoHateoasAdder;
        this.certificateDtoHateoasAdder = certificateDtoHateoasAdder;
        this.tagDtoHateoasAdder = tagDtoHateoasAdder;
    }

    /**
     * Method for saving new order.
     *
     * @param order order entity for saving
     * @return created order with hateoas
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestBody @Valid OrderDto order) {
        OrderDto addedOrder = orderService.create(order);
        hateoasAdder.addLinks(addedOrder);
        return addedOrder;
    }

    /**
     * Method for getting order by ID.
     *
     * @param id ID of order
     * @return Found order entity with hateoas
     */
    @GetMapping("/{id}")
    public OrderDto readOrder(@PathVariable("id") int id) {
        OrderDto order = orderService.readOrder(id);
        hateoasAdder.addLinks(order);
        userDtoHateoasAdder.addLinks(order.getUserDto());
        certificateDtoHateoasAdder.addLinks(order.getCertificateDto());
        order.getCertificateDto().getTagsDto().stream()
                .peek(tagDtoHateoasAdder::addLinks)
                .collect(Collectors.toList());
        return order;
    }

    /**
     * Method for getting orders by user ID.
     *
     * @param userId ID of user
     * @param page   the number of page for pagination
     * @param size   the size of page for pagination
     * @return Found list of orders with hateoas
     */
    @GetMapping("/users/{userId}")
    public List<OrderDto> ordersByUserId(@PathVariable int userId,
                                         @RequestParam(value = "page", defaultValue = "1", required = false) @Min(1) int page,
                                         @RequestParam(value = "size", defaultValue = "5", required = false) @Min(1) int size) {
        List<OrderDto> orders = orderService.readAllByUserId(userId, page, size);

        return orders.stream()
                .peek(orderDto -> userDtoHateoasAdder.addLinks(orderDto.getUserDto()))
                .peek(orderDto -> certificateDtoHateoasAdder.addLinks(orderDto.getCertificateDto()))
                .peek(orderDto -> orderDto.getCertificateDto().getTagsDto().stream().peek(tagDtoHateoasAdder::addLinks)
                                        .collect(Collectors.toList()))
                .peek(hateoasAdder::addLinks)
                .collect(Collectors.toList());
    }
}
