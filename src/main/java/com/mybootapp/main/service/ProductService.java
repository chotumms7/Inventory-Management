package com.mybootapp.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybootapp.main.model.Product;
import com.mybootapp.main.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	public Product insert(Product product) {
		return productRepository.save(product);
		
		
	}

	public List<Product> getAll() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}
	public Product getproduct(int id) {
	Optional <Product> optional=productRepository.findById(id);
	if(!optional.isPresent()) {
		return null;
	}
	return optional.get();
	}
	public void deleteProduct(Product product) {
		productRepository.delete(product);
		
	}
	public Product getById(int productId) {
		Optional <Product> optional=productRepository.findById(productId);
		if(!optional.isPresent()) {
			return null;
	}
		return optional.get();
	
}
}
