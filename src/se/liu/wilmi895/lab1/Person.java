package se.liu.wilmi895.lab1;

import java.time.LocalDate;
import java.time.Period;

public class Person
{
    private String namn;
    private LocalDate birthDay;

    Person(String namnet, LocalDate bDay) {
        namn = namnet;
        birthDay = bDay;
    }

    public static void main(String[] args) {
        Person person = new Person("William", LocalDate.of(2001,5,3));
        System.out.println(person.getAge());

        Person person1 = new Person("Ferdinand", LocalDate.of(2002, 7, 4));
        Person person2 = new Person("Olle", LocalDate.of(2002, 8, 7));

        System.out.println(person1);
        System.out.println(person2);
    }

    public int getAge() {
        return Period.between(birthDay, LocalDate.now()).getYears();
    }

    public String toString() {
        return namn + " " + getAge();
    }
}
