package com.example.productsservice.command;


import com.example.productsservice.event.ProductCreateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate(){

    }


    @CommandHandler  //รอฟังคำสั่ง ถ้ามีจะต้องรับรู้ด้วย  === command gateway
    public ProductAggregate(CreateProductCommand createProductCommand){

        //สร้างเงื่อนไขให้กับข้อมูลที่เข้ามา
        //1.ราคาต้องมีค่ามากกว่าเท่ากับ 0
        if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be less than or equal to zero");
        }
        //2.title มีค่าไหม / ห้ามเป็น null
        if(createProductCommand.getTitle() == null || createProductCommand.getTitle().isBlank()){
            throw new IllegalArgumentException("Title cannot be empty");
        }

        ProductCreateEvent productCreateEvent = new ProductCreateEvent();
        //การ copy ข้อมูลที่ได้มาใส่่ใน productCreateEvent === ซึ่งเป็นวิธีเดียวกับไฟล์ ProductsController.java ใน CreateProductCommand command
        BeanUtils.copyProperties(createProductCommand, productCreateEvent);
        //ทำการส่ง/โยน
        AggregateLifecycle.apply(productCreateEvent);
    }

    @EventSourcingHandler //รอฟัง event เพื่อจะอัพเดทตัวมันเอง เพื่อให้ได้ข้อมูลที่เป็น ปจบ.ตลอดเวลา
    public void on(ProductCreateEvent productCreateEvent){
        System.out.println(("ON AGGREGATE"));
        this.productId = productCreateEvent.getProductId();
        this.title = productCreateEvent.getTitle();
        this.price = productCreateEvent.getPrice();
        this.quantity = productCreateEvent.getQuantity();
    }
}
