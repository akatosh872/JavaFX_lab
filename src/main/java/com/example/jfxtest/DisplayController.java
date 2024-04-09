package com.example.jfxtest;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class DisplayController {
    @FXML
    private Label osLabel;

    @FXML
    private Label systemBitnessLabel;

    @FXML
    private Label computerNameLabel;

    @FXML
    private Label cpuLabel;

    @FXML
    private Label memoryLabel;

    @FXML
    private Label totalMemoryLabel;

    @FXML
    private Pane cpuLoadPane;

    @FXML
    private TabPane CPUtabs;

    private AnimationTimer cpuLoadTimer;

    private SystemController systemController;

    @FXML
    private LineChart<Number, Number> cpuLoadChart;
    private XYChart.Series<Number, Number> cpuLoadSeries;
    private int cpuLoadIndex = 0;

    public void initialize() {
        systemController = new SystemController();
        osLabel.setText(systemController.getOS());
        cpuLabel.setText(systemController.getCPU());
        memoryLabel.setText(systemController.getMemory());
        computerNameLabel.setText(systemController.getComputerName());
        totalMemoryLabel.setText(systemController.getTotalMemory());
        systemBitnessLabel.setText(systemController.getSystemBitness());

        cpuLoadSeries = new XYChart.Series<>();
        cpuLoadChart.getData().add(cpuLoadSeries);

        cpuLoadTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double cpuLoad = systemController.getCPULoad();
                cpuLoadSeries.getData().add(new XYChart.Data<>(cpuLoadIndex++, cpuLoad * 100));
                // Remove old data to keep the chart clean
                if (cpuLoadSeries.getData().size() > 200) {
                    cpuLoadSeries.getData().remove(0);
                }
            }
        };

        CPUtabs.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab.getText().equals("CPU Loading")) {
                cpuLoadTimer.start();
            } else {
                cpuLoadTimer.stop();
            }
        });

        hideAllElements();
    }

    @FXML
    private void showOSInfo() {
        // Hide all elements
        hideAllElements();
        // Show OS related elements
        osLabel.setVisible(true);
        systemBitnessLabel.setVisible(true);
        computerNameLabel.setVisible(true);
    }

    @FXML
    private void showCPUInfo() {
        // Hide all elements
        hideAllElements();
        // Show CPU related elements
        CPUtabs.setVisible(true);
        cpuLabel.setVisible(true);

        cpuLoadPane.setVisible(true);
        cpuLoadChart.setVisible(true);

        CPUtabs.getSelectionModel().select(0);
        cpuLoadTimer.start();
    }

    @FXML
    private void showCPULoading() {
        // Hide all elements
        hideAllElements();
        // Show CPU Loading related elements
        CPUtabs.setVisible(true);
        cpuLabel.setVisible(true);

        cpuLoadPane.setVisible(true);
        cpuLoadChart.setVisible(true);

        cpuLoadTimer.start();
        CPUtabs.getSelectionModel().select(1);
    }

    private void hideAllElements() {
        osLabel.setVisible(false);
        CPUtabs.setVisible(false);
        systemBitnessLabel.setVisible(false);
        computerNameLabel.setVisible(false);
        cpuLabel.setVisible(false);
        memoryLabel.setVisible(false);
        totalMemoryLabel.setVisible(false);
        cpuLoadPane.setVisible(false);
        cpuLoadChart.setVisible(false);
        cpuLoadTimer.stop();
    }
}