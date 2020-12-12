package com.snake.game;

import com.snake.game.model.Field;
import com.snake.game.util.PropertiesGetter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static int init_value;

    private static final String DOWN = "s";
    private static final String UP = "w";
    private static final String LEFT = "a";
    private static final String RIGHT = "d";
    private static final String EXIT_GAME = "0";
    private static final String INIT_VALUE_PROP_PATH = "init.value";
    private static final PropertiesGetter propertiesGetter = new PropertiesGetter();

    static {
        try {
            init_value = Integer.parseInt(propertiesGetter.getPropValues(INIT_VALUE_PROP_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Field field = new Field(init_value);
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Please enter: \n'a' to move left\n'd' to move right"
                + "\n's' to move down\nor 'w' to move up");

        label:
        while (true) {
            input = scanner.nextLine();
            switch (input) {
                default:
                    break;
                case RIGHT:
                    field.move(1);
                    break;
                case DOWN:
                    field.move(2);
                    break;
                case LEFT:
                    field.move(3);
                    break;
                case UP:
                    field.move(4);
                    break;
                case EXIT_GAME:
                    break label;
            }
            System.out.println(field.printField());
        }
    }
}
