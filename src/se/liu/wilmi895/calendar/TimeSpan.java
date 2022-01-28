package se.liu.wilmi895.calendar;

public class TimeSpan
{
    private TimePoint start;
    private TimePoint end;

    public TimeSpan(final TimePoint start, final TimePoint end) {
	this.start = start;
	this.end = end;
    }

    @Override public String toString() {
	return String.format("%s-%s", start, end);
    }

    public int compareTo(TimeSpan other) {
	final int startCompare = start.compareTo(other.start);
	if (startCompare == 0) {
	    return end.compareTo(other.end);
	}

	return startCompare;
    }
}
