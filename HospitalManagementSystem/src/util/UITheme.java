package util;

import java.awt.Color;
import java.awt.Font;

/**
 * Central place to keep UI colors and fonts.
 * All panels can reuse this so the app looks consistent.
 */
public class UITheme {

    public static final Color HEADER_BG = new Color(30, 30, 30);
    public static final Color HEADER_FG = Color.WHITE;

    public static final Color FORM_BG   = new Color(245, 245, 245);
    public static final Color PANEL_BG  = Color.WHITE;

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font TEXT_FONT  = new Font("Segoe UI", Font.PLAIN, 12);

    public static Font font(int size, boolean bold) {
        return new Font("Segoe UI", bold ? Font.BOLD : Font.PLAIN, size);
    }
}
