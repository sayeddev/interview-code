package org.example;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingBetterParser extends ParserImpl {

    private static final Logger LOGGER = Logger.getLogger(LoggingBetterParser.class.getName());

    /**
     * This is better because it does not throw exceptions.
     * @return String result
     */
    public String getContentWithoutUnicode() {
        String result;
        try {
            result = super.getContentWithoutUnicode();
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            result = "no result";
        }
        return result;
    }

    protected static void logThis(final Class clazz, final byte[] message) {
        try {
            final Constructor constructor = clazz.getConstructor(byte[].class, Charset.class);
            final Object tmp = clazz.cast(constructor.newInstance(message, StandardCharsets.ISO_8859_1));
            LOGGER.log(Level.SEVERE, tmp.toString());
        } catch (final NoSuchMethodException | IllegalAccessException
            | InstantiationException | InvocationTargetException exception) {
                logThis(Exception.class, exception.getMessage().getBytes());
        }
    }

    public static boolean isInfo() {
        return true;
    }

    public static boolean isDebug() {
        return true;
    }

    public static final void info(final byte[] message) {
        logThis(String.class, message);
    }

    public static final void debug(final byte[] message) {
        logThis(String.class, message);
    }
}
