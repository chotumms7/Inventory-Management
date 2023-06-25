package com.mybootapp.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybootapp.main.model.Manager;
import com.mybootapp.main.model.User;
import com.mybootapp.main.service.ManagerService;
import com.mybootapp.main.service.MyUserService;

@RestController
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	private ManagerService managerService; 
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MyUserService userService; 
	@PostMapping("/add")
	public Manager postManager(@RequestBody Manager manager) {
		/*Read user info given as input and attach it to user object.  */
		User user = manager.getUser();
		user.setRole("MANAGER");
		
		/* Encode the password before saving in DB */
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		/* Save user in DB and fetch saved object */
		user = userService.insert(user);
		
		/* attach user to manager */
		manager.setUser(user);
		
		/* Save manager in DB */
		return managerService.insert(manager);
	}
	
	
	 @GetMapping("/getAll")
	    public List<Manager> getAll(){
	    	List<Manager> list =managerService.getAll();
	    	return list;
	    }
	 
	 @GetMapping("/one/{id}")
	    public ResponseEntity<?> getManager(@PathVariable("id") int id){
	    	Manager manager=managerService.getmanager(id);
	    	if(manager==null) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body("Invalid ID given");
	    	}
	    	return ResponseEntity.status(HttpStatus.OK).body(manager);
	    }
	 
	 @PutMapping("/update/{id}")
	    public ResponseEntity<?> updateManager(@PathVariable("id") int id,@RequestBody Manager newManager){
	    	
	    	
	    	Manager oldManager=managerService.getmanager(id);
	    	if(oldManager==null) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body("INVALID Id given");
	    	}
	    	
	    	newManager.setId(oldManager.getId());
	    	newManager=managerService.insert(newManager);
	    	return ResponseEntity.status(HttpStatus.OK).body(newManager);	
	    	
	    }
	 
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteManager(@PathVariable("id") int id) {
		Manager manager = managerService.getmanager(id);
		if (manager == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid id given");
		}
		managerService.deleteManager(manager);
		return ResponseEntity.status(HttpStatus.OK).body("manager deleted");
	}

}
