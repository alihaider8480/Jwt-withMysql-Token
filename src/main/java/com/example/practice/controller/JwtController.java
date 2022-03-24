package com.example.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.practice.pojo.JwtRequest;
import com.example.practice.pojo.JwtResponse;
import com.example.practice.service.CustomUserDetailsService;
import com.example.practice.utils.JwtTokenUtil;

@RestController
public class JwtController {
	
	    // ya aik builtin class hai iska andar aik authenticate ka method ha jab ma isko call karonga to 
		// ya khud configuration class ma la jae ga.
	 	@Autowired
	    AuthenticationManager authenticationManager;

	 	// is class ma humna methods banae hai jis ma
	    @Autowired
	    JwtTokenUtil jwtUtil;

	    @Autowired
	    CustomUserDetailsService customUserDetailsService;

	    // har new tokin ka lia ma is url pa click karonga to har bar new token generate hoga
	    @GetMapping("/get-token")                // iska andar email or password ha class ma simple
	    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
	        System.out.println(jwtRequest.toString());
	        try {											// ya aik new username or password ka object ma isko dala ga or iska principle credential 
	            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(),jwtRequest.getPassword()));
	        }catch (Exception e){
	            e.printStackTrace();
	            throw new Exception("Bad Credentials.");
	        }
	        									// ya db sa id password match kara ga agar match howa to token genearte karaga warna
	        									// exception throw kardagasss
	        String token= jwtUtil.generateToken(customUserDetailsService.loadUserByUsername(jwtRequest.getEmail()));
	        return ResponseEntity.ok(new JwtResponse(token));
	    }
}
