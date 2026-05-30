## Key Bindings.

### In graphical applications, you often need to handle keyboard input:

- Ctrl + S → save
- Ctrl + B → action
- Esc → exit
- arrow keys → navigation

Swing has several ways to handle keys.

---

### **❌ KeyListener (old method)**

At first glance, it seems logical:

```java
component.addKeyListener(...)
```

- **⚠️ PROBLEM**

**🔴 1. Focus required**

The component must be active.

👉 If focus is on a button or text field, the processing breaks.

---

**🔴 2. Doesn't scale well**

If your app has a lot of buttons and panels:

- difficult to navigate
- easy to get confused
- code becomes messy

---

**🔴 3. Doesn't work as a "UI system"**

KeyListener works "at the keyboard level," not the interface.

---

**🧠 Problem Summary**

👉 We need a method that:

- is focus-independent
- works globally within a window
- integrates with UI components

**✅ SOLUTION — Key Bindings**

Swing offers a modern mechanism:

### 👉 **Key Bindings (InputMap + ActionMap)**

**🧩 Basic Idea**

Key Bindings split the process into 3 parts:

---

**🔹 1. KeyStroke**

👉 “which key is pressed”

```java
KeyStroke.getKeyStroke("ctrl B")
```

---

**🔹 2. InputMap**
👉 `InputMap` is a **mapping table (recognizes key)**

👉 Binds a key to an action name

```java
inputMap.put(keyStroke,"changeColor");
```

---

**🔹 3. ActionMap**

👉 An `ActionMap` is a **table of actions** that can be triggered by name.

👉 Describes what to do (finds an action)

```java
actionMap.put("changeColor",action);
```

---

**🧠 How it works**

When the user presses Ctrl + B:

1. Swing receives a keyboard event
2. Looks up KeyStroke in InputMap
3. Finds the action name `"changeColor"`
4. Goes to ActionMap
5. Executes the Action

---

**4. 📌 FULL EXAMPLE**

```java
JPanel panel = new JPanel();

KeyStroke keyStroke = KeyStroke.getKeyStroke("ctrl B");

//Get the set of keyboard bindings (InputMap) from the panel
InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
inputMap.put(keyStroke, "changeColor");

//Get access to the panel's "action table"
ActionMap actionMap = panel.getActionMap();

actionMap.put("changeColor", new AbstractAction() {
    @Override
    public void actionPerformed(ActionEvent e) {
        panel.setBackground(Color.RED);
    }
});
```

---

**🧠 What's happening here**

- Ctrl + B → starts the action
- the panel changes color to red

---

**5. 📌 InputMap LEVELS**

Swing allows you to select the "keymap" range.

---

**🔹 WHEN_FOCUSED**

```java
component.getInputMap(JComponent.WHEN_FOCUSED)
```

👉 Works only if the component is focused

---

**🔹 WHEN_ANCESTOR_OF_FOCUSED_COMPONENT**

👉 Works if any child element is focused

---

**🔥 WHEN_IN_FOCUSED_WINDOW (most used)**

👉 Works if the window is active

(like hotkeys in applications)

---

**6. 🧩 ADDITIONAL FEATURES**

---

**🔹 Multiple Keys**

```java
KeyStroke.getKeyStroke("ctrl shift S");
```

---

**🔹 Arrow Keys**

```java
KeyStroke.getKeyStroke("UP");
```

---

**🔹 Escape**

```java
KeyStroke.getKeyStroke("ESCAPE");
```

---

**🔹 Space**

```java
KeyStroke.getKeyStroke("SPACE");
```

- **Space Key Specialty**

If `KeyStroke.getKeyStroke("SPACE")` doesn't work, it's usually not a Key Bindings issue, but a focus issue.

---

❗ Cause

`JPanel` by default:

- not focusable
- does not receive keyboard events correctly

---

✔ Solution

```java
panel.setFocusable(true);
panel.requestFocusInWindow();
```

💡 Important nuance

SPACE is often:

- intercepted by buttons (JButton)
- considered the "default UI action"

👉 therefore, it is sometimes better to test other keys (A, L, Ctrl+...).

### Function bindKey()

```java
static void bindKey(JComponent component, String key, String actionName, Action action) {
    
    KeyStroke keyStroke = KeyStroke.getKeyStroke(key);
    
    InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    inputMap.put(keyStroke, actionName);
    
    ActionMap actionMap = component.getActionMap();
    actionMap.put(actionName, action);
}
```

---

**7. 📌 OTHER KEY PROCESSING METHODS**

---

**❌ KeyListener**

- simple, but outdated
- focus-dependent

---

**⚠️ DocumentListener**

👉 used in JTextField

(reacting to text input)

---

**⚠️ ActionListener**

👉 for buttons, menus

---

**✅ Key Bindings ⭐**

- flexible
- scalable
- UI-oriented

---

**8. 🧠 COMPARISON**

| method | where used | quality |
| --- | --- | --- |
| KeyListener | study | ❌ bad |
| ActionListener | buttons | ✔ |
| Key Bindings | UI hotkeys | ⭐ best |

---

**9. 🚀 CONCLUSION**

Key Bindings are:

👉 A modern way to handle keyboard interactions in Swing

👉 A system of interface-level hotkeys

👉 More flexible and reliable than KeyListener

---

**💡 A simple formula to remember**

```
KeyStroke → InputMap → ActionMap → Action
```

---
