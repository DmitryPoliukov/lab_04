package com.epam.esm.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.epam.esm.exception.PermissionException;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.repository.dto.CertificateDto;
import com.epam.esm.repository.dto.OrderDto;
import com.epam.esm.repository.dto.TagDto;
import com.epam.esm.repository.dto.UserDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UserService userService;
    private final HateoasAdder<OrderDto> hateoasAdder;
    private final HateoasAdder<UserDto> userDtoHateoasAdder;
    private final HateoasAdder<CertificateDto> certificateDtoHateoasAdder;
    private final HateoasAdder<TagDto> tagDtoHateoasAdder;

    @Autowired
    public OrderController(OrderService orderService,
                           UserService userService, HateoasAdder<OrderDto> hateoasAdder,
                           HateoasAdder<UserDto> userDtoHateoasAdder,
                           HateoasAdder<CertificateDto> certificateDtoHateoasAdder,
                           HateoasAdder<TagDto> tagDtoHateoasAdder) {
        this.orderService = orderService;
        this.userService = userService;
        this.hateoasAdder = hateoasAdder;
        this.userDtoHateoasAdder = userDtoHateoasAdder;
        this.certificateDtoHateoasAdder = certificateDtoHateoasAdder;
        this.tagDtoHateoasAdder = tagDtoHateoasAdder;
    }


    private static final String JWT_SECRET = "secret";

    private static final String PERMISSION_MESSAGE = "You don't have permission to do that";

    /**
     * Method for saving new order.
     *
     * @param order order entity for saving
     * @return created order with hateoas
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestHeader("Authorization") String authorizationHeader,
                                @RequestBody @Valid OrderDto order) {
        if (isEmailsEquals(authorizationHeader, order)) {
            OrderDto addedOrder = orderService.create(order);
            hateoasAdder.addLinks(addedOrder);
            return addedOrder;
        } else {
            throw new PermissionException(PERMISSION_MESSAGE);
        }
    }

    /**
     * Method for getting order by ID.
     *
     * @param id ID of order
     * @return Found order entity with hateoas
     */
    @GetMapping("/{id}")
    public OrderDto readOrder(@RequestHeader("Authorization") String authorizationHeader,
                              @PathVariable("id") int id) {
        OrderDto order = orderService.readOrder(id);
        if (isEmailsEquals(authorizationHeader, order)) {
            hateoasAdder.addLinks(order);
            userDtoHateoasAdder.addLinks(order.getUserDto());
            certificateDtoHateoasAdder.addLinks(order.getCertificateDto());
            order.getCertificateDto().getTagsDto()
                    .forEach(tagDtoHateoasAdder::addLinks);
            return order;
        } else {
            throw new PermissionException(PERMISSION_MESSAGE);
        }
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
    public List<OrderDto> ordersByUserId(@RequestHeader("Authorization") String authorizationHeader,
                                         @PathVariable int userId,
                                         @RequestParam(value = "page", defaultValue = "1", required = false) @Min(1) int page,
                                         @RequestParam(value = "size", defaultValue = "5", required = false) @Min(1) int size) {
        String emailFromPath = userService.read(userId).getEmail();
        String emailFromAuth = getEmailFromHeader(authorizationHeader);
        if (emailFromAuth.equals(emailFromPath)) {
            List<OrderDto> orders = orderService.readAllByUserId(userId, page, size);

            return orders.stream()
                    .peek(orderDto -> userDtoHateoasAdder.addLinks(orderDto.getUserDto()))
                    .peek(orderDto -> certificateDtoHateoasAdder.addLinks(orderDto.getCertificateDto()))
                    .peek(orderDto -> orderDto.getCertificateDto().getTagsDto().forEach(tagDtoHateoasAdder::addLinks))
                    .peek(hateoasAdder::addLinks)
                    .collect(Collectors.toList());
        } else {
            throw new PermissionException(PERMISSION_MESSAGE);
        }
    }

    private boolean isEmailsEquals(String authorizationHeader, OrderDto orderDto) {
        String emailFromToken = getEmailFromHeader(authorizationHeader);
        return emailFromToken.equals(orderDto.getUserDto().getEmail());
    }

    private String getEmailFromHeader(String authorizationHeader) {
        final String BEARER = "Bearer ";
        String token = authorizationHeader.substring(BEARER.length());
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }
}
