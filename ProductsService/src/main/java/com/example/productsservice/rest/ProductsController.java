package com.example.productsservice.rest;

import com.example.productsservice.command.CreateProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


//Axon 8025
//Eureka 8761

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final Environment env;
    private final CommandGateway commandGateway;


    @Autowired
    public ProductsController(Environment env, CommandGateway commandGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createProduct(@RequestBody CreateProductRestModel model){
        //การสร้างคำสั่ง โดยรับข้อมูลมาจาก user
        CreateProductCommand command = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .title(model.getTitle())
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .build();

        String result;
        try{
            //รอผลลัพท์ตอบกลับ
            result = commandGateway.sendAndWait(command);
        }catch (Exception e){
            result = e.getLocalizedMessage();
        }
        return result; //ส่งกลับ user
    }

    @GetMapping
    public String getProduct(){
        return "HTTP GET Handled " + env.getProperty("local.server.port");
    }
    @PutMapping
    public String putProduct(){
        return "HTTP PUT Handled";
    }
    @DeleteMapping
    public String deleteProduct(){
        return "HTTP DELETE Handled";
    }
}


