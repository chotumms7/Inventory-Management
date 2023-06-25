package com.mybootapp.main.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybootapp.main.model.Customer;
import com.mybootapp.main.model.CustomerProduct;
import com.mybootapp.main.model.Product;
import com.mybootapp.main.service.CustomerProductService;
import com.mybootapp.main.service.CustomerService;
import com.mybootapp.main.service.InwardRegisterService;
import com.mybootapp.main.service.ProductService;

@RestController
@RequestMapping("/customerproduct")
public class CustomerProductController {
	
	@Autowired
	private CustomerService customerService; 
	@Autowired
	private ProductService productService; 
	@Autowired
	private CustomerProductService customerProductService;
	@Autowired
	private InwardRegisterService inwardRegisterService;
	
	@PostMapping("/purchase/{customerId}/{productId}")
	public ResponseEntity<?> purchaseApi(@RequestBody CustomerProduct customerProduct,
							@PathVariable("customerId") int customerId, 
							@PathVariable("productId") int productId) {
		
		/* Step 1: validate Ids and fetch objects  */
		Customer customer  = customerService.getById(customerId);
		if(customer == null )
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid customer ID given");
		
		Product product = productService.getById(productId);
		if(product == null )
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid product ID given");
		
		/* Step 2: set customer and product to customerProduct*/
		customerProduct.setCustomer(customer);
		customerProduct.setProduct(product);
		customerProduct.setDateOfPurchase(LocalDate.now());
		
		/* Step 3: check if that product is available in proper quantity in InwardRegister*/
		boolean status = inwardRegisterService.checkQuantity(productId,customerProduct.getQuantityPuchased());
		if(status == false)
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body("Product out of stock");
		
		/* Step 4: Save customerProduct object in DB */
		customerProduct = customerProductService.insert(customerProduct);
		return ResponseEntity.status(HttpStatus.OK)
				.body(customerProduct);
	}
	
	@PostMapping("/add")
	public CustomerProduct postCustomerProduct(@RequestBody CustomerProduct customerProduct) {
		
	return customerProductService.insert(customerProduct);
	}
	
	
	
	 @GetMapping("/one/{id}")
	    public ResponseEntity<?> getCustomerProduct(@PathVariable("id") int id){
		 CustomerProduct customerProduct=customerProductService.getcustomerProduct(id);
	    	if(customerProduct==null) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body("Invalid ID given");
	    	}
	    	return ResponseEntity.status(HttpStatus.OK).body(customerProduct);
	    }
	 
	 @PutMapping("/update/{id}/{customerId}/{productId}")
	    public ResponseEntity<?> updateCustomerProduct(@PathVariable("id") int id,
	    		@PathVariable("customerId") int customerId,
	    		@PathVariable("productId") int productId,
	    		@RequestBody CustomerProduct newCustomerProduct){
		 
		 Customer customer  = customerService.getById(customerId);
			if(customer == null )
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Invalid customer ID given");
			
			Product product = productService.getById(productId);
			if(product == null )
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Invalid product ID given");
			
			 CustomerProduct oldCustomerProduct=customerProductService.getcustomerProduct(id);
		    	if(oldCustomerProduct==null) {
		    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		    				.body("INVALID Id given");
		    	}
		 
			newCustomerProduct.setCustomer(customer);
			newCustomerProduct.setProduct(product);
	    	newCustomerProduct.setId(oldCustomerProduct.getId());
	    	newCustomerProduct=customerProductService.insert(newCustomerProduct);
	    	return ResponseEntity.status(HttpStatus.OK).body(newCustomerProduct);	
	    	
	    }
	 
	 @DeleteMapping("/delete/{id}")
	    public ResponseEntity<?> deleteCustomerProduct(@PathVariable("id") int id){
		 CustomerProduct customerProduct=customerProductService.getcustomerProduct(id);
	    	if(customerProduct==null) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body("invalid id given");
	    	}
	    	customerProductService.deleteCustomerProduct(customerProduct);
	    	return ResponseEntity.status(HttpStatus.OK)
	    			.body("customerProduct deleted");
	    }

}
