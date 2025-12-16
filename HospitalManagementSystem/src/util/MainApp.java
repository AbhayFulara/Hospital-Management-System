package util;

import ui.MainFrame;
import javax.swing.*;
import java.awt.*;

/**
 * Application entry point.
 * Sets Look & Feel + global fonts and opens MainFrame.
 */
public class MainApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());

                Font uiFont = new Font("Segoe UI", Font.PLAIN, 13);
                UIManager.put("Label.font", uiFont);
                UIManager.put("Button.font", uiFont);
                UIManager.put("Table.font", uiFont);
                UIManager.put("TextField.font", uiFont);
                UIManager.put("TextArea.font", uiFont);
                UIManager.put("ComboBox.font", uiFont);
            } catch (Exception ignored) {
            }

            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
