# Component Event Interaction: Basic Key Binding (JavaBasics_Task_514_V0.1)

## 📖 Description
In industrial graphical software development, using primitive AWT `KeyListener` sensors often introduces fragile focus management bugs. To establish highly reliable keyboard navigation pathways, production desktop architectures implement Java Swing's **Key Bindings** subsystem. This project isolates the interaction mechanics of **`InputMap`** and **`ActionMap`** components. By mapping the physical keystroke matrix **`CTRL + G`** inside the input layer, the registry routes the user gesture directly to an abstract named token. The underlying action engine captures this token and evaluates our custom closure, shifting the core **`JPanel`** background properties to green.

## 📋 Requirements Compliance
- **Input Matrix Registration**: Configured an `InputMap` wrapper tracking the custom `control G` keystroke pattern.
- **Action Abstraction Separation**: Decoupled the keystroke from execution logic by using an intermediate named key string token.
- **Dynamic State Mutator**: Programmed a custom `AbstractAction` subclass changing the panel context background to green.
- **Focus Independence**: Utilized `WHEN_IN_FOCUSED_WINDOW` bounds to ensure hotkey tracking executes seamlessly across child layouts.

## 🚀 Architectural Stack
- Java 17+ (Java AWT Event Delegation Model, Java Swing)

## 🏗️ Implementation Details
- **BasicKeyBinding**: The primary layout orchestration frame assembling input registers, action mappings, and content canvases.

## 📋 Expected result
*(Pressing the Ctrl and G keys simultaneously triggers the background transformation sequence instantly)*
- **Result state**: The main panel canvas background color shifts from light gray to `Color.GREEN`.

## 💻 Code Example

Project Structure:

    JavaBasics_Task_514/
    ├── src/
    │   └── com/yurii/pavlenko/
    │                 └── app/
    │                     └── BasicKeyBinding.java
    ├── LICENSE
    ├── TASK.md
    ├── THEORY.md
    └── README.md

Code
```java
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

public class BasicKeyBinding extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BasicKeyBinding app = new BasicKeyBinding();
            app.setVisible(true);
        });
    }

    public BasicKeyBinding() {
        super("Basic Key Binding Demo");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        JPanel targetPanel = new JPanel(new BorderLayout());
        targetPanel.setBackground(Color.LIGHT_GRAY);
        add(targetPanel);

        JLabel infoLabel = new JLabel("Press CTRL + G to turn green", JLabel.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        targetPanel.add(infoLabel, BorderLayout.CENTER);

        KeyStroke ctrlGKey = KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK);
        KeyStroke ctrlBKey = KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK);

        String greenToken = "changeColorToGreenAction";
        String blueToken = "changeColorToBlueAction";

        InputMap inputMap = targetPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = targetPanel.getActionMap();

        inputMap.put(ctrlGKey, greenToken);
        inputMap.put(ctrlBKey, blueToken);

        actionMap.put(greenToken, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                targetPanel.setBackground(new ColorUIResource(0, 150, 0));
                infoLabel.setText("Press CTRL + B to turn blue");
                infoLabel.setForeground(Color.WHITE);
            }
        });

        actionMap.put(blueToken, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                targetPanel.setBackground(new ColorUIResource(0, 120, 170));
                infoLabel.setText("Press CTRL + G to turn green");
                infoLabel.setForeground(Color.WHITE);
            }
        });
    }
}
```

## ⚖️ License
This project is licensed under the **MIT License**.

Copyright (c) 2026 Yurii Pavlenko

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files...

License: [MIT](LICENSE)
