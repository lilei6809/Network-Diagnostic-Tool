package com.example.networkdiagnostictool.controller;

import com.example.networkdiagnostictool.service.TcpIpDiagnosticService;
import com.example.networkdiagnostictool.validator.HostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for TCP/IP diagnostic operations with input validation.
 * This controller provides endpoints for basic network diagnostics
 * such as ping, port checking, and latency measurement.
 */
@RestController
@RequestMapping("/api/tcpip")
public class TcpIpDiagnosticController {

    private final TcpIpDiagnosticService tcpIpDiagnosticService;
    private final HostValidator hostValidator;

    /**
     * Constructor for dependency injection of TcpIpDiagnosticService and HostValidator.
     *
     * @param tcpIpDiagnosticService The service to be used for TCP/IP diagnostics.
     * @param hostValidator The validator for hostnames and IP addresses.
     */
    @Autowired
    public TcpIpDiagnosticController(TcpIpDiagnosticService tcpIpDiagnosticService, HostValidator hostValidator) {
        this.tcpIpDiagnosticService = tcpIpDiagnosticService;
        this.hostValidator = hostValidator;
    }

    /**
     * Endpoint for pinging a host.
     *
     * @param host The hostname or IP address to ping.
     * @return ResponseEntity containing the ping result or an error message.
     */
    @GetMapping("/ping")
    public ResponseEntity<?> pingHost(@RequestParam String host) {
        if (!hostValidator.isValidHost(host)) {
            return ResponseEntity.badRequest().body("Invalid hostname or IP address");
        }
        boolean result = tcpIpDiagnosticService.pingHost(host);
        return ResponseEntity.ok(result);
    }

    /**
     * Endpoint for checking if a specific port on a host is open.
     *
     * @param host The hostname or IP address to check.
     * @param port The port number to check.
     * @return ResponseEntity containing the port check result or an error message.
     */
    @GetMapping("/port")
    public ResponseEntity<?> checkPort(@RequestParam String host, @RequestParam int port) {
        if (!hostValidator.isValidHost(host)) {
            return ResponseEntity.badRequest().body("Invalid hostname or IP address");
        }
        if (port < 1 || port > 65535) {
            return ResponseEntity.badRequest().body("Invalid port number. Must be between 1 and 65535");
        }
        int result = tcpIpDiagnosticService.checkPort(host, port);
        return ResponseEntity.ok(result);
    }

    /**
     * Endpoint for measuring latency to a specific host and port.
     *
     * @param host The hostname or IP address to measure latency to.
     * @param port The port number to connect to.
     * @return ResponseEntity containing the latency measurement or an error message.
     */
    @GetMapping("/latency")
    public ResponseEntity<?> measureLatency(@RequestParam String host, @RequestParam int port) {
        if (!hostValidator.isValidHost(host)) {
            return ResponseEntity.badRequest().body("Invalid hostname or IP address");
        }
        if (port < 1 || port > 65535) {
            return ResponseEntity.badRequest().body("Invalid port number. Must be between 1 and 65535");
        }
        long result = tcpIpDiagnosticService.measureLatency(host, port);
        return ResponseEntity.ok(result);
    }
}