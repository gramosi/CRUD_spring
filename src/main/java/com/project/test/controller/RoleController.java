package com.project.test.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.test.exception.RoleNotFoundException;
import com.project.test.exception.UserNotFoundException;
import com.project.test.exception.response.UserErrorResponse;
import com.project.test.model.Role;
import com.project.test.repository.RoleRepo;

@RestController
public class RoleController {

	@Autowired
	RoleRepo roleRepo;
	
	@GetMapping("/roles")
	public List<Role> getAllRoles() {
		
		return roleRepo.findAll();
		
	}
	
	@GetMapping("/roles/{id}")
	public Role getRole(@PathVariable("id") Long id) {

		return roleRepo.findById(id).orElseThrow(()-> new UserNotFoundException("Roli me id: " + id + ", nuk egziston"));
		
	}
	
	@PostMapping("/roles")
	public Role addRole(@RequestBody Role role) {
		
		return roleRepo.save(role);
		
	}
	
	//ADD success message
	@DeleteMapping("roles/{id}")
	public void deleteRole(@PathVariable("id") Long id) {
		
		Optional<Role> role = roleRepo.findById(id);
		if(role.isPresent()) {
			roleRepo.deleteById(id);
		}
		else {
			throw new UserNotFoundException("Roli me id:" + id + ", nuk egziston");
		}
	}
	
	@ExceptionHandler
	private ResponseEntity<UserErrorResponse> handleError(RoleNotFoundException ex){
		
		UserErrorResponse userError = new UserErrorResponse();
		userError.setStatus(HttpStatus.NOT_FOUND.value());
		userError.setMessage(ex.getMessage());
		
		return new ResponseEntity<UserErrorResponse>(userError, HttpStatus.NOT_FOUND);
		
	}

	@ExceptionHandler
	private ResponseEntity<UserErrorResponse> handleError(Exception ex){
		
		UserErrorResponse userError = new UserErrorResponse();
		userError.setStatus(HttpStatus.BAD_REQUEST.value());
		userError.setMessage(ex.getMessage());
		
		return new ResponseEntity<UserErrorResponse>(userError, HttpStatus.BAD_REQUEST);
		
	}
	
}
