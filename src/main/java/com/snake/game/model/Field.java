package com.snake.game.model;

import java.util.LinkedList;
import lombok.Data;

/**
 * Field class represents our playground. Is the matrix of custom Nodes (e.g. cells)
 */
@Data
public class Field {
    /**
     * Field matrix
     */
    private Node[][] field;

    /**
     * I've put snakes body here to improve readability
     */
    private LinkedList<Node> body;

    /**
     * The snake to be initialized
     */
    private Snake snake;

    /**
     * Marker to match if cookie is set
     */
    private boolean cookieSet = false;

    /**
     * Constructor with initial value of the matrix field.
     * Matrix will be initialized automatically as well as a snake.
     * Prints the playground so we could see snakes initial position
     */
    public Field(int initVal) {
        field = new Node[initVal][initVal];
        initField();
        snake = new Snake(this);
        body = snake.getBody();
        System.out.println(printField());
    }

    /**
     * Method to move the snake.
     * move... methods do just move the snake into a requested direction.
     * move...AndGrow methods increase the snakes length
     */
    public void move(int dir) {
        if (dir == 1) {
            if (checkNext(dir).getItem().equals(Node.Item.S) && !isMovingBackwards(dir)) {
                throw new RuntimeException("You have lost!");
            } else if (checkNext(dir).getItem().equals(Node.Item.O)) {
                moveRight();
            } else if (checkNext(dir).getItem().equals(Node.Item.T)) {
                moveRightAndGrow();
            }
        } else if (dir == 2) {
            if (checkNext(dir).getItem().equals(Node.Item.S) && !isMovingBackwards(dir)) {
                throw new RuntimeException("You have lost!");
            } else if (checkNext(dir).getItem().equals(Node.Item.O)) {
                moveDown();
            } else if (checkNext(dir).getItem().equals(Node.Item.T)) {
                moveDownAndGrow();
            }
        } else if (dir == 3) {
            if (checkNext(dir).getItem().equals(Node.Item.S) && !isMovingBackwards(dir)) {
                throw new RuntimeException("You have lost!");
            } else if (checkNext(dir).getItem().equals(Node.Item.O)) {
                moveLeft();
            } else if (checkNext(dir).getItem().equals(Node.Item.T)) {
                moveLeftAndGrow();
            }
        } else if (dir == 4) {
            if (checkNext(dir).getItem().equals(Node.Item.S) && !isMovingBackwards(dir)) {
                throw new RuntimeException("You have lost!");
            } else if (checkNext(dir).getItem().equals(Node.Item.O)) {
                moveUp();
            } else if (checkNext(dir).getItem().equals(Node.Item.T)) {
                moveUpAndGrow();
            }
        }
        checkAndGenerateCookie();
    }

    private void moveUp() {
        Node oldTail = body.pollLast();
        Node oldHead = body.peekFirst();
        try {
            field[oldHead.getX() - 1][oldHead.getY()].setItem(Node.Item.S);
            field[oldTail.getX()][oldTail.getY()].setItem(Node.Item.O);
            body.addFirst(Node.builder()
                    .x(oldHead.getX() - 1)
                    .y(oldHead.getY())
                    .build());
        } catch (ArrayIndexOutOfBoundsException e) {
            field[field.length - 1][oldHead.getY()].setItem(Node.Item.S);
            field[oldTail.getX()][oldTail.getY()].setItem(Node.Item.O);
            if (oldHead.getX() - 1 < 0) {
                oldHead = Node.builder().x(field.length).y(oldHead.getY()).build();
            }
            body.addFirst(Node.builder()
                    .x(oldHead.getX() - 1)
                    .y(oldHead.getY())
                    .build());
        }
    }

    private void moveUpAndGrow() {
        Node oldHead = body.peekFirst();
        try {
            field[oldHead.getX() - 1][oldHead.getY()].setItem(Node.Item.S);
            body.addFirst(Node.builder()
                    .x(oldHead.getX() - 1)
                    .y(oldHead.getY())
                    .build());
            cookieSet = false;
        } catch (ArrayIndexOutOfBoundsException e) {
            field[field.length - 1][oldHead.getY()].setItem(Node.Item.S);
            if (oldHead.getX() - 1 < 0) {
                oldHead = Node.builder().x(field.length).y(oldHead.getY()).build();
            }
            body.addFirst(Node.builder()
                    .x(oldHead.getX() - 1)
                    .y(oldHead.getY())
                    .build());
            cookieSet = false;
        }
    }

    private void moveLeft() {
        Node oldTail = body.pollLast();
        Node oldHead = body.peekFirst();
        try {
            field[oldTail.getX()][oldTail.getY()].setItem(Node.Item.O);
            field[oldHead.getX()][oldHead.getY() - 1].setItem(Node.Item.S);
            body.addFirst(Node.builder()
                    .x(oldHead.getX())
                    .y(oldHead.getY() - 1)
                    .build());
        } catch (ArrayIndexOutOfBoundsException e) {
            field[oldHead.getX()][field.length - 1].setItem(Node.Item.S);
            field[oldTail.getX()][oldTail.getY()].setItem(Node.Item.O);
            if (oldHead.getY() - 1 < 0) {
                oldHead = Node.builder().x(oldHead.getX()).y(field.length).build();
            }
            body.addFirst(Node.builder()
                    .x(oldHead.getX())
                    .y(oldHead.getY() - 1)
                    .build());
        }
    }

    private void moveLeftAndGrow() {
        Node oldHead = body.peekFirst();
        try {
            field[oldHead.getX()][oldHead.getY() - 1].setItem(Node.Item.S);
            body.addFirst(Node.builder()
                    .x(oldHead.getX())
                    .y(oldHead.getY() - 1)
                    .build());
            cookieSet = false;
        } catch (ArrayIndexOutOfBoundsException e) {
            field[oldHead.getX()][field.length - 1].setItem(Node.Item.S);
            if (oldHead.getY() - 1 < 0) {
                oldHead = Node.builder().x(oldHead.getX()).y(field.length).build();
            }
            body.addFirst(Node.builder()
                    .x(oldHead.getX())
                    .y(oldHead.getY() - 1)
                    .build());
            cookieSet = false;
        }
    }

    private void moveDown() {
        Node oldTail = body.pollLast();
        Node oldHead = body.peekFirst();
        try {
            field[oldTail.getX()][oldTail.getY()].setItem(Node.Item.O);
            field[oldHead.getX() + 1][oldHead.getY()].setItem(Node.Item.S);
            body.addFirst(Node.builder()
                    .x(oldHead.getX() + 1)
                    .y(oldHead.getY())
                    .build());
        } catch (ArrayIndexOutOfBoundsException e) {
            field[0][oldHead.getY()].setItem(Node.Item.S);
            field[oldTail.getX()][oldTail.getY()].setItem(Node.Item.O);
            if (oldHead.getX() + 1 > field.length - 1) {
                oldHead = Node.builder()
                        .x(0)
                        .y(oldHead.getY())
                        .build();
            }
            body.addFirst(Node.builder()
                    .x(oldHead.getX())
                    .y(oldHead.getY())
                    .build());
        }
    }

    private void moveDownAndGrow() {
        Node oldHead = body.peekFirst();
        try {
            field[oldHead.getX() + 1][oldHead.getY()].setItem(Node.Item.S);
            body.addFirst(Node.builder()
                    .x(oldHead.getX() + 1)
                    .y(oldHead.getY())
                    .build());
            cookieSet = false;
        } catch (ArrayIndexOutOfBoundsException e) {
            field[0][oldHead.getY()].setItem(Node.Item.S);
            if (oldHead.getX() + 1 > field.length - 1) {
                oldHead = Node.builder()
                        .x(0)
                        .y(oldHead.getY())
                        .build();
            }
            body.addFirst(Node.builder()
                    .x(oldHead.getX())
                    .y(oldHead.getY())
                    .build());
            cookieSet = false;
        }
    }

    private void moveRight() {
        Node oldTail = body.pollLast();
        Node oldHead = body.peekFirst();
        try {
            field[oldTail.getX()][oldTail.getY()].setItem(Node.Item.O);
            field[oldHead.getX()][oldHead.getY() + 1].setItem(Node.Item.S);
            body.addFirst(Node.builder()
                    .x(oldHead.getX())
                    .y(oldHead.getY() + 1)
                    .build());
        } catch (ArrayIndexOutOfBoundsException e) {
            field[oldHead.getX()][0].setItem(Node.Item.S);
            field[oldTail.getX()][oldTail.getY()].setItem(Node.Item.O);
            if (oldHead.getY() + 1 > field.length - 1) {
                oldHead = Node.builder()
                        .x(oldHead.getX())
                        .y(0)
                        .build();
            }
            body.addFirst(Node.builder()
                    .x(oldHead.getX())
                    .y(oldHead.getY())
                    .build());
        }
    }

    private void moveRightAndGrow() {
        Node oldHead = body.peekFirst();
        try {
            field[oldHead.getX()][oldHead.getY() + 1].setItem(Node.Item.S);
            body.addFirst(Node.builder()
                    .x(oldHead.getX())
                    .y(oldHead.getY() + 1)
                    .build());
            cookieSet = false;
        } catch (ArrayIndexOutOfBoundsException e) {
            field[oldHead.getX()][0].setItem(Node.Item.S);
            if (oldHead.getY() + 1 > field.length - 1) {
                oldHead = Node.builder()
                        .x(oldHead.getX())
                        .y(0)
                        .build();
            }
            body.addFirst(Node.builder()
                    .x(oldHead.getX())
                    .y(oldHead.getY())
                    .build());
            cookieSet = false;
        }
    }

    /**
     * Method to create cookies (food) for the snake
     */
    private void checkAndGenerateCookie() {
        if (cookieSet) {
            return;
        }
        int r1 = (int) Math.floor(Math.random() * field.length);
        int r2 = (int) Math.floor(Math.random() * field.length);
        if (field[r1][r2].getItem().equals(Node.Item.O)) {
            field[r1][r2].setItem(Node.Item.T);
            cookieSet = true;
        } else {
            checkAndGenerateCookie();
        }
    }

    /**
     * Method to prevent backwards moving
     * @param dir Direction
     * @return Returns false if snakes movement is correct.
     *         Throws RuntimeException if something went wrong
     */
    private boolean isMovingBackwards(int dir) {
        Node second = body.get(1);
        if (dir == 1) {
            return field[body.peekFirst().getX()][body.peekFirst().getY() + 1].getX() == second.getX()
                    && field[body.peekFirst().getX()][body.peekFirst().getY() + 1].getY() == second.getY();
        } else if (dir == 2) {
            return field[body.peekFirst().getX() + 1][body.peekFirst().getY()].getX() == second.getX()
                    && field[body.peekFirst().getX() + 1][body.peekFirst().getY()].getY() == second.getY();
        } else if (dir == 3) {
            return field[body.peekFirst().getX()][body.peekFirst().getY() - 1].getX() == second.getX()
                    && field[body.peekFirst().getX()][body.peekFirst().getY() - 1].getY() == second.getY();
        } else if (dir == 4) {
            return field[body.peekFirst().getX() - 1][body.peekFirst().getY()].getX() == second.getX()
                    && field[body.peekFirst().getX() - 1][body.peekFirst().getY()].getY() == second.getY();
        }
        throw new RuntimeException("Can't check if moving backwards");
    }

    /**
     * Method checks next field
     * @param dir Direction
     * @return Returns the next Node in requested direction
     *         Throws RuntimeException if something went wrong
     */
    private Node checkNext(int dir) {
        try {
            if (dir == 1) {
                return field[body.peekFirst().getX()][body.peekFirst().getY() + 1];
            } else if (dir == 2) {
                return field[body.peekFirst().getX() + 1][body.peekFirst().getY()];
            } else if (dir == 3) {
                return field[body.peekFirst().getX()][body.peekFirst().getY() - 1];
            } else if (dir == 4) {
                return field[body.peekFirst().getX() - 1][body.peekFirst().getY()];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            if (dir == 1) {
                return field[body.peekFirst().getX()][0];
            } else if (dir == 2) {
                return field[0][body.peekFirst().getY()];
            } else if (dir == 3) {
                return field[body.peekFirst().getX()][field.length - 1];
            } else if (dir == 4) {
                return field[field.length - 1][body.peekFirst().getY()];
            }
        }
        throw new RuntimeException("Can't check next field");
    }

    /**
     * Method to automatically initialize the playground
     */
    private void initField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = Node.builder().x(i).y(j).item(Node.Item.O).build();
            }
        }
    }

    /**
     * Prints out the playground so we could see the gaming process
     * @return Returns the String of the playground
     */
    public String printField() {
        StringBuilder result = new StringBuilder();
        for (Node[] arr : field) {
            result
                    .append(arr[0].getItem())
                    .append(" ")
                    .append(arr[1].getItem())
                    .append(" ")
                    .append(arr[2].getItem())
                    .append(" ")
                    .append(arr[3].getItem())
                    .append(" ")
                    .append(arr[4].getItem())
                    .append(" ")
                    .append(arr[5].getItem())
                    .append(" ")
                    .append(arr[6].getItem())
                    .append(" ")
                    .append(arr[7].getItem())
                    .append(" ")
                    .append(arr[8].getItem())
                    .append(" ")
                    .append(arr[9].getItem())
                    .append(" ")
                    .append("\n");
        }
        return result.toString();
    }
}
