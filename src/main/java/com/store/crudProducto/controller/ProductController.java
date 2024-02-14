package com.store.crudProducto.controller;


import com.store.crudProducto.dto.ProductDto;
import com.store.crudProducto.entity.Product;
import com.store.crudProducto.service.ProductService;
import com.store.global.dto.MessageDto;
import com.store.global.exceptions.AttributeException;
import com.store.global.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
       return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable("id") int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(productService.getOneProduct(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deleteProduct(@PathVariable("id") int id) throws ResourceNotFoundException {

        Product product=productService.deleteProduct(id);
        String message="Product "+ product.getName()+" have been deleted";

        return ResponseEntity.ok(new MessageDto(HttpStatus.OK,message));
    }

    @PostMapping
    public ResponseEntity<MessageDto> saveProduct(@Valid @RequestBody ProductDto productDto) throws AttributeException{

        Product product=productService.saveProduct(productDto);
        String message="Product "+ product.getPrice()+ "have been saved";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK,message));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> updateProduct (@PathVariable("id") int id,@Valid @RequestBody ProductDto productDto) throws ResourceNotFoundException, AttributeException {

        Product product=productService.update(id, productDto);
        String message="Product "+ product.getName()+" have been updated";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK,message));
    }
}
