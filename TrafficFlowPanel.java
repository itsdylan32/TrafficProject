//create a panel for the traffic flow

//import packages for the traffic flow panel
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class TrafficFlowPanel extends JPanel {
    private ArrayList<Integer> vehiclePositions;
    private Random random;
    private static final int SPEED = 10; // Constant speed for moving vehicles
    private boolean isTrafficStopped = false; // Flag to control traffic flow

    public TrafficFlowPanel() {
        setPreferredSize(new Dimension(300, 300));
        vehiclePositions = new ArrayList<>();
        random = new Random();
    }

    public void updateTraffic() {
        if (!isTrafficStopped) {
            // Randomly add a new car with a certain probability
            if (random.nextFloat() < 0.1) {
                vehiclePositions.add(0); // Add new vehicle at position 0
            }

            // Move each car
            for (int i = 0; i < vehiclePositions.size(); i++) {
                int newX = vehiclePositions.get(i) + SPEED;
                if (newX > this.getWidth()) {
                    vehiclePositions.remove(i); // Remove vehicle if it goes off-screen
                    i--; // Adjust the index after removal
                } else {
                    vehiclePositions.set(i, newX);
                }
            }
        }
        repaint(); // Repaint to show updated positions
    }

    public void stopTraffic() {
        isTrafficStopped = true; // Stop the traffic
    }

    public void resumeTraffic() {
        isTrafficStopped = false; // Resume the traffic
    }

    public int getVehicleCount() {
        return vehiclePositions.size(); // Number of vehicles currently on screen
    }

    public int getAverageSpeed() {
        return isTrafficStopped ? 0 : SPEED; // Returning 0 if traffic is stopped, otherwise constant speed
    }

    public String getCongestionLevel() {
        // Determine congestion level based on the number of vehicles
        int vehicleCount = vehiclePositions.size();
        if (vehicleCount < 10) {
            return "Low";
        } else if (vehicleCount < 20) {
            return "Medium";
        } else {
            return "High";
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTrafficMap(g);
    }

    private void drawTrafficMap(Graphics g) {
        // Drawing the road
        g.setColor(Color.BLACK);
        g.fillRect(50, 100, this.getWidth(), 50);

        //add a line in the middle of the road
        g.setColor(Color.WHITE);
        for (int i = 0; i < 200; i += 20) {
            g.fillRect(60 + i, 120, 8, 10);
        }

        // Drawing the vehicles
        for (Integer vehicleX : vehiclePositions) {
            g.setColor(Color.BLUE);
            g.fillOval(vehicleX, 104, 15, 15); // Draw each vehicle as a circle
        }
    }
}
