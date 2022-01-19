package se.liu.wilmi895.lab1;

import javax.swing.JOptionPane;

public class Exercise8
{
    public static void main(String[] args) {
	while (true) {
	    if (askUser("Quit?") && askUser("Really?")) {
		return;
	    }
	    System.out.println("Continue running...");
	}
    }

    public static boolean askUser(String question) {
	return JOptionPane.showConfirmDialog(null, question, "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
