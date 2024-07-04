package com.example.networkdiagnostictool.validator;

import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * Validator class for hostnames and IP addresses.
 * This class provides methods to validate if a given string is a valid hostname or IP address.
 */
@Component
public class HostValidator {

    // Regular expression for validating IPv4 addresses
    private static final String IPV4_REGEX =
            "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    private static final Pattern IPV4_PATTERN = Pattern.compile(IPV4_REGEX);

    /**
     * Validates if the given string is a valid hostname or IP address.
     *
     * @param host The string to validate.
     * @return true if the string is a valid hostname or IP address, false otherwise.
     */
    public boolean isValidHost(String host) {
        if (host == null || host.isEmpty()) {
            return false;
        }

        // Check if it's a valid IPv4 address
        if (IPV4_PATTERN.matcher(host).matches()) {
            return true;
        }

        // If not an IP address, check if it's a valid hostname
        try {
            InetAddress.getByName(host);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}