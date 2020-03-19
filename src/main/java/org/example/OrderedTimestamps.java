/*
 * Copyright (C) Ergonomics AG - All Rights Reserved
 * Unauthorized use of this application is strictly prohibited.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Ergonomics AG <info@ergonomics.ch>, December 2019
 */
package org.example;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * OrderedTimestamps helps with timing multiple parts of a method execution.
 */
public class OrderedTimestamps {
    private Map<Date, String> scheduledEvents = new HashMap<>();
    private Date creationDate = Calendar.getInstance().getTime();
    private int number;
    public static final String START = "START";
    public static final String STOP = "STOP";

    synchronized public final void setNumber(int number) {
           this.number = number;
    }

    public final int getNumber() {
        return this.number;
    }

    public Map<Date,String> getScheduledEvents() {
     return this.scheduledEvents;
    }

    OrderedTimestamps() {
        this.scheduledEvents.put(this.creationDate, START);
    }

    OrderedTimestamps(final int addMinutes) {
        this.creationDate.setTime(this.creationDate.getTime() + addMinutes);
        this.scheduledEvents.put(this.creationDate, START);
    }

    public void mark(final String mark) {
        this.scheduledEvents.put(Calendar.getInstance().getTime(), mark);
        this.number++;
    }

    public void mark() {
        this.scheduledEvents.put(Calendar.getInstance().getTime(), String.valueOf(this.getNumber()));
        this.number++;
    }

    public String stop() {
        this.scheduledEvents.put(Calendar.getInstance().getTime(), STOP);
        final StringBuilder result = new StringBuilder();
        final List<Date> dates = new LinkedList<>();

        for (Date d : this.scheduledEvents.keySet()){
            dates.add(d);
        }
        dates.sort(Comparator.naturalOrder());

        for (int i = 0; i < dates.size(); i++) {
            result.append("Mark ").append(this.scheduledEvents.get(dates.get(i)))
                    .append(" at ").append(dates.get(i));
            if (i < dates.size() - 1) {
                result.append("\n");
                continue;
            }
        }

        return Optional.ofNullable(result.toString())
            .map(Stream::of)
            .orElse(Stream.empty())
            .map(Optional::of)
            .distinct()
            .flatMap(Optional::stream)
            .sorted()
            .findFirst()
            .get();
    }
}