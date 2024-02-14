package com.store.crudProducto.service;


import com.store.crudProducto.dto.ProductDto;
import com.store.crudProducto.entity.Product;
import com.store.crudProducto.repository.ProductRepository;
import com.store.global.exceptions.AttributeException;
import com.store.global.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;


    public List<Product> getAll() {

        return productRepository.findAll();
    }

    public Product getOneProduct(int id) throws ResourceNotFoundException{
        return productRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Product not found")
        );
    }

    public Product saveProduct(ProductDto productDto) throws AttributeException {

        if(productRepository.existsByName(productDto.getName())){
            throw new AttributeException("Name already in use");
        }

        int id=autoIncrementId();
        Product product=new Product(id,productDto.getName(),productDto.getPrice());
        return productRepository.save(product);
    }

    public Product updateProduct(int idProduct, ProductDto productDto){

        Product product= productRepository.findById(idProduct).get();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());

        return productRepository.save(product);
    }

    public Product deleteProduct(int idProduct) throws ResourceNotFoundException {
        Product product=productRepository.findById(idProduct).orElseThrow(
                ()-> new ResourceNotFoundException("Product not found")
        );
        productRepository.delete(product);
        return product;
    }


    private int autoIncrementId(){
        List<Product> productList=productRepository.findAll();

        /*
        //Programacion Declarativa

        //For-each
        int idMax=0;
        for (Product product:productList){
            int id=product.getId();
            if(idMax<id){
                idMax=id;
            }
        }

        for (int i=0;i<productList.size();i++){
            int id=productList.get(i).getId();
            if(idMax<id){
                idMax=id;
            }
        }
        return idMax+1;
         */

        //Programacion Funcional
        return productList.isEmpty()?1:productList.stream()
                .max(Comparator.comparing(Product::getId))
                .get().getId()+1;
    }








    public Product getOne(int id) throws ResourceNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Not found"));
    }

    /*

    public Product save(ProductDto productDto) throws AttributeException {
        if(productRepository.existsByName(productDto.getName()))
            throw new AttributeException("Name already in use");
        int id=autoIncrement();
        Product product=new Product(id,productDto.getName(),productDto.getPrice());
        return productRepository.save(product);
    }*/

    public Product update(int id,ProductDto productDto) throws ResourceNotFoundException, AttributeException {
        Product product=productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Not found"));

        if(productRepository.existsByName(productDto.getName()) && productRepository.findByName(productDto.getName()).get().getId()!=id)
            throw new AttributeException("Name already in use");
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        return productRepository.save(product);
    }
    /*
    public Product delete(int id) throws ResourceNotFoundException {
        Product product=productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Not found"));;
        productRepository.delete(product);
        return product;
    }

    private int autoIncrement(){
        List<Product> products=productRepository.findAll();
        return products.isEmpty()?1:products
                .stream().max(
                        Comparator.comparing(Product::getId)
                ).get().getId()+1;
    }
    */
}
