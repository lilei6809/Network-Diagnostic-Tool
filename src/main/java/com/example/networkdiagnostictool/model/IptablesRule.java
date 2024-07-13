package com.example.networkdiagnostictool.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IptablesRule {
    private Chain chain;
    private String sourceIp;
    private String destinationIp;
    private Protocol protocol;
    private Integer sourcePort;
    private Integer destinationPort;
    private Action action;

    public enum Chain {
        INPUT, OUTPUT, FORWARD
    }

    public enum Protocol {
        TCP, UDP, ICMP
    }

    public enum Action {
        ACCEPT, DROP, REJECT
    }

    public String toIptablesCommand() {
        StringBuilder command = new StringBuilder();
        command.append("-A ").append(chain);

        if (sourceIp != null) {
            command.append(" -s ").append(sourceIp);
        }
        if (destinationIp != null) {
            command.append(" -d ").append(destinationIp);
        }
        if (protocol != null) {
            command.append(" -p ").append(protocol.toString().toLowerCase());
        }
        if (sourcePort != null) {
            command.append(" --sport ").append(sourcePort);
        }
        if (destinationPort != null) {
            command.append(" --dport ").append(destinationPort);
        }
        command.append(" -j ").append(action);

        return command.toString();
    }
}