package com.project.test.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.PostRemove;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.test.exception.UserNotFoundException;
import com.project.test.exception.response.UserErrorResponse;
import com.project.test.model.Role;
import com.project.test.model.User;
import com.project.test.repository.RoleRepo;
import com.project.test.repository.UserRepo;

@RestController
public class UserController{

	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		
		
		log.info("U listuan te gjithe userat");
		return userRepo.findAll();
		
	}
	
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable("id") Long id) {
		
		Optional<User> user = userRepo.findById(id);
		if(user.isPresent()) {
			return user.get();
		}else {
			log.error("Useri me id: " + id + ", nuk eshte gjetur");
			throw new UserNotFoundException("Useri me id: " + id + ", nuk eshte gjetur");
			
		}
		
	}
	
	@PostMapping("/users")
	public User addUser(@RequestBody User user) {
		
		log.info("Useri " + user.toString() + " eshte krijuar me sukses");
		
		User u = new User();
		
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		u.setEmail(user.getEmail());
		u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		u.setRoles(Arrays.asList(roleRepo.getById(2L)));
		return userRepo.save(u);
	
	}

	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		
		Optional<User> u = userRepo.findById(id);
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		if(u.isPresent()) {
			
			User u2 = u.get();

			if(user.getFirstName() != null)
			u2.setFirstName(user.getFirstName());
			
			if(user.getLastName() != null)
			u2.setLastName(user.getLastName());
			
			if(user.getEmail() != null)
			u2.setEmail(user.getEmail());

			if(user.getPassword() != null)
			u2.setPassword(bcrypt.encode(user.getPassword()));
			
			if(user.getRoles() != null)
				//System.out.println("Test");
				u2.setRoles(user.getRoles());
			
			
			return userRepo.save(u2);
			
			
		}else {
			log.error("Useri me id: " + id + ", nuk eshte gjetur");
			throw new UserNotFoundException("User with Id: "+id +", was not found");
		}
		
	}

	@DeleteMapping("users/{id}")
	public ResponseEntity<List<String>> deleteUser(@PathVariable("id") Long id) {
	
		
		List<String> ret = new ArrayList<String>();
		ret.add(HttpStatus.OK.value()+ "");
		ret.add("Useri me id: " + id + " u fshi me sukses!");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Optional<User> user = userRepo.findById(id);
		if(user.isPresent()) {
			log.info("U be fshirja e userit me id: " + id+". Nga " + authentication.getName());
			userRepo.deleteById(id);
			return new ResponseEntity<List<String>>(ret,HttpStatus.OK);
		}else {
			log.error("Useri me id: " + id + ", nuk eshte gjetur");
			throw new UserNotFoundException("Not found");
		}
		
	}
	
	@ExceptionHandler
	public ResponseEntity<UserErrorResponse> handleException(UserNotFoundException ex){
		
		UserErrorResponse userError = new UserErrorResponse();
		userError.setStatus(HttpStatus.NOT_FOUND.value());
		userError.setMessage(ex.getMessage());
	
		return new ResponseEntity<UserErrorResponse>(userError,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<UserErrorResponse> handleException(Exception ex){
		
		UserErrorResponse userError = new UserErrorResponse();
		userError.setStatus(HttpStatus.BAD_REQUEST.value());
		userError.setMessage(ex.getMessage());

	
		return new ResponseEntity<UserErrorResponse>(userError,HttpStatus.BAD_REQUEST);
		
	}
}
