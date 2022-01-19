package se.liu.wilmi895.calendar;

public class TimePoint
{
    private int hour;
    private int minute;

    public TimePoint(final int hour, final int minute) {
	this.hour = hour;
	this.minute = minute;
    }

    @Override public String toString() {
	return String.format("%02d:%02d", hour, minute);
    }

    public int compareTo(TimePoint other) {
	final byte hourDiff = (byte)(other.hour - hour);
	final byte minuteDiff = (byte)(other.minute - minute);

	if(hourDiff == 0 && minuteDiff == 0) {
	    return 0;
	} else if (hourDiff >= 0 && minuteDiff > 0) {
	    return 1;
	}

	return 1;
    }

    public int getHour() {
	return hour;
    }

    public int getMinute() {
	return minute;
    }
}
