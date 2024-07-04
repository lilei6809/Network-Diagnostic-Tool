package com.example.networkdiagnostictool.service;

import com.example.networkdiagnostictool.model.ArpEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for ARP cache inspection.
 * This class provides methods to retrieve and parse ARP cache entries from the system.
 */
@Slf4j
@Service
public class ArpCacheService {

    /**
     * Retrieves the ARP cache entries from the system.
     *
     * @return A list of ArpEntry objects representing the ARP cache.
     */
    public List<ArpEntry> getArpCache() {
        List<String> rawEntries = retrieveRawArpEntries();
        return parseArpEntries(rawEntries);
    }

    /**
     * Retrieves the raw ARP cache entries from the system.
     *
     * @return A list of strings, each representing a raw ARP cache entry.
     */
    private List<String> retrieveRawArpEntries() {
        List<String> arpEntries = new ArrayList<>();
        String osName = System.getProperty("os.name").toLowerCase();

        try {
            Process process;
            if (osName.contains("win")) {
                // Windows command to display ARP cache
                process = Runtime.getRuntime().exec("arp -a");
            } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
                // Unix-like systems (Linux, macOS) command to display ARP cache
                process = Runtime.getRuntime().exec("arp -e");
            } else {
                throw new UnsupportedOperationException("Unsupported operating system");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    arpEntries.add(line.trim());
                }
            }
        } catch (Exception e) {
            log.error("Error retrieving ARP cache entries", e);
        }

        return arpEntries;
    }

    /**
     * Parses the raw ARP cache entries into ArpEntry objects.
     *
     * @param rawEntries The list of raw ARP cache entries.
     * @return A list of ArpEntry objects.
     */
    private List<ArpEntry> parseArpEntries(List<String> rawEntries) {
        List<ArpEntry> parsedEntries = new ArrayList<>();
        String osName = System.getProperty("os.name").toLowerCase();

        for (String entry : rawEntries) {
            String[] parts;
            if (osName.contains("win")) {
                // Parse Windows ARP entry format
                parts = entry.split("\\s+");
                if (parts.length >= 3) {
                    parsedEntries.add(new ArpEntry(parts[0], parts[1], parts[2]));
                }
            } else {
                // Parse Unix-like ARP entry format
                parts = entry.split("\\s+");
                if (parts.length >= 4) {
                    parsedEntries.add(new ArpEntry(parts[0], parts[2], parts[3]));
                }
            }
        }

        return parsedEntries;
    }
}