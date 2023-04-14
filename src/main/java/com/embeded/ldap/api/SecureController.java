package com.embeded.ldap.api;

import com.embeded.ldap.entry.UserEntry;
import com.embeded.ldap.entry.UserEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/secure")
@RequiredArgsConstructor
public class SecureController {
    private final UserEntryRepository userEntryRepository;
    @GetMapping("/users")
    public ResponseEntity<List<UserEntry>> findAll(){
        return ResponseEntity.ok(userEntryRepository.findAll());
    }
}
