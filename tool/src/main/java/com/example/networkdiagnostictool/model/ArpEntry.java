package com.example.networkdiagnostictool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an entry in the ARP (Address Resolution Protocol) cache.
 * Each entry contains information about the mapping between an IP address
 * and its corresponding MAC address on a specific network interface.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArpEntry {
    /**
     * The IP address associated with this entry.
     */
    private String ipAddress;

    /**
     * The MAC address corresponding to the IP address.
     */
    private String macAddress;

    /**
     * The name of the network interface for this entry.
     */
    private String interfaceName;
}