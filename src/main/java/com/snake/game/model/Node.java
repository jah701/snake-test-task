package com.snake.game.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Custom Node class represents a gaming field cell
 */
@Data
@Builder
@AllArgsConstructor
public class Node {
    private final int x;
    private final int y;
    private Item item;

    /**
     * Item tells us if the cell is empty (O), food (T) or snake is on it (S)
     * We may need it to recognize either if we can spawn food on a field or not,
     * as well as to see where the snake is (may be used to attach model)
     */
    public enum Item {
        O,
        S,
        T
    }
}
