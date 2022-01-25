package se.liu.wilmi895.calendar;

import java.util.Map;

public class Month
{
    public final static Map<String, Integer> MONTH_NAME_TO_LENGTH =
	    Map.ofEntries(Map.entry("january", 31), Map.entry("february", 28), Map.entry("march", 31),
			  Map.entry("april", 30), Map.entry("may", 31), Map.entry("june", 30), Map.entry("july", 31),
			  Map.entry("august", 31), Map.entry("september", 30), Map.entry("october", 31),
			  Map.entry("november", 30), Map.entry("december", 31));

    public final static Map<String, Integer> MONTH_NAME_TO_NUMBER =
	    Map.ofEntries(Map.entry("january", 1), Map.entry("february", 2), Map.entry("march", 3),
			  Map.entry("april", 4), Map.entry("may", 5), Map.entry("june", 6), Map.entry("july", 7),
			  Map.entry("august", 8), Map.entry("september", 9), Map.entry("october", 10),
			  Map.entry("november", 11), Map.entry("december", 12));

    private String name;
    private int number;
    private int days;

    public Month(final String name) {
	this.number = getMonthNumber(name);

	if (this.number == 0) {
	    throw new IllegalArgumentException(String.format("Month %s does not exist", name));
	}

	this.name = name;
	this.days = getMonthDays(name);

    }

    public String getName() {
	return name;
    }

    public int getNumber() {
	return number;
    }

    public int getDays() {
	return days;
    }

    public static int getMonthDays(String name) {
	return Month.MONTH_NAME_TO_LENGTH.getOrDefault(name, 0);
    }

    public static int getMonthNumber(String name) {
	return Month.MONTH_NAME_TO_NUMBER.getOrDefault(name, 0);
    }
}




