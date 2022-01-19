package se.liu.wilmi895.calendar;

public class SimpleDate
{
    private int year;
    private int month;
    private int day;

    public SimpleDate(final int year, final int month, final int day) {
	this.year = year;
	this.month = month;
	this.day = day;
    }

    @Override public String toString() {
	return year + "-" + month + "-" + day;
    }

    public int getYear() {
	return year;
    }

    public int getMonth() {
	return month;
    }

    public int getDay() {
	return day;
    }
}
