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
	return String.format("%s - %s", start.toString(), end.toString());
    }

    public static void main(String[] args) {
	System.out.println(new TimeSpan(new TimePoint(12,5), new TimePoint(12,30)));
    }
}
