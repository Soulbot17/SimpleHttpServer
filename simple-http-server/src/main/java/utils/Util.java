package utils;

import main.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Util {

    static void info(String format, Object... args) {
        System.err.printf(format, args);
    }

    static void debug(String format, Object... args) {
        System.err.printf(format, args);
    }

    static void error(String format, Object... args) {
        System.err.printf(format, args);
    }

}
