package com.thojensen.snake;

public class Cell {
    private final int row, col;
    private CellType cellType;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public CellType getCellType() { return cellType; }
    public void setCellType(CellType nuevo) {
        this.cellType = nuevo;
    }

    public enum CellType {
        EMPTY,
        FOOD,
        SNAKE_NODE
    }
}
