package com.thojensen.snake;

import java.util.LinkedList;

public class Snake {
    private final LinkedList<Cell> snakeParts = new LinkedList<>();
    private Cell head;

    public Snake (Cell initPos) {
        head = initPos;
        snakeParts.add(head);
        head.setCellType(Cell.CellType.SNAKE_NODE);
    }

    public void grow() {
        Cell newCell = new Cell(head.getRow(), head.getCol());
        newCell.setCellType(Cell.CellType.SNAKE_NODE);
        snakeParts.addFirst(newCell);
    }

    public void move(Cell nextCell) {
        Cell tail = snakeParts.removeLast();
        tail.setCellType(Cell.CellType.EMPTY);

        head = nextCell;
        head.setCellType(Cell.CellType.SNAKE_NODE);
        snakeParts.addFirst(head);
    }

    public boolean checkCollision(Cell nextCell) {
        for (Cell cell : snakeParts) {
            if (cell.getRow() == nextCell.getRow() && 
                cell.getCol() == nextCell.getCol()) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<Cell> getSnakeParts() {
        return snakeParts;
    }
    public Cell getHead() { return head; }
}
