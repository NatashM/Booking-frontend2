package com.example.sso.service;

import com.example.sso.model.Member;
import com.example.sso.model.MembershipType;
import com.example.sso.model.Department;
import com.example.sso.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));
    }


    public Member createMember(Member member) {
        return memberRepository.save(member);
    }


    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }


    public Member updateMember(Long memberId, Member updatedMember) {
        return memberRepository.findById(memberId)
                .map(member -> {
                    member.setName(updatedMember.getName());
                    member.setLastName(updatedMember.getLastName());
                    member.setEmail(updatedMember.getEmail());
                    member.setPassword(updatedMember.getPassword());
                    member.setDepartment(updatedMember.getDepartment());
                    member.setMembershipType(updatedMember.getMembershipType());
                    return memberRepository.save(member);
                })
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));
    }


    public void deleteMember(Long memberId) {
        if (memberRepository.existsById(memberId)) {
            memberRepository.deleteById(memberId);
        } else {
            throw new IllegalArgumentException("Member not found with ID: " + memberId);
        }
    }
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }
    public Member register(String name, String lastName, String email, String password, MembershipType membershipType, Department department) {
        // Validér data
        if (name == null || lastName == null || email == null || password == null || membershipType == null || department == null) {
            throw new IllegalArgumentException("Alle felter skal udfyldes.");
        }


        Optional<Member> existingMember = memberRepository.findByEmail(email);
        if (existingMember.isPresent()) {
            throw new IllegalArgumentException("Emailen er allerede registreret.");
        }


        Member member = new Member();
        member.setName(name);
        member.setLastName(lastName);
        member.setEmail(email);
        member.setPassword(password);
        member.setMembershipType(membershipType.toString());
        member.setDepartment(department.toString());

        return memberRepository.save(member);
    }

    public Member registerMember(Member member) {
        // Encrypt password before saving
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        // Check if ROLE_USER exists in the database, else create it
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            roleRepository.save(userRole);
        }

        // Add the role to the member
        member.getRoles().add(userRole);

        // Save the member in the database
        return memberRepository.save(member);
    }
}

