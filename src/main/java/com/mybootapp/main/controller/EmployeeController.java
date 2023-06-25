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

import com.mybootapp.main.model.Employee;
import com.mybootapp.main.model.Manager;
import com.mybootapp.main.model.User;
import com.mybootapp.main.service.EmployeeService;
import com.mybootapp.main.service.ManagerService;
import com.mybootapp.main.service.MyUserService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private PasswordEncoder encoder; 
	
	@Autowired
	private MyUserService userService; 
	
	@Autowired
	private EmployeeService employeeService; 
	
	@Autowired
	private ManagerService managerService; 
	
	@PostMapping("/add/{managerId}")
	public ResponseEntity<?> addEmployee(@PathVariable("managerId") int managerId, 
			@RequestBody Employee employee) {
		/* validate managerId and fetch manager obj from DB */
		Manager manager  = managerService.getById(managerId);
		if(manager == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Manager ID invalid");
		
		/* attach manager to employee */
		employee.setManager(manager);
		
		/* Fetch the user from employee */
		User user = employee.getUser();
		
		/* Encode the password given as Plain text from UI */
		user.setPassword(encoder.encode(user.getPassword()));
		
		/* Set the role: EMPLOYEE */
		user.setRole("EMPLOYEE");
		
		/* Save the user in DB */
		user  = userService.insert(user);
		
		/* Attach user to employee and save employee */
		employee.setUser(user);
		employee =  employeeService.insert(employee);
		return ResponseEntity.status(HttpStatus.OK)
				.body(employee);
	}
	
	 @GetMapping("/getAll")
	    public List<Employee> getAll(){
	    	List<Employee> list =employeeService.getAll();
	    	return list;
	    }
	 
	 @GetMapping("/one/{id}")
	    public ResponseEntity<?> getEmployee(@PathVariable("id") int id){
		 Employee employee=employeeService.getemployee(id);
	    	if(employee==null) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body("Invalid ID given");
	    	}
	    	return ResponseEntity.status(HttpStatus.OK).body(employee);
	    }
	 
	 @PutMapping("/update/{id}")
	    public ResponseEntity<?> updateEmployee(@PathVariable("id") int id,@RequestBody Employee newEmployee){
	    	
	    	
		 Employee oldEmployee=employeeService.getemployee(id);
	    	if(oldEmployee==null) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body("INVALID Id given");
	    	}
	    	
	    	newEmployee.setId(oldEmployee.getId());
	    	newEmployee=employeeService.insert(newEmployee);
	    	return ResponseEntity.status(HttpStatus.OK).body(newEmployee);	
	    	
	    }
	 
	 @DeleteMapping("/delete/{id}")
		public ResponseEntity<?> deleteEmployee(@PathVariable("id") int id) {
		 Employee employee = employeeService.getemployee(id);
			if (employee == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid id given");
			}
			employeeService.deleteEmployee(employee);
			return ResponseEntity.status(HttpStatus.OK).body("employee deleted");
		}

}
