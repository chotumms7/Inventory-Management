package com.mybootapp.main.controller;

import java.util.List;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mybootapp.main.model.Product;
import com.mybootapp.main.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	

	
    @PostMapping("/product/add")
	public ResponseEntity<?> postProduct(@RequestBody Product product) {
    	product=productService.insert(product);
    	return ResponseEntity.status(HttpStatus.OK)
    			.body(product);
    	
	}
    
    @GetMapping("/product/getAll")
    public List<Product> getAll(){
    	List<Product> list =productService.getAll();
    	return list;
    }
  
    @GetMapping("/product/one/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") int id){
    	Product product=productService.getproduct(id);
    	if(product==null) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body("Invalid ID given");
    	}
    	return ResponseEntity.status(HttpStatus.OK).body(product);
    }
   
    @PutMapping("/product/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int id,@RequestBody Product newProduct){
    	if(newProduct.getTitle()==null || !newProduct.getTitle().trim().matches("[a-zA-Z0-9- *]+"))
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body("title has to be in valid format");
    	if(newProduct.getDescription() ==null || newProduct.getDescription().equals(""))
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body("description can't be null");
    	if(newProduct.getPrice()==0)
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body("price shouldnot be 0");
    	
    	Product oldProduct=productService.getproduct(id);
    	if(oldProduct==null) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body("INVALID Id given");
    	}
    	
    	newProduct.setId(oldProduct.getId());
    	newProduct=productService.insert(newProduct);
    	return ResponseEntity.status(HttpStatus.OK).body(newProduct);	
    	
    }
    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int id){
    	Product product=productService.getproduct(id);
    	if(product==null) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body("invalid id given");
    	}
    	productService.deleteProduct(product);
    	return ResponseEntity.status(HttpStatus.OK)
    			.body("product deleted");
    }
}
