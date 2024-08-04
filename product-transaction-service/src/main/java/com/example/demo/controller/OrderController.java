package com.example.demo.controller;

import com.example.demo.dto.OrderPostDto;
import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @GetMapping("")
    public ResponseEntity<List<Order>> getAllOrder() {
        return ResponseEntity.ok(orderService.getAllOrder());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Order>> searchOrder(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                   @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(orderService.searchOrder(from, to));
    }

    @PostMapping("")
    public ResponseEntity<List<Order>> createOrders(@RequestBody OrderPostDto dto) {
        return new ResponseEntity<>(orderService.placeOrders(dto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable("id") String orderId) {
        return new ResponseEntity<>(orderService.cancelOrder(orderId), HttpStatus.OK);
    }
}