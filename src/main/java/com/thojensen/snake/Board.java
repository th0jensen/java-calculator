package com.thojensen.snake;

public class Board {
    final int ROW_COUNT, COL_COUNT;
    private final Cell[][] cells;

    public Board(int rowCount, int colCount) {
        ROW_COUNT = rowCount;
        COL_COUNT = colCount;

        cells = new Cell[ROW_COUNT][COL_COUNT];
        for (int r = 0; r < ROW_COUNT; ++r) {
            for (int c = 0; c < COL_COUNT; ++c) {
                cells[r][c] = new Cell(r, c);
            }
        }
    }

    public Cell[][] getCells() { return cells; }

    public void generateFood() {
        int row, col;
        do {
            row = (int) (Math.random() * ROW_COUNT);
            col = (int) (Math.random() * COL_COUNT);
        } while (cells[row][col].getCellType() == Cell.CellType.SNAKE_NODE);

        cells[row][col].setCellType(Cell.CellType.FOOD);
    }
}
