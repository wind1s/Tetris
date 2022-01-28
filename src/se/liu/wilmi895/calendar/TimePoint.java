package se.liu.wilmi895.calendar;

public class TimePoint
{
    private int hour;
    private int minute;

    public TimePoint(final int hour, final int minute) {
	if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
	    throw new IllegalArgumentException("Time point is out of bounds");
	}

	this.hour = hour;
	this.minute = minute;
    }

    @Override public String toString() {
	return String.format("%02d:%02d", hour, minute);
    }

    public int compareTo(TimePoint other) {
	final int hourDiff = hour - other.hour;
	final int minuteDiff = minute - other.minute;

	if (hourDiff == 0) {
	    return minuteDiff;
	}

	return hourDiff;
    }

    public int getHour() {
	return hour;
    }

    public int getMinute() {
	return minute;
    }
}
