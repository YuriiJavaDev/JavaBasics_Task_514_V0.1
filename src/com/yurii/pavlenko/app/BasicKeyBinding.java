package com.yurii.pavlenko.app;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ColorUIResource;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Custom frame architecture isolating primary InputMap and ActionMap key binding processing pipelines.
 */
public class BasicKeyBinding extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BasicKeyBinding app = new BasicKeyBinding();
            app.setVisible(true);
        });
    }

    /**
     * Constructs the application frame and wires input hotkey mapping hooks.
     */
    public BasicKeyBinding() {
        super("Basic Key Binding Demo");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        // Core single visual presentation panel container targeting mutations
        JPanel targetPanel = new JPanel(new BorderLayout());
        targetPanel.setBackground(Color.LIGHT_GRAY);
        add(targetPanel);

        // Explicit visual dynamic help prompt initialized on the center layout
        JLabel infoLabel = new JLabel("Press CTRL + G to turn green or CTRL + B to turn blue", JLabel.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        targetPanel.add(infoLabel, BorderLayout.CENTER);

        // Define the target hotkey configurations via explicit combination masks
        KeyStroke ctrlGKey = KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK);
        KeyStroke ctrlBKey = KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK);

        // Configuration for the ESCAPE key without any modifier masks
        KeyStroke escKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        // Define unique action string token identifiers
        String greenToken = "changeColorToGreenAction";
        String blueToken = "changeColorToBlueAction";
        String resetToken = "resetToGrayAction";

        // Retrieve core input and action maps bound to the focused window scope
        InputMap inputMap = targetPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = targetPanel.getActionMap();

        // Pair the physical keystrokes with their corresponding abstract named tokens
        inputMap.put(ctrlGKey, greenToken);
        inputMap.put(ctrlBKey, blueToken);
        inputMap.put(escKey, resetToken);

        // Bind the green string token to its execution behavior block
        actionMap.put(greenToken, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                targetPanel.setBackground(new ColorUIResource(0, 150, 0));
                infoLabel.setText("Press CTRL + B to turn blue (or ESC to reset)");
                infoLabel.setForeground(Color.WHITE);
            }
        });

        // Bind the blue string token to its execution behavior block
        actionMap.put(blueToken, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                targetPanel.setBackground(new ColorUIResource(0, 120, 170));
                infoLabel.setText("Press CTRL + G to turn green (or ESC to reset)");
                infoLabel.setForeground(Color.WHITE);
            }
        });

        // Bind the escape string token to reset the initial state properties
        actionMap.put(resetToken, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                targetPanel.setBackground(Color.LIGHT_GRAY);
                infoLabel.setText("Press CTRL + G to turn green or CTRL + B to turn blue");
                infoLabel.setForeground(Color.BLACK);
            }
        });
    }
}
