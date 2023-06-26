package com.mybootapp.main.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mybootapp.main.exception.ResourceNotFoundException;
import com.mybootapp.main.model.ReturnRegister;
import com.mybootapp.main.repository.ReturnRegisterRepository;

@Service
public class ReturnRegisterService {

	@Autowired
	private ReturnRegisterRepository returnsRepository;
	
	public ReturnRegister insert(ReturnRegister returns) {
		return returnsRepository.save(returns);
	}
	
	public List<ReturnRegister> getAll() {
		return returnsRepository.findAll();
	}

	public ReturnRegister getById(int id) throws ResourceNotFoundException{
		Optional<ReturnRegister> returns = returnsRepository.findById(id);
		if(returns.isEmpty()) {
			throw new ResourceNotFoundException("invalid id given");
		}
		return returns.get();
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		returnsRepository.deleteById(id);
	}

}