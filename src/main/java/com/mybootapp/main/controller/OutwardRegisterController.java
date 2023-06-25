package com.mybootapp.main.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.mybootapp.main.dto.OutwardRegisterDto;
import com.mybootapp.main.exception.ResourceNotFoundException;
import com.mybootapp.main.model.Customer;
import com.mybootapp.main.model.CustomerProduct;
import com.mybootapp.main.model.Godown;
import com.mybootapp.main.model.OutwardRegister;
import com.mybootapp.main.model.Product;
import com.mybootapp.main.service.CustomerProductService;
import com.mybootapp.main.service.CustomerService;
import com.mybootapp.main.service.GodownService;
import com.mybootapp.main.service.OutwardRegisterService;
import com.mybootapp.main.service.ProductService;

@RestController
@RequestMapping("/outwardregister")
public class OutwardRegisterController {
	
	@Autowired
	private ProductService productService; 
	
	@Autowired
	private GodownService godownService;
	
	@Autowired
	private CustomerService customerService; 
	
	@Autowired
	private OutwardRegisterService outwardRegisterService;
	
	@Autowired
	private CustomerProductService customerProductService;
	
	@PostMapping("/add/{productId}/{godownId}/{customerId}")
	public ResponseEntity<?> postOutwardRegister(@RequestBody OutwardRegister outwardRegister,
								   @PathVariable("productId") int productId,
								   @PathVariable("godownId") int godownId,
								   @PathVariable("customerId") int customerId) {
		
		/* Validate Ids and fetch Objects */
		
		Product product = productService.getById(productId);
		if(product == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid product ID given");
		
		Godown godown = godownService.getById(godownId);
		if(godown == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid godown ID given");
		
		Customer customer  = customerService.getById(customerId);
		if(customer == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid supplier ID given");
		
		/* Attach all objects to outwardRegister */
		outwardRegister.setProduct(product);
		outwardRegister.setGodown(godown);
		outwardRegister.setCustomer(customer);
		
		outwardRegister.setDateOfDelivery(LocalDate.now());
		
		/* save outwardRegister object  */
		outwardRegister = outwardRegisterService.insert(outwardRegister);
		return ResponseEntity.status(HttpStatus.OK)
				.body(outwardRegister);
	}
	
	@GetMapping("/getAll")
    public List<OutwardRegister> getAll(){
    	List<OutwardRegister> list =outwardRegisterService.getAll();
    	return list;
    }
	
	@GetMapping("/one/{id}")
    public ResponseEntity<?> getOutwardRegister(@PathVariable("id") int id){
		OutwardRegister outwardRegister=outwardRegisterService.getOutwardRegister(id);
    	if( outwardRegister==null) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body("Invalid ID given");
    	}
    	return ResponseEntity.status(HttpStatus.OK).body( outwardRegister);
    }
	
	

	@PutMapping("/update/{productId}/{godownId}/{supplierId}/{id}")
	    public ResponseEntity<?> updateOutwardRegister(@PathVariable("id") int id,
	            @PathVariable("productId") int productId,
	            @PathVariable("godownId") int godownId,
	            @PathVariable("customerId") int customerId,
	            @RequestBody OutwardRegister newOutwardRegister)
	    {
	         Product product=productService.getById(productId);
	         if(product==null)
	         {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid id");
	         }
	         Godown godown=godownService.getById(godownId);
	         if(godown==null)
	         {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid id");
	         }
	         Customer customer=customerService.getById(customerId);
	         if(customer==null)
	         {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid id");
	         }
	      OutwardRegister oldOutwardRegister= outwardRegisterService.getOutwardRegister(id);
	      if(oldOutwardRegister==null)
	      {
	          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid");
	      }
	      newOutwardRegister.setGodown(godown);
	      newOutwardRegister.setProduct(product);
	      newOutwardRegister.setCustomer(customer);
	      newOutwardRegister.setId(oldOutwardRegister.getId());


	      newOutwardRegister=outwardRegisterService.insert(newOutwardRegister);

	      return ResponseEntity.status(HttpStatus.OK).body(newOutwardRegister);

	     }
     
	 @DeleteMapping("/delete/{id}")
	    public ResponseEntity<?> deleteOutwardRegister(@PathVariable("id") int id){
		 OutwardRegister outwardRegister=outwardRegisterService.getOutwardRegister(id);
	    	if(outwardRegister==null) {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body("invalid id given");
	    	}
	    	outwardRegisterService.deleteOutwardRegister(outwardRegister);
	    	return ResponseEntity.status(HttpStatus.OK)
	    			.body("outwardRegister deleted");
	    }

	//report api3
	 @GetMapping("/report")
		public List<OutwardRegisterDto> outwardReport() {
			/* Go to db and fetch all outward entries.. */
			List<OutwardRegister> list = outwardRegisterService.getAll();
			List<OutwardRegisterDto> listDto = new ArrayList<>();
			/* convert the response into UI format */
			 list.stream().forEach(entry->{
				 OutwardRegisterDto dto = new OutwardRegisterDto(); //100X 200X
				 dto.setProductTitle(entry.getProduct().getTitle());
				 dto.setProductQuantity(entry.getQuantity());
				 dto.setGodownLocation(entry.getGodown().getLocation());
				 dto.setGodownManager(entry.getGodown().getManager().getName());
				 dto.setReceiptno(entry.getReceiptNo());
				 listDto.add(dto); //100X 200X
			 });
			 
			return listDto; 
		}
	 
	 //report api2
	 @GetMapping("/report/customer/{customerId}")
	    public ResponseEntity<?> outwardReportByCustomer(@PathVariable int customerId) throws ResourceNotFoundException {
	        List<CustomerProduct> list;
	        list = (List<CustomerProduct>) customerProductService.getCustomerProduct(customerId);
	        HashMap<String, Integer> map = new HashMap<>();
	        list.stream().forEach(entry -> {
	            if (map.containsKey(entry.getProduct().getTitle())) {
	                map.put(entry.getProduct().getTitle(), map.get(entry.getProduct().getTitle()) + entry.getQuantityPuchased());
	            }
	            else {
	                map.put(entry.getProduct().getTitle(), entry.getQuantityPuchased());                
	            }
	        });

	        return ResponseEntity.status(HttpStatus.OK).body(map);
	    }
}
