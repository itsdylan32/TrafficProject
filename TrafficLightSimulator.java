//Dylan Howell Traffic light simulator problem
//Date 12/5/23
//This program will simulate a traffic light and display the time and data analytics

//start with imports

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrafficLightSimulator {

    // Labels for displaying current time and vehicle count
    private static JLabel currentTimeLabel;
    private static JLabel vehicleCountLabel;
    private static JLabel averageSpeedLabel;
    private static JLabel congestionLevelLabel;

    // Traffic flow panels
    private static TrafficFlowPanel trafficFlowPanel1 = new TrafficFlowPanel();
    private static TrafficFlowPanel trafficFlowPanel2 = new TrafficFlowPanel();
    private static TrafficFlowPanel trafficFlowPanel3 = new TrafficFlowPanel();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Traffic Congestion Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel timeDisplayPanel = createTimeDisplayPanel();
        frame.add(timeDisplayPanel, BorderLayout.NORTH);

        JPanel mainTrafficPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        addCombinedPanels(mainTrafficPanel);
        frame.add(mainTrafficPanel, BorderLayout.CENTER);

        JPanel dataAnalyticsPanel = createDataAnalyticsPanel();
        frame.add(dataAnalyticsPanel, BorderLayout.SOUTH);

        new Timer(1000, e -> updateAnalyticsPanel()).start();

        frame.pack();
        frame.setVisible(true);
    }

    private static JPanel createTimeDisplayPanel() {
        JPanel timeDisplayPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
        currentTimeLabel = new JLabel("Current Time: ");
        timeDisplayPanel.add(currentTimeLabel);

        new Timer(1000, e -> currentTimeLabel.setText("Current Time: " + new SimpleDateFormat("hh:mm:ss a").format(new Date()))).start();
        return timeDisplayPanel;
    }

    private static JPanel createDataAnalyticsPanel() {
        JPanel dataAnalyticsPanel = new JPanel();
        dataAnalyticsPanel.setBorder(BorderFactory.createTitledBorder("Data Analytics"));

        vehicleCountLabel = new JLabel("Vehicle Count: 0");
        averageSpeedLabel = new JLabel("Average Speed: 0 km/h");
        congestionLevelLabel = new JLabel("Congestion Level: Low");

        dataAnalyticsPanel.add(vehicleCountLabel);
        dataAnalyticsPanel.add(averageSpeedLabel);
        dataAnalyticsPanel.add(congestionLevelLabel);

        return dataAnalyticsPanel;
    }

    private static void addCombinedPanels(JPanel mainTrafficPanel) {
        for (int i = 0; i < 3; i++) {
            TrafficLightPanel trafficLightPanel = new TrafficLightPanel();
            TrafficFlowPanel trafficFlowPanel = selectTrafficFlowPanel(i);

            setupTimer(trafficLightPanel, trafficFlowPanel, 1000); // Timer for traffic light control

            JPanel combinedPanel = new JPanel(new BorderLayout());
            combinedPanel.add(trafficLightPanel, BorderLayout.NORTH);
            combinedPanel.add(trafficFlowPanel, BorderLayout.CENTER);

            mainTrafficPanel.add(combinedPanel);
        }
    }

    private static TrafficFlowPanel selectTrafficFlowPanel(int index) {
        if (index == 0) {
            return trafficFlowPanel1;
        } else if (index == 1) {
            return trafficFlowPanel2;
        } else {
            return trafficFlowPanel3;
        }
    }

    private static void setupTimer(TrafficLightPanel lightPanel, TrafficFlowPanel flowPanel, int delay) {
        new Timer(delay, e -> {
            if (lightPanel.isRed()) {
                flowPanel.stopTraffic();
            } else {
                flowPanel.resumeTraffic();
            }
            flowPanel.updateTraffic();
        }).start();
    }

    private static void updateAnalyticsPanel() {
        int totalVehicleCount = trafficFlowPanel1.getVehicleCount() +
                                trafficFlowPanel2.getVehicleCount() +
                                trafficFlowPanel3.getVehicleCount();

        int totalSpeed = trafficFlowPanel1.getAverageSpeed() +
                         trafficFlowPanel2.getAverageSpeed() +
                         trafficFlowPanel3.getAverageSpeed();

        int averageSpeed = (totalVehicleCount > 0) ? totalSpeed / totalVehicleCount : 0;

        vehicleCountLabel.setText("Vehicle Count: " + totalVehicleCount);
        averageSpeedLabel.setText("Average Speed: " + averageSpeed + " km/h");
        congestionLevelLabel.setText("Congestion Level: " + (averageSpeed > 40 ? "High" : "Low"));
    }
}
