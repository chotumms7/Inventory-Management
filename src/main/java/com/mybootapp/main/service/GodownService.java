package com.mybootapp.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybootapp.main.model.Godown;

import com.mybootapp.main.repository.GoDownRepository;
@Service
public class GodownService {

	@Autowired
	private GoDownRepository goDownRepository;
	public Godown insert(Godown godown) {
		
		return goDownRepository.save(godown);
	}
	public Godown getById(int godownId) {
		Optional<Godown> optional=goDownRepository.findById(godownId);
		if(!optional.isPresent()) {
		return null;
		}
		return optional.get();
		
		
		
	}
	public List<Godown> getAll() {
		
		return goDownRepository.findAll();
	}
	public Godown getgodown(int id) {
		Optional <Godown> optional=goDownRepository.findById(id);
		if(!optional.isPresent()) {
			return null;
		}
		return optional.get();
	}
	public void deleteGodown(Godown godown) {
		goDownRepository.delete(godown);
	}

}
