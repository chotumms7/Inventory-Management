package com.mybootapp.main.controller;

import java.util.ArrayList;
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

import com.mybootapp.main.dto.GodownDto;
import com.mybootapp.main.model.Godown;
import com.mybootapp.main.model.Manager;
import com.mybootapp.main.service.GodownService;
import com.mybootapp.main.service.ManagerService;

@RestController
@RequestMapping("/godown")
public class GodownController {

	@Autowired
	private GodownService godownService;
	
	@Autowired
	private ManagerService managerService;
	
	
	@PostMapping("/add/{managerID}")
	public ResponseEntity<?> insertGodown(@PathVariable("managerID") int managerID,
			@RequestBody Godown godown) {
		
		Manager manager = managerService.getById(managerID);
		if(manager==null) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("invalid managerID");
			
		godown.setManager(manager);
		
		godown= godownService.insert(godown);
		return ResponseEntity.status(HttpStatus.OK)
				.body(godown);
	}
	
	
	
	 @GetMapping("/getAll")
	    public List<Godown> getAll(){
	    	List<Godown> list =godownService.getAll();
	    	return list;
	    }
	 
	 @GetMapping("/one/{id}")
	    public ResponseEntity<?> getGodown(@PathVariable("id") int id){
		 Godown godown=godownService.getgodown(id);
	    	if(godown==null) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body("Invalid ID given");
	    	}
	    	return ResponseEntity.status(HttpStatus.OK).body(godown);
	    }
	 
	 @PutMapping("/update/{managerId}/{id}")
	   public ResponseEntity<?> updategodown(@PathVariable("id") int id,
	            @PathVariable("managerId") int managerId,@RequestBody Godown newgodown)
	    {
	        Manager manager=managerService.getById(managerId);
	        if(manager==null)
	        {
	           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid id");
	        }
	      Godown oldgodown=godownService.getgodown(id);
	      if(oldgodown==null)
	      {
	          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid");
	      }

	      newgodown.setManager(manager);

	      newgodown.setId(oldgodown.getId());

	      newgodown=godownService.insert(newgodown);

	      return ResponseEntity.status(HttpStatus.OK).body(newgodown);
	    }
	 
	 @DeleteMapping("/delete/{id}")
	    public ResponseEntity<?> deleteGodown(@PathVariable("id") int id){
		 Godown godown=godownService.getgodown(id);
	    	if(godown==null) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body("invalid id given");
	    	}
	    	godownService.deleteGodown(godown);
	    	return ResponseEntity.status(HttpStatus.OK)
	    			.body("godown deleted");
	    }
	 
	 //report api4
	 @GetMapping("/report")
		public List<GodownDto> godownReport() {
			/* Go to db and fetch all Inward entries.. */
			List<Godown> list = godownService.getAll();
			List<GodownDto> listDto = new ArrayList<>();
			/* convert the response into UI format */
			 list.stream().forEach(entry->{
				 GodownDto dto = new GodownDto(); //100X 200X
				dto.setGodownId(entry.getId());
				dto.setGodownCapacity(entry.getCapacityInQunitals());
				dto.setGodownLocation(entry.getLocation());
				dto.setGodownManager(entry.getManager().getName());
				 listDto.add(dto); //100X 200X
			 });
			 
			return listDto; 
	 }
}
