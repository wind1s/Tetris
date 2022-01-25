package se.liu.wilmi895.lab1;

import javax.swing.JOptionPane;

public class Exercise4
{
    public static void main(String[] args) {
	int tabell = 0;

	while (true) {
	    String input = JOptionPane.showInputDialog("Please input a value");

	    try {
		if (input == null) {
		    return;
		}

		tabell = Integer.parseInt(input);
		break;

	    } catch (NumberFormatException ignored) {
		System.err.println("Please input an integer");
	    }
	}


	for (int i = 1; i <= 12; i++) {
	    System.out.println(i + " * " + tabell + " = " + i * tabell);
	}
    }
}
