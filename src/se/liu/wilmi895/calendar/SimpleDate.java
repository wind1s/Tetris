package se.liu.wilmi895.calendar;

public class SimpleDate
{
    private int year;
    private Month month;
    private int day;

    public SimpleDate(final int year, final Month month, final int day) {
	if (year <= 1970) {
	    throw new IllegalArgumentException(String.format("Year %s has to be later than 1970", year));
	} else if (day < 0 || day > month.getDays()) {
	    throw new IllegalArgumentException(String.format("Month %s has less than %d days", month.getName(), day));
	}

	this.year = year;
	this.month = month;
	this.day = day;
    }

    @Override public String toString() {
	return String.format("%02d-%02d-%02d", year, month.getNumber(), day);
    }

    public int compareTo(SimpleDate other) {
	final int yearDiff = year - other.year;
	final int monthDaysDiff = month.getDays() - other.month.getDays();
	final int dayDiff = day - other.day;

	if (yearDiff == 0) {
	    if(monthDaysDiff == 0) return dayDiff;

	    return monthDaysDiff;
	}

	return yearDiff;
    }

    public int getYear() {
	return year;
    }

    public Month getMonth() {
	return month;
    }

    public int getDay() {
	return day;
    }
}
