package se.liu.wilmi895.calendar;

import java.util.ArrayList;
import java.util.List;

public class Cal
{
    private List<Appointment> appointments;

    public Cal() {
	this.appointments = new ArrayList<>();
    }

    public static void main(String[] args) {
	final Cal calendar = new Cal();
	calendar.book(2001, "march", 5, 12, 30, 14, 30, "Min egna födsel");
	calendar.book(1999, "june", 15, 1, 30, 6, 0, "Tidig morgon...");
	calendar.book(1999, "june", 14, 1, 30, 6, 0, "No subject");
	calendar.book(1999, "june", 14, 1, 29, 6, 0, "No subject");
	calendar.book(1999, "june", 14, 1, 29, 5, 0, "No subject");
	calendar.book(2022, "january", 21, 21, 0, 23, 15, "Evening changes...");
	calendar.show();
    }

    public void show() {
	appointments.sort((final Appointment o1, final Appointment o2) -> {
	    final int dateCompare = o1.getDate().compareTo(o2.getDate());

	    if (dateCompare == 0) {
		return o1.getTimeSpan().compareTo(o2.getTimeSpan());
	    }

	    return dateCompare;
	});

	for (Appointment app : appointments) {
	    System.out.println(app);
	}
    }

    public void book(int year, String month, int day, int startHour, int startMinute, int endHour, int endMinute,
		     String subject)
    {
	final SimpleDate date = new SimpleDate(year, new Month(month), day);
	final TimeSpan timeSpan =
		new TimeSpan(new TimePoint(startHour, startMinute), new TimePoint(endHour, endMinute));
	appointments.add(new Appointment(subject, date, timeSpan));
    }
}
