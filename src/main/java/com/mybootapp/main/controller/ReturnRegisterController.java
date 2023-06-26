package com.mybootapp.main.controller;

import java.time.LocalDate;
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

import com.mybootapp.main.exception.ResourceNotFoundException;
import com.mybootapp.main.model.OutwardRegister;
import com.mybootapp.main.model.ReturnRegister;
import com.mybootapp.main.service.OutwardRegisterService;
import com.mybootapp.main.service.ReturnRegisterService;

@RestController
@RequestMapping("/returns")
public class ReturnRegisterController {
	
	@Autowired
	private ReturnRegisterService returnsService;
	
	@Autowired
	private OutwardRegisterService outwardRegisterService;
	
	@GetMapping("getall")
	public List<ReturnRegister> getAll(){
		List<ReturnRegister> list = returnsService.getAll();
		return list;
	}
	
	@GetMapping("one/{id}")
	public ResponseEntity<?> getById(@PathVariable int id) {
		ReturnRegister returns;
		try {
			returns = returnsService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Id given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(returns);
	}
	
	@PostMapping("/add/{outwardRegisterId}")
	public ResponseEntity<?> addReturns(@PathVariable("outwardRegisterId") int outwardRegisterId,@RequestBody ReturnRegister returns) throws ResourceNotFoundException{
		OutwardRegister outwardRegister = outwardRegisterService.getOutwardRegister(outwardRegisterId);
		returns.setOutwardRegister(outwardRegister);
		returns.setDateOfReturn(LocalDate.now());
		returns = returnsService.insert(returns);
		return ResponseEntity.status(HttpStatus.OK).body(returns);
	}
	
	@PutMapping("/update/{returnsId}/{outwardRegisterId}")
	public ResponseEntity<?> update(@PathVariable int returnId,@PathVariable int outwardRegisterId, @RequestBody ReturnRegister returns) throws ResourceNotFoundException{
		try {
			returnsService.getById(returnId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		returns.setId(returnId);
		OutwardRegister outwardRegister = outwardRegisterService.getById(outwardRegisterId);
		returns.setOutwardRegister(outwardRegister);	
		returns.setDateOfReturn(LocalDate.now());
		returnsService.insert(returns);
		return ResponseEntity.status(HttpStatus.OK).body(returns);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable int id){
		try {
			returnsService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		returnsService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}