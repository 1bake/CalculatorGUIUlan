package com.company;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Calculator {
    double counter = 0;
    double secondary = 0;
    char lastOperation = '0';
    private JPanel mainPanel;
    private JPanel displayPanel;
    private JPanel buttonsPanel;
    private JLabel display;
    private JButton buttonC;
    private JButton buttonRevers;
    private JButton buttonMod;
    private JButton buttonDivide;
    private JButton button7;
    private JButton button8;
    private JButton button4;
    private JButton button1;
    private JButton button9;
    private JButton buttonMultiply;
    private JButton button5;
    private JButton button6;
    private JButton buttonMinus;
    private JButton buttonEvaluate;
    private JButton buttonPoint;
    private JButton button0;
    private JButton button2;
    private JButton button3;
    private JButton buttonPlus;
    private final ArrayList<JButton> numberButtons = new ArrayList<>();
    private final ArrayList<JButton> operationButtons = new ArrayList<>();

    public double eval()
    {
        if (lastOperation == '+') return counter + secondary;
        if (lastOperation == '-') return counter - secondary;
        if (lastOperation == 'x') return counter * secondary;
        if (lastOperation == '/') return counter / secondary;
        if (lastOperation == '%') return counter % secondary;
        return 0;
    }

    public String getValidated(String number)
    {
        int points = 0;
        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(0)) && number.length() > 1){
                return number.substring(1);
            }

            int pos = -1;
            if (number.charAt(i) == '.'){
                points++;
                pos = i;
            }
            if (points > 1){
                String left = number.substring(0, pos);
                String right = number.substring(pos+1);
//                System.out.println("LEFT: " + left);
//                System.out.println("RIGHT: " + right);
                right.replaceAll(".", "");
                number = left + right;
//                System.out.println("RESULT: " + number);
            }
        }
        if (number.length() > 2 && number.charAt(1) == '.')return number;
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) != '0' && number.length() > i+1 && number.charAt(i) != '.') {
                if (number.charAt(i+1) != '.') {
                    number = number.substring(i);
                    break;
                }
            }
        }
        return number;
    }

    public Calculator() {
        ArrayList<JButton> numberButtons = new ArrayList<>();
        numberButtons.add(button0);
        numberButtons.add(button1);
        numberButtons.add(button2);
        numberButtons.add(button3);
        numberButtons.add(button4);
        numberButtons.add(button5);
        numberButtons.add(button6);
        numberButtons.add(button7);
        numberButtons.add(button8);
        numberButtons.add(button9);
        numberButtons.add(buttonPoint);

        operationButtons.add(buttonDivide);
        operationButtons.add(buttonMultiply);
        operationButtons.add(buttonMinus);
        operationButtons.add(buttonPlus);
        operationButtons.add(buttonMod);
        buttonC.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                counter = 0;
                display.setText("0");
                lastOperation = '0';
            }
        });
        buttonRevers.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText("-" + display.getText());
            }
        });

        for (JButton button : operationButtons) {
            button.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    lastOperation = button.getLabel().charAt(0);
                    double backup = counter;
                    try {
                        counter = Double.parseDouble(display.getText());
                    }
                    catch (Exception Ex){
                        counter = backup;
                    }
                    display.setText(String.valueOf(lastOperation));
                }
            });
        }
        for (JButton button : numberButtons) {
            button.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    String text = display.getText();
                    String number = button.getLabel();
                    display.setText(getValidated(text + number));

                }
            });
        }
        buttonEvaluate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double backup = secondary;
                try {
                    secondary = Double.parseDouble(display.getText());
                }
                catch (Exception Ex){
                    secondary = backup;
                }
                counter = eval();
                lastOperation = '0';
                display.setText(String.valueOf(counter));
            }
        });
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Calculator");
        frame.setContentPane(new Calculator().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
