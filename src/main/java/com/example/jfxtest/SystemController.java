package com.example.jfxtest;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class SystemController {
    private final HardwareAbstractionLayer hardware;
    private final OperatingSystem os;

    public SystemController() {
        SystemInfo si = new SystemInfo();
        this.hardware = si.getHardware();
        this.os = si.getOperatingSystem();
    }

    public String getOS() {
        StringBuilder osInfo = new StringBuilder();
        osInfo.append("OS: ").append(os.toString()).append("\n");
        osInfo.append("Family: ").append(os.getFamily()).append("\n");
        osInfo.append("Manufacturer: ").append(os.getManufacturer()).append("\n");
        osInfo.append("Computer name: ").append(System.getenv("COMPUTERNAME")).append("\n");
        osInfo.append("System bytness: ").append(System.getProperty("sun.arch.data.model")).append("-bit").append("\n");
        osInfo.append("Version: ").append(os.getVersionInfo().toString()).append("\n");
        osInfo.append("Booted Uptime: ").append(formatUptime(os.getSystemBootTime())).append("\n");
        osInfo.append("Current Process ID: ").append(os.getProcessId()).append("\n");
        osInfo.append("Current Thread Count: ").append(os.getThreadCount()).append("\n");
        osInfo.append("Process Count: ").append(os.getProcessCount()).append("\n");
        return osInfo.toString();
    }

    private String formatUptime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    public GlobalMemory getMemory() {
        return hardware.getMemory();
    }

    public double getUsedMemoryPercentage() {
        GlobalMemory memory = hardware.getMemory();
        return (double) (memory.getTotal() - memory.getAvailable()) / memory.getTotal() * 100;
    }

    public String getCPU() {
        CentralProcessor processor = hardware.getProcessor();
        StringBuilder cpuInfo = new StringBuilder();

        cpuInfo.append("Physical CPU packages: ").append(processor.getPhysicalPackageCount()).append("\n");
        cpuInfo.append("Physical CPU cores: ").append(processor.getPhysicalProcessorCount()).append("\n");
        cpuInfo.append("Logical CPUs: ").append(processor.getLogicalProcessorCount()).append("\n");
        cpuInfo.append("Switches: ").append(processor.getContextSwitches()).append("\n");
        cpuInfo.append("Identifier: ").append(processor.getProcessorIdentifier()).append("\n");
        return cpuInfo.toString();
    }

    public Double getCPULoad() {
        CentralProcessor processor = hardware.getProcessor();
        return processor.getSystemCpuLoad(300);
    }
}
