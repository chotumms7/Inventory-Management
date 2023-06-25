package com.mybootapp.main.controller;

import java.util.List;

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

import com.mybootapp.main.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/add")
	public Customer postCustomer(@RequestBody Customer customer) {
		return customerService.insert(customer);
		
	}
	
	
	 @GetMapping("/getAll")
	    public List<Customer> getAll(){
	    	List<Customer> list =customerService.getAll();
	    	return list;
	    }
	 
	 @GetMapping("/one/{id}")
	    public ResponseEntity<?> getCustomer(@PathVariable("id") int id){
		 Customer customer=customerService.getcustomer(id);
	    	if(customer==null) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body("Invalid ID given");
	    	}
	    	return ResponseEntity.status(HttpStatus.OK).body(customer);
	    }
	 
	 @PutMapping("/update/{id}")
	    public ResponseEntity<?> updateCustomer(@PathVariable("id") int id,@RequestBody Customer newCustomer){
	    	
	    	
		 Customer oldCustomer=customerService.getcustomer(id);
	    	if(oldCustomer==null) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body("INVALID Id given");
	    	}
	    	
	    	newCustomer.setId(oldCustomer.getId());
	    	newCustomer=customerService.insert(newCustomer);
	    	return ResponseEntity.status(HttpStatus.OK).body(newCustomer);	
	    	
	    }
	 
	 @DeleteMapping("/delete/{id}")
		public ResponseEntity<?> deleteCustomer(@PathVariable("id") int id) {
		 Customer customer =customerService.getcustomer(id);
			if (customer == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid id given");
			}
			customerService.deleteCustomer(customer);
			return ResponseEntity.status(HttpStatus.OK).body("customer deleted");
		}
	 
	 

}
