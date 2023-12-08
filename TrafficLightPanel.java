//import packages for the trffic light panel

import javax.swing.*;
import java.awt.*;

public class TrafficLightPanel extends JPanel {
    private Color lightColor = Color.RED;
    private Timer timer;

    // Define separate durations for each color
    private static final int RED_DURATION = 5000;   // 5 seconds for red
    private static final int GREEN_DURATION = 5000; // 5 seconds for green
    private static final int YELLOW_DURATION = 2000; // 2 seconds for yellow

    public TrafficLightPanel() {
        setPreferredSize(new Dimension(100, 300));
        timer = new Timer(RED_DURATION, e -> cycleTrafficLight());
        timer.start();
    }

    public void cycleTrafficLight() {
        if (lightColor.equals(Color.RED)) {
            lightColor = Color.GREEN;
            timer.setDelay(GREEN_DURATION);
        } else if (lightColor.equals(Color.GREEN)) {
            lightColor = Color.YELLOW;
            timer.setDelay(YELLOW_DURATION);
        } else if (lightColor.equals(Color.YELLOW)) {
            lightColor = Color.RED;
            timer.setDelay(RED_DURATION);
        }
        repaint();
    }

    public boolean isRed() {
        return lightColor.equals(Color.RED);
    }

    public boolean isGreen() {
        return lightColor.equals(Color.GREEN);
    }

    public boolean isYellow() {
        return lightColor.equals(Color.YELLOW);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(lightColor);
        g.fillOval(50, 50, 50, 50); // Draw the traffic light
    }
}
