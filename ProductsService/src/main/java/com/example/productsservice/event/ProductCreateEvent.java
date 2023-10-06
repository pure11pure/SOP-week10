package com.example.productsservice.event;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCreateEvent {
    //เวลาที่มีงานเข้ามา ต้องมีอะไรบ้าง
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
