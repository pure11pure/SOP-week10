package com.example.productsservice.rest;

import lombok.Data;

import java.math.BigDecimal;

@Data //ช่วยทำ constructor
public class CreateProductRestModel {
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
