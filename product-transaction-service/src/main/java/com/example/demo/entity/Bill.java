package com.example.demo.entity;

import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bill")
public class Bill {
    @Id
    private String id;
    @Nullable
    private LocalDate payDate;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal totalPrice;
    private BillStatus status;

    @DocumentReference(lazy = true)
    private Order order;

}
