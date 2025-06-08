package com.dpbo.api.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dpbo.api.model.Role;
import com.dpbo.api.model.User;
import com.dpbo.api.payload.SignUpDto;
import com.dpbo.api.repository.RoleRepository;
import com.dpbo.api.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    /* application/json
     * 
     * -----signUpDto------
     * fullName: String
     * phoneNumber: String
     * password: String
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        if(userRepo.existsByPhoneNumber(signUpDto.getPhoneNumber())){
        	// 400 BAD REQUEST
            return new ResponseEntity<>("Phone number is already taken!", HttpStatus.BAD_REQUEST);
        }
        
        // INIT USER MODEL
        User user = new User();
        String id = UUID.randomUUID().toString();
        user.setId(id);
        user.setFullName(signUpDto.getFullName());
        user.setPhoneNumber(signUpDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        //SET DEFAULT ROLES ('USER')
        HashSet<Role> roles = new HashSet<>();
        Role userRole = roleRepo.findByName("USER");
        if (userRole == null) {
        	// 400 BAD REQUEST
            return new ResponseEntity<>("ROLE NOT FOUND", HttpStatus.BAD_REQUEST);
        }
        roles.add(userRole);
        user.setRoles(roles);
        
        // SAVE DATA TO DATABSE
        userRepo.save(user);
        
        // RETURN RESPONSE
        Map<String, Object> responseBody = new HashMap<>();
        
        responseBody.put("message", "Registration Succesfull");
        responseBody.put("user", user);
        
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
	
	@GetMapping("/session-expired")
	public ResponseEntity<?> sessionExpired() {
		// 401 UNAUTHORIZED
		return new ResponseEntity<>("Session has expired or is invalid. Please log in again.", HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/check-session")
	/*
	 * header
	 * Set-Cookie: JSESSIONID
	 */
	public ResponseEntity<?> checkSession(){
		// CHECK FOR EXISTING TOKEN
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		// INIT RESPONSE
		Map<String, Object> responseBody = new HashMap<>();
		
		// NO TOKEN FOUND HANDLER
		if(authentication == null || !authentication.isAuthenticated()) {
            responseBody.put("message", "No active authenticated session found.");
            responseBody.put("authenticated", false);
            // 401 UNAUTHORIZED
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);

		}
		
		// TOKEN FOUND HANDLER
		responseBody.put("message", "Active authentication found.");
        responseBody.put("authenticated", true);
        
        // GET CURRENT USER THROUGH SECURITY CONTEXT
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        User user = userRepo.findByPhoneNumber(userDetails.getUsername());
        responseBody.put("user", user);
        responseBody.put("authorities", userDetails.getAuthorities().stream()
                                           .map(grantedAuthority -> grantedAuthority.getAuthority())
                                           .collect(Collectors.toList()));
        
        return new ResponseEntity<>(responseBody, HttpStatus.OK);

	}
	
	@GetMapping("/test")
	public ResponseEntity<?> test(){
		return ResponseEntity.ok("Hello");
	}
	
}
