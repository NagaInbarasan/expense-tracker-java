import database.DatabaseManager;
import ui.MainFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();
        setupDarkTheme();

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.err.println("Uncaught exception in " + t.getName() + ":");
            e.printStackTrace();
        });

        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Initializing MainFrame...");
                MainFrame mainFrame = new MainFrame();
                System.out.println("Setting MainFrame visible...");
                mainFrame.setVisible(true);
                System.out.println("MainFrame visible: " + mainFrame.isVisible());
            } catch (Throwable t) {
                System.err.println("EXCEPTION IN EDT:");
                t.printStackTrace();
            }
        });
    }

    private static void setupDarkTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        Color bg = new Color(30, 30, 30);
        Color fg = new Color(240, 240, 240);
        Color accent = new Color(13, 110, 253); // Blue
        Color panelBg = new Color(45, 45, 45);

        UIManager.put("Panel.background", bg);
        UIManager.put("Label.foreground", fg);
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        
        UIManager.put("Button.background", accent);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
        UIManager.put("Button.border", BorderFactory.createEmptyBorder(8, 15, 8, 15));
        UIManager.put("Button.focus", new Color(0, 0, 0, 0));
        
        UIManager.put("TextField.background", panelBg);
        UIManager.put("TextField.foreground", fg);
        UIManager.put("TextField.caretForeground", fg);
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("TextField.border", BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        UIManager.put("TextArea.background", panelBg);
        UIManager.put("TextArea.foreground", fg);
        UIManager.put("TextArea.caretForeground", fg);
        UIManager.put("TextArea.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("TextArea.border", BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        UIManager.put("ComboBox.background", panelBg);
        UIManager.put("ComboBox.foreground", fg);
        UIManager.put("ComboBox.selectionBackground", accent);
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);
        UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 14));
        
        UIManager.put("Table.background", panelBg);
        UIManager.put("Table.foreground", fg);
        UIManager.put("Table.gridColor", new Color(60, 60, 60));
        UIManager.put("Table.selectionBackground", accent);
        UIManager.put("Table.selectionForeground", Color.WHITE);
        UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Table.rowHeight", 30);
        
        UIManager.put("TableHeader.background", new Color(20, 20, 20));
        UIManager.put("TableHeader.foreground", fg);
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 14));
        
        UIManager.put("ScrollPane.background", bg);
        UIManager.put("ScrollPane.border", BorderFactory.createEmptyBorder());
        UIManager.put("Viewport.background", bg);
        
        UIManager.put("OptionPane.background", bg);
        UIManager.put("OptionPane.messageForeground", fg);
    }
}
