package se.liu.wilmi895.lab1;

import javax.swing.*;

public class Exercise7
{
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog("Choose for or while loop");

        final int min = 10;
        final int max = 20;

        switch(input) {
            case "for" :
                System.out.println(sumFor(min, max));
                break;
            case "while":
                System.out.println(sumWhile(min, max));
                break;
            default:
                System.out.println("Please choose for or while loop");
        }
    }

    public static int sumFor(int min, int max) {
        int sum = 0;

        for (int i = min; i <= max; i++) {
            sum += i;
        }

        return sum;
    }

    public static int sumWhile(int min, int max) {
        int sum = 0;

        while(min <= max) {
            sum += min;
            min++;
        }

        return sum;
    }
}
