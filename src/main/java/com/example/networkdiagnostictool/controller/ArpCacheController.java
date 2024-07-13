package com.example.networkdiagnostictool.controller;

import com.example.networkdiagnostictool.model.ArpEntry;
import com.example.networkdiagnostictool.service.ArpCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for ARP cache inspection operations.
 * This controller provides endpoints for retrieving ARP cache information.
 */
@Slf4j
@RestController
@RequestMapping("/api/arp")
public class ArpCacheController {

    private final ArpCacheService arpCacheService;

    /**
     * Constructor for dependency injection of ArpCacheService.
     *
     * @param arpCacheService The service to be used for ARP cache operations.
     */
    @Autowired
    public ArpCacheController(ArpCacheService arpCacheService) {
        this.arpCacheService = arpCacheService;
    }

    /**
     * Endpoint for retrieving the ARP cache entries.
     *
     * @return ResponseEntity containing the list of ARP cache entries.
     */
    @GetMapping("/cache")
    public ResponseEntity<List<ArpEntry>> getArpCache() {
        log.info("Received request to retrieve ARP cache");
        List<ArpEntry> arpEntries = arpCacheService.getArpCache();
        log.debug("Retrieved {} ARP cache entries", arpEntries.size());
        return ResponseEntity.ok(arpEntries);
    }
}