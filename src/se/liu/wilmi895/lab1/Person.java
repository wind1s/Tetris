package se.liu.wilmi895.lab1;

import java.time.LocalDate;

public class Person
{
    private String namn;
    private LocalDate birthDay;

    Person(String namnet, LocalDate bDay) {
        namn = namnet;
        birthDay = bDay;
    }
}
