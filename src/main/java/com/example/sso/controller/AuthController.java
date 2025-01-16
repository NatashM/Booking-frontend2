package com.example.sso.controller;

import com.example.sso.dto.LoginRequest;
import com.example.sso.dto.SignupRequest;  // Importer SignupRequest DTO
import com.example.sso.model.Member;  // Bruger Member model
import com.example.sso.repository.MemberRepository;  // Bruger MemberRepository
import com.example.sso.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private MemberRepository memberRepository;  // Bruger MemberRepository

    // Login funktionalitet
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        // Autentificer brugeren
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Bekræft at brugeren er autentificeret
        return "Login successful!";
    }

    // Tilmeldingsfunktionalitet for Member
    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest signupRequest) {
        // Opret en ny Member
        Member newMember = new Member();
        newMember.setEmail(signupRequest.getEmail());
        newMember.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));  // Krypter password
        newMember.setName(signupRequest.getName());
        newMember.setLastName(signupRequest.getLastName());
        newMember.setDepartment(signupRequest.getDepartment());
        newMember.setMembershipType("MEMBER");  // Angiv en standard medlemskaberrolle

        memberRepository.save(newMember);  // Gem den nye Member i databasen
        return "Signup successful!";
    }
}
