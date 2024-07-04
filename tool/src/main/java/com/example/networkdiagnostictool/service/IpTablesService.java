package com.example.networkdiagnostictool.service;

import com.example.networkdiagnostictool.model.IptablesRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class IpTablesService {

    /**
     * Checks if the system is Linux and IP tables is available.
     *
     * @return true if the system is Linux and IP tables is available, false otherwise.
     */
    public boolean isIpTablesAvailable() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (!osName.contains("linux")) {
            log.warn("IP tables is only available on Linux systems");
            return false;
        }

        try {
            // create a Process object that represents the execution of the which iptables command
            // If iptables is not found, which will not output anything and will exit with a non-zero status
            Process process = Runtime.getRuntime().exec("which iptables");
            // In Unix-like systems, a process typically
            // exits with 0 if it was successful,
            // and a non-zero value if there was an error.
            return process.waitFor() == 0;
        } catch (Exception e) {
            log.error("Error checking for iptables availability", e);
            return false;
        }
    }

    /**
     * Lists all IP tables rules.
     *
     * @return A list of strings, each representing an IP tables rule.
     */
    public List<String> listRules() {
        if (!isIpTablesAvailable()) {
            return List.of("IP tables is not available on this system");
        }

        List<String> rules = new ArrayList<>();
        try {
            // -L: List   -v: verbose   -n: numeric, display IP addresses and port numbers in numeric form
            Process process = Runtime.getRuntime().exec("sudo iptables -L -v -n");

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                rules.add(line);
            }
        } catch (Exception e) {
            log.error("Error listing IP tables rules", e);
            rules.add("Error: " + e.getMessage());
        }
        return rules;
    }

    /**
     * Adds a new IP tables rule.
     *
     * @param rule The rule to add.
     * @return The result of the operation.
     */
    public String addRule(String rule) {
        if (!isIpTablesAvailable()) {
            return "IP tables is not available on this system";
        }

        try {
            Process process = Runtime.getRuntime().exec("sudo iptables " + rule);
            int exitCode = process.waitFor();
            return exitCode == 0 ? "Rule added successfully" : "Failed to add rule";
        } catch (Exception e) {
            log.error("Error adding IP tables rule", e);
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Deletes an IP tables rule.
     *
     * @param rule The rule to delete.
     * @return The result of the operation.
     */
    public String deleteRule(String rule) {
        if (!isIpTablesAvailable()) {
            return "IP tables is not available on this system";
        }

        try {
            Process process = Runtime.getRuntime().exec("sudo iptables -D " + rule);
            int exitCode = process.waitFor();
            return exitCode == 0 ? "Rule deleted successfully" : "Failed to delete rule";
        } catch (Exception e) {
            log.error("Error deleting IP tables rule", e);
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Adds a new IP tables rule.
     *
     * @param rule The rule to add.
     * @return The result of the operation.
     */
    public String addRule(IptablesRule rule) {
        if (!isIpTablesAvailable()) {
            return "IP tables is not available on this system";
        }

        try {
            String command = "sudo iptables " + rule.toIptablesCommand();
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            return exitCode == 0 ? "Rule added successfully" : "Failed to add rule";
        } catch (Exception e) {
            log.error("Error adding IP tables rule", e);
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Deletes an IP tables rule.
     *
     * @param rule The rule to delete.
     * @return The result of the operation.
     */
    public String deleteRule(IptablesRule rule) {
        if (!isIpTablesAvailable()) {
            return "IP tables is not available on this system";
        }

        try {
            String command = "sudo iptables -D " + rule.toIptablesCommand().substring(3); // remove "-A " prefix
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            return exitCode == 0 ? "Rule deleted successfully" : "Failed to delete rule";
        } catch (Exception e) {
            log.error("Error deleting IP tables rule", e);
            return "Error: " + e.getMessage();
        }
    }
}