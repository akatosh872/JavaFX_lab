package com.example.jfxtest;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class DisplayController {
    @FXML
    private StackPane mainPanel;

    @FXML
    private Pane osPane;
    @FXML
    private Pane cpuPane;
    @FXML
    private Pane memoryPane;

    @FXML
    private Label osLabel;
    @FXML
    private Label cpuLabel;
    @FXML
    private Label memoryLabel;

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

    private static final int MAX_DATA_POINTS = 10;

    public void initialize() {
        systemController = new SystemController();
        osLabel.setText(systemController.getOS());
        cpuLabel.setText(systemController.getCPU());
        memoryLabel.setText(systemController.getMemory());

        initializeCharts();
        initializeTimelines();

        CPUtabs.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if ("CPU Loading".equals(newTab.getText())) {
                cpuLoadTimeline.play();
            } else {
                cpuLoadTimeline.stop();
            }
        });

        showOSInfo();
    }

    private void initializeCharts() {
        cpuLoadSeries = new XYChart.Series<>();
        cpuLoadChart.getData().add(cpuLoadSeries);
        ((NumberAxis) cpuLoadChart.getXAxis()).setForceZeroInRange(false);
        cpuLoadChart.setAnimated(false);

        memorySeries = new XYChart.Series<>();
        memoryChart.getData().add(memorySeries);
        ((NumberAxis) memoryChart.getXAxis()).setForceZeroInRange(false);
        memoryChart.setAnimated(false);
    }

    private void initializeTimelines() {
        memoryTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateMemoryChart()));
        memoryTimeline.setCycleCount(Timeline.INDEFINITE);

        cpuLoadTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateCpuLoadChart()));
        cpuLoadTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void updateMemoryChart() {
        double usedMemoryPercentage = systemController.getUsedMemoryPercentage();
        memorySeries.getData().add(new XYChart.Data<>(memoryIndex++, usedMemoryPercentage));
        if (memorySeries.getData().size() > MAX_DATA_POINTS) {
            memorySeries.getData().remove(0);
        }
        ((NumberAxis) memoryChart.getXAxis()).setLowerBound(memoryIndex - MAX_DATA_POINTS);
        ((NumberAxis) memoryChart.getXAxis()).setUpperBound(memoryIndex - 1);
    }

    private void updateCpuLoadChart() {
        double cpuLoad = systemController.getCPULoad();
        cpuLoadSeries.getData().add(new XYChart.Data<>(cpuLoadIndex++, cpuLoad * 100));
        if (cpuLoadSeries.getData().size() > MAX_DATA_POINTS) {
            cpuLoadSeries.getData().remove(0);
        }
        ((NumberAxis) cpuLoadChart.getXAxis()).setLowerBound(cpuLoadIndex - MAX_DATA_POINTS);
        ((NumberAxis) cpuLoadChart.getXAxis()).setUpperBound(cpuLoadIndex - 1);
    }

    @FXML
    private void showOSInfo() {
        hideAllPanes();
        osPane.setVisible(true);
    }

    @FXML
    private void showCPUInfo() {
        hideAllPanes();
        cpuPane.setVisible(true);
        CPUtabs.getSelectionModel().select(0);
    }

    @FXML
    private void showCPULoading() {
        hideAllPanes();
        cpuPane.setVisible(true);
        CPUtabs.getSelectionModel().select(1);
    }

    @FXML
    private void showMemoryInfo() {
        hideAllPanes();
        memoryPane.setVisible(true);
        memoryTimeline.play();
    }

    private void hideAllPanes() {
        osPane.setVisible(false);
        cpuPane.setVisible(false);
        memoryPane.setVisible(false);
        memoryTimeline.stop();
        cpuLoadTimeline.stop();
    }
}
