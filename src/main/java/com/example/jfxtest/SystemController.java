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
        return os.toString();
    }

    public String getCPU() {
        CentralProcessor processor = hardware.getProcessor();
        return processor.toString();
    }

    public String getMemory() {
        return hardware.getMemory().toString();
    }

    public String getComputerName() {
        return System.getenv("COMPUTERNAME");
    }

    public String getTotalMemory() {
        GlobalMemory memory = hardware.getMemory();
        return memory.getTotal() / (1024 * 1024 * 1024) + " GB";
    }

    public String getSystemBitness() {
        return System.getProperty("sun.arch.data.model") + "-bit";
    }

    public Double getCPULoad () {
        CentralProcessor processor = hardware.getProcessor();
        return processor.getSystemCpuLoad(300);
    }
}
