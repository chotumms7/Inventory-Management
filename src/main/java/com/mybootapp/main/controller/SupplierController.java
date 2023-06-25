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


import com.mybootapp.main.model.Supplier;
import com.mybootapp.main.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
	
	@Autowired
	private SupplierService supplierService;
	
	@PostMapping("/add")
	public Supplier postSupplier(@RequestBody Supplier supplier) {
		return supplierService.insert(supplier);
	}
	@GetMapping("/getAll")
    public List<Supplier> getAll(){
    	List<Supplier> list =supplierService.getAll();
    	return list;
    }
	 @GetMapping("/one/{id}")
	    public ResponseEntity<?> getSupplier(@PathVariable("id") int id){
		 Supplier supplier=supplierService.getsupplier(id);
	    	if(supplier==null) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body("Invalid ID given");
	    	}
	    	return ResponseEntity.status(HttpStatus.OK).body(supplier);
	    }
	
	 @PutMapping("/update/{id}")
	    public ResponseEntity<?> updateSupplier(@PathVariable("id") int id,@RequestBody Supplier newSupplier){
	    	
	    	
		 Supplier oldSupplier=supplierService.getsupplier(id);
	    	if(oldSupplier==null) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body("INVALID Id given");
	    	}
	    	
	    	newSupplier.setId(oldSupplier.getId());
	    	newSupplier=supplierService.insert(newSupplier);
	    	return ResponseEntity.status(HttpStatus.OK).body(newSupplier);	
	    	
	    }
	 @DeleteMapping("/delete/{id}")
	    public ResponseEntity<?> deleteSupplier(@PathVariable("id") int id){
		 Supplier supplier=supplierService.getsupplier(id);
	    	if(supplier==null) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body("invalid id given");
	    	}
	    	supplierService.deleteSupplier(supplier);
	    	return ResponseEntity.status(HttpStatus.OK)
	    			.body("supplier deleted");
	    }

}
