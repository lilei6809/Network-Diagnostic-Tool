package com.example.networkdiagnostictool.service;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.*;

/**
 * Service class for performing TCP/IP related diagnostic operations.
 * This class provides methods for basic network diagnostics such as
 * ping, port checking, and latency measurement.
 */
@Service
public class TcpIpDiagnosticService {

    /**
     * Attempts to ping the specified host.
     *
     * @param host The hostname or IP address to ping.
     * @return true if the host is reachable, false otherwise.
     */
    public boolean pingHost(String host) {
        try {
            // Resolve the host to an IP address
            InetAddress address = InetAddress.getByName(host);

            // Attempt to reach the host with a 5-second timeout
            return address.isReachable(5000); // 5 seconds timeout
        } catch (IOException e) {
            // If an exception occurs (e.g., unknown host), consider it unreachable
            return false;
        }
    }

    /**
     * Checks if a specific port on the given host is open.
     *
     * @param host The hostname or IP address to check.
     * @param port The port number to check.
     * @return The port number if it's open, -1 if it's closed or an error occurred.
     */
    public int checkPort(String host, int port) {
        try (Socket socket = new Socket()) {
            // Attempt to connect to the specified host and port
            socket.connect(new InetSocketAddress(host, port), 5000); // 5 seconds timeout

            // If connection is successful, return the port number
            return socket.getPort();
        } catch (IOException e) {
            // If an exception occurs (e.g., connection refused), consider the port closed
            return -1; // Port is not open
        }
    }

    /**
     * Measures the latency to a specific host and port.
     *
     * @param host The hostname or IP address to measure latency to.
     * @param port The port number to connect to.
     * @return The latency in milliseconds, or -1 if unable to connect.
     */
    public long measureLatency(String host, int port) {
        long start = System.currentTimeMillis();
        if (checkPort(host, port) != -1) {
            long end = System.currentTimeMillis();
            return end - start; // Return the time taken to connect
        }
        return -1; // Unable to connect
    }
}