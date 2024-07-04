# Network Diagnostic Tool

## Overview
The Network Diagnostic Tool is a comprehensive Java-based application designed for network administrators and IT professionals. It provides a suite of functionalities for diagnosing network issues, inspecting network configurations, and managing firewall rules through a user-friendly RESTful API.

## Features
- **TCP/IP Diagnostics**: Perform ping tests, port checks, and latency measurements.
- **ARP Cache Inspection**: View and analyze the Address Resolution Protocol (ARP) cache.
- **IP Tables Management**: Add, remove, and list IP Tables rules (Linux only).
- **Cross-Platform Compatibility**: Supports both Windows and Linux operating systems.
- **RESTful API**: Easy-to-use API for remote network diagnostics and management.

## Technologies Used
- Java 17
- Spring Boot 3.x
- Lombok
- Maven

## Prerequisites
- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher
- (Optional) Docker for containerized deployment

## Setup and Installation
1. Clone the repository:
   ```
   git clone https://github.com/lilei6809/network-diagnostic-tool.git
   ```

2. Navigate to the project directory:
   ```
   cd network-diagnostic-tool
   ```

3. Build the project:
   ```
   mvn clean install
   ```

4. Run the application:
   ```
   java -jar target/network-diagnostic-tool-1.0.0.jar
   ```

## Usage

### TCP/IP Diagnostics
- Ping a host:
  ```
  GET /api/tcpip/ping?host=example.com
  ```

- Check if a port is open:
  ```
  GET /api/tcpip/port?host=example.com&port=80
  ```

- Measure latency:
  ```
  GET /api/tcpip/latency?host=example.com&port=80
  ```

### ARP Cache Inspection
- View ARP cache:
  ```
  GET /api/arp/cache
  ```

### IP Tables Management (Linux only)
- List IP Tables rules:
  ```
  GET /api/iptables/rules
  ```

- Add a new rule:
  ```
  POST /api/iptables/rule
  Content-Type: application/json
  
  {
    "chain": "INPUT",
    "sourceIp": "192.168.1.0/24",
    "protocol": "TCP",
    "destinationPort": 80,
    "action": "ACCEPT"
  }
  ```

- Delete a rule:
  ```
  DELETE /api/iptables/rule
  Content-Type: application/json
  
  {
    "chain": "INPUT",
    "sourceIp": "192.168.1.0/24",
    "protocol": "TCP",
    "destinationPort": 80,
    "action": "ACCEPT"
  }
  ```

