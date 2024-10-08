package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "shipment")
public class Ship {

    @Id
    private String id;
    private OrderStatus status;
    private LocalDate receivedDate;

    @DocumentReference(lazy = true)
    private User user;

    @DocumentReference(lazy = true)
    private List<Order> orders;
}
