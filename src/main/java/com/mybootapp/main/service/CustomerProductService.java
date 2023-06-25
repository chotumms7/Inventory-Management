package com.mybootapp.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybootapp.main.model.CustomerProduct;

import com.mybootapp.main.repository.CustomerProductRepository;

@Service
public class CustomerProductService {
	
	@Autowired
	private CustomerProductRepository customerProductRepository;
	
	public CustomerProduct insert(CustomerProduct customerProduct) {
		 
		return customerProductRepository.save(customerProduct);
	}

	public List<CustomerProduct> getAll() {
		
		return customerProductRepository.findAll();
	}

	public CustomerProduct getcustomerProduct(int id) {
		
		Optional <CustomerProduct> optional=customerProductRepository.findById(id);
		if(!optional.isPresent()) {
			return null;
		}
		return optional.get();
	}
	
public CustomerProduct getCustomerProduct(int customerId) {
		
		Optional <CustomerProduct> optional=customerProductRepository.findById(customerId);
		if(!optional.isPresent()) {
			return null;
		}
		return optional.get();
	}

	public void deleteCustomerProduct(CustomerProduct customerProduct) {
		
		customerProductRepository.delete(customerProduct);
	}
	
	
}
