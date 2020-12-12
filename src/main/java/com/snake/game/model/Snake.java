package com.snake.game.model;

import java.util.LinkedList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The snake class will be used to move on the gaming field
 * To initialize just pass the field it should be placed on
 */
@Data
@NoArgsConstructor
public class Snake {
    private LinkedList<Node> body;
    private Field field;

    public Snake(Field field) {
        body = new LinkedList<>();
        this.field = field;
        init();
    }

    /**
     * Method initializes a snake
     */
    private void init() {
        int i = field.getField().length / 2;
        Node head = Node.builder().x(i).y(i + 1).build();
        Node mid = Node.builder().x(i).y(i).build();
        Node tail = Node.builder().x(i).y(i - 1).build();
        field.getField()[head.getX()][head.getY()].setItem(Node.Item.S);
        field.getField()[mid.getX()][mid.getY()].setItem(Node.Item.S);
        field.getField()[tail.getX()][tail.getY()].setItem(Node.Item.S);
        body.addFirst(head);
        body.addLast(mid);
        body.addLast(tail);
    }
}
