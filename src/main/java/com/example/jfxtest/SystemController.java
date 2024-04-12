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
        String osInfo = "";
        osInfo += "OS: " + os.toString() + "\n";
        osInfo += "Family: " + os.getFamily() + "\n";
        osInfo += "Manufacturer: " + os.getManufacturer() + "\n";
        osInfo += "Computer name: " + System.getenv("COMPUTERNAME") + "\n";
        osInfo += "System bytness: " + System.getProperty("sun.arch.data.model") + "-bit" + "\n";
        osInfo += "Version: " + os.getVersionInfo().toString() + "\n";
        osInfo += "Booted Uptime: " + formatUptime(os.getSystemBootTime()) + "\n";
        osInfo += "Current Process ID: " + os.getProcessId() + "\n";
        osInfo += "Current Thread Count: " + os.getThreadCount() + "\n";
        osInfo += "Process Count: " + os.getProcessCount() + "\n";
        return osInfo;
    }

    private String formatUptime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    public String getMemory() {
        return hardware.getMemory().toString();
    }

    public String getTotalMemory() {
        GlobalMemory memory = hardware.getMemory();
        return memory.getTotal() / (1024 * 1024 * 1024) + " GB";
    }

    public double getUsedMemoryPercentage() {
        GlobalMemory memory = hardware.getMemory();
        return (double) (memory.getTotal() - memory.getAvailable()) / (1024 * 1024 * 1024);
    }

    public String getCPU() {
        CentralProcessor processor = hardware.getProcessor();
        return processor.toString();
    }

    public Double getCPULoad () {
        CentralProcessor processor = hardware.getProcessor();
        return processor.getSystemCpuLoad(300);
    }
}
