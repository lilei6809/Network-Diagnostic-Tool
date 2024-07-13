package com.example.networkdiagnostictool.controller;

import com.example.networkdiagnostictool.model.IptablesRule;
import com.example.networkdiagnostictool.service.IpTablesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/iptables")
public class IpTablesController {

    private final IpTablesService ipTablesService;

    @Autowired
    public IpTablesController(IpTablesService ipTablesService) {
        this.ipTablesService = ipTablesService;
    }


    @GetMapping("/available")
    public ResponseEntity<Boolean> isAvailable() {
        log.info("Checking IP tables availability");
        boolean available = ipTablesService.isIpTablesAvailable();
        return ResponseEntity.ok(available);
    }

    @GetMapping("/rules")
    public ResponseEntity<List<String>> listRules() {
        log.info("Listing IP tables rules");
        List<String> rules = ipTablesService.listRules();
        return ResponseEntity.ok(rules);
    }

    @PostMapping("/rule")
    public ResponseEntity<String> addRule(@RequestBody IptablesRule rule) {
        log.info("Adding IP tables rule: {}", rule);
        String result = ipTablesService.addRule(rule);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/rule")
    public ResponseEntity<String> deleteRule(@RequestBody IptablesRule rule) {
        log.info("Deleting IP tables rule: {}", rule);
        String result = ipTablesService.deleteRule(rule);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/rule")
    public ResponseEntity<String> addRule(@RequestParam String rule) {
        log.info("Adding IP tables rule: {}", rule);
        String result = ipTablesService.addRule(rule);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/rule")
    public ResponseEntity<String> deleteRule(@RequestParam String rule) {
        log.info("Deleting IP tables rule: {}", rule);
        String result = ipTablesService.deleteRule(rule);
        return ResponseEntity.ok(result);
    }
}