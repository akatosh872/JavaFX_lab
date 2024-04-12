package com.example.jfxtest;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class DisplayController {
    @FXML
    private Label osLabel;

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

    private SystemController systemController;

    private Timeline memoryTimeline;

    private Timeline cpuLoadTimeline;

    @FXML
    private LineChart<Number, Number> cpuLoadChart;
    private XYChart.Series<Number, Number> cpuLoadSeries;
    private int cpuLoadIndex = 0;

    @FXML
    private LineChart<Number, Number> memoryChart;
    private XYChart.Series<Number, Number> memorySeries;
    private int memoryIndex = 0;

    public void initialize() {
        systemController = new SystemController();
        osLabel.setText(systemController.getOS());
        cpuLabel.setText(systemController.getCPU());
        memoryLabel.setText(systemController.getMemory());
        totalMemoryLabel.setText(systemController.getTotalMemory());

        cpuLoadSeries = new XYChart.Series<>();
        cpuLoadChart.getData().add(cpuLoadSeries);

        memorySeries = new XYChart.Series<>();
        memoryChart.getData().add(memorySeries);


        memoryTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            double usedMemory = systemController.getUsedMemoryPercentage();
            memorySeries.getData().add(new XYChart.Data<>(memoryIndex++, usedMemory));
            if (memorySeries.getData().size() > 30) {
                memorySeries.getData().remove(0);
            }
        }));
        memoryTimeline.setCycleCount(Timeline.INDEFINITE);
        memoryTimeline.play();

        cpuLoadTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            double cpuLoad = systemController.getCPULoad();
            cpuLoadSeries.getData().add(new XYChart.Data<>(cpuLoadIndex++, cpuLoad * 100));
            if (cpuLoadSeries.getData().size() > 200) {
                cpuLoadSeries.getData().remove(0);
            }
        }));
        cpuLoadTimeline.setCycleCount(Timeline.INDEFINITE);
        cpuLoadTimeline.play();

        CPUtabs.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab.getText().equals("CPU Loading")) {
                cpuLoadTimeline.play();
            } else {
                cpuLoadTimeline.stop();
            }
        });

        showOSInfo();
    }

    @FXML
    private void showOSInfo() {
        // Hide all elements
        hideAllElements();
        // Show OS related elements
        osLabel.setVisible(true);
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
        cpuLoadTimeline.play();
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

        cpuLoadTimeline.play();
        CPUtabs.getSelectionModel().select(1);
    }

    @FXML
    private void showMemoryInfo() {
        // Hide all elements
        hideAllElements();
        // Show Memory related elements
        memoryChart.setVisible(true);
        memoryLabel.setVisible(true);
        totalMemoryLabel.setVisible(true);

        memoryTimeline.play();
    }

    private void hideAllElements() {
        osLabel.setVisible(false);
        CPUtabs.setVisible(false);
        cpuLabel.setVisible(false);
        memoryLabel.setVisible(false);
        totalMemoryLabel.setVisible(false);
        cpuLoadPane.setVisible(false);
        cpuLoadChart.setVisible(false);
        memoryChart.setVisible(false);
        memoryTimeline.stop();
        cpuLoadTimeline.stop();
    }
}