package com.example.demo.service;

import com.example.demo.dto.ShipPostDto;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.Ship;
import com.example.demo.exception.InvalidInputParameter;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShipRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ShipService {
    private ShipRepository shipRepository;
    private OrderRepository orderRepository;

    public List<Ship> getAllShip() {
        return shipRepository.findAll();
    }

    public List<Ship> searchShippingOrder(LocalDate from, LocalDate to) {
        return shipRepository.searchShippingOrder(from, to);
    }

    public Ship checkExistShip(String shipId) {
        return shipRepository.findById(shipId).orElseThrow(() ->
                new NotFoundException("Order's Shipping with id " + shipId + " doesn't exist."));
    }

    public Ship createShip(ShipPostDto dto) {
        if (dto.orders().isEmpty()) throw new InvalidInputParameter("Field 'orders' must not empty.");
        dto.orders().forEach(orderId -> {
            orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order with id " + orderId + " doesn't exist."));
        });
        List<Order> orders = orderRepository.findAllById(dto.orders());
        Ship newShip = Ship.builder().status(OrderStatus.PROCESSING).orders(orders).build();
        return shipRepository.save(newShip);
    }

    public Ship confirmShippedOrder(String shipId) {
        Ship ship = checkExistShip(shipId);
        ship.setStatus(OrderStatus.DONE);
        ship.setReceivedDate(LocalDate.now());
        ship.getOrders().forEach(order -> {
            order.setStatus(OrderStatus.DONE);
        });
        orderRepository.saveAll(ship.getOrders());
        return shipRepository.save(ship);
    }

    public void deleteOrderShipping(String shipId) {
        checkExistShip(shipId);
        shipRepository.deleteById(shipId);
    }
}
