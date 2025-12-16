package util;

import java.util.logging.*;

public class LoggerUtil {

    private static boolean initialized = false;

    private static void init() {
        if (!initialized) {
            Logger root = Logger.getLogger("");

            for (Handler h : root.getHandlers()) {
                root.removeHandler(h);
            }

            ConsoleHandler handler = new ConsoleHandler();
            handler.setFormatter(new SimpleFormatter());
            handler.setLevel(Level.INFO);   // ✅ FIX

            root.addHandler(handler);
            root.setLevel(Level.INFO);      // ✅ FIX

            initialized = true;
        }
    }

    public static Logger getLogger(Class<?> clazz) {
        init();
        return Logger.getLogger(clazz.getName());
    }
}
