package com.example.sso.controller;

import com.example.sso.model.SSOinfo;
import com.example.sso.service.SSOinfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/info")
public class SSOrestcontroller {

    private final SSOinfoService ssoinfoService;

    public SSOrestcontroller(SSOinfoService ssoinfoService) {
        this.ssoinfoService = ssoinfoService;
    }

    @GetMapping
    public SSOinfo getSSOinfo() {
        return ssoinfoService.getSSOinfo();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public SSOinfo updateSSOinfo(@RequestBody String newContent) {
        return ssoinfoService.updateSSOinfo(newContent);
    }

    @PostMapping
    public SSOinfo createSSOinfo(@RequestBody String content) {
        return ssoinfoService.createSSOinfo(content);
    }
}
