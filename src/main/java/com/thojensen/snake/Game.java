package com.thojensen.snake;

public class Game {

    public static final int DIRECTION_NONE
            = 0,
            DIRECTION_RIGHT = 1, DIRECTION_LEFT = -1,
            DIRECTION_UP = 2, DIRECTION_DOWN = -2;
    private Snake snake;
    private Board board;
    private int direction;
    private boolean gameOver;

    public Game(Snake snake, Board board) {
        this.snake = snake;
        this.board = board;
    }

    public Snake getSnake() { return snake; }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Board getBoard() { return board; }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean isGameOver() { return gameOver; }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getDirection() { return direction; }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void update() {
        if (!gameOver) {
            if (direction != DIRECTION_NONE) {
                Cell nextCell
                        = getNextCell(snake.getHead());

                if (snake.checkCollision(nextCell)) {
                    setDirection(DIRECTION_NONE);
                    gameOver = true;
                }
                else {
                    boolean wasFood = nextCell.getCellType() == CellType.FOOD;
                    
                    if (wasFood) {
                        snake.grow();
                    }
                    
                    snake.move(nextCell);
                    
                    if (wasFood) {
                        board.generateFood();
                    }
                }
            }
        }
    }

    private Cell getNextCell(Cell currentPosition) {
        int row = currentPosition.getRow();
        int col = currentPosition.getCol();

        if (direction == DIRECTION_RIGHT) {
            col++;
        }
        else if (direction == DIRECTION_LEFT) {
            col--;
        }
        else if (direction == DIRECTION_UP) {
            row--;
        }
        else if (direction == DIRECTION_DOWN) {
            row++;
        }

        if (row < 0) row = board.ROW_COUNT - 1;
        if (row >= board.ROW_COUNT) row = 0;
        if (col < 0) col = board.COL_COUNT - 1;
        if (col >= board.COL_COUNT) col = 0;

        return board.getCells()[row][col];
    }
}
