package com.example.sso.config;

import com.example.sso.model.Admin;
import com.example.sso.model.Member;
import com.example.sso.repository.AdminRepository;
import com.example.sso.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitData {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initializeData() {
        setupAdmins();
        setupMembers();
    }

    private void setupAdmins() {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));

            adminRepository.save(admin);
            System.out.println("Admin initialized.");
        } else {
            System.out.println("Admin already exists.");
        }
    }



    private void setupMembers() {
        if (memberRepository.count() == 0) {
            Member member1 = new Member();
            member1.setName("member1");
            member1.setPassword(passwordEncoder.encode("member123"));
            member1.setEmail("member1@example.com");

            Member member2 = new Member();
            member2.setName("member2");
            member2.setPassword(passwordEncoder.encode("member123"));
            member2.setEmail("member2@example.com");

            memberRepository.save(member1);
            memberRepository.save(member2);
            System.out.println("Members initialized.");
        } else {
            System.out.println("Members already exist.");
        }
    }
}
