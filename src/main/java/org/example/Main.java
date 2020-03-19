/*
 * Copyright (C) Ergonomics AG - All Rights Reserved
 * Unauthorized use of this application is strictly prohibited.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Ergonomics AG <info@ergonomics.ch>, December 2019
 */
package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the entry point of the application.
 * @since 1.0
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * Here we go...
     * @param args The command line arguments.
     */
    @SuppressWarnings("ProhibitPublicStaticMethods")
    public static void main(final String[] args) {
        final OrderedTimestamps ots = new OrderedTimestamps();

        if (LoggingBetterParser.isDebug()) {
            LoggingBetterParser.info("Hi, welcome to the challenge....".getBytes(StandardCharsets.UTF_8));
        }
        ots.mark("after welcome");
        final ParserImpl first = new LoggingBetterParser();
        first.setFile(new File("LICENSE.txt"));
        final Parser second = first;
        String data = "";
        try {
            ots.mark();
            data = second.getContentWithoutUnicode();
        } catch (final IOException exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage());
        }
        ots.mark("before result");
        final String formattedData;
        if (LoggingBetterParser.isDebug()) {
            formattedData = String.format("Read %d characters.", data.length());
            LoggingBetterParser.info(formattedData.getBytes());
        }
        String stopMessage = ots.stop();
        LOGGER.log(Level.SEVERE, stopMessage);
    }
}