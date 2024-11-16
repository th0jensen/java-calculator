package com.thojensen.snake;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Graphics {
    private static Shell shell;
    private Game game;
    private static final int CELL_SIZE = 20;
    private static int fps = 120;
    private Color snakeColor;
    private Color foodColor;
    private Color backgroundColor;
    private int score;
    private Font scoreFont;
    private Button tryAgainButton;
    private boolean isGameOver = false;

    public Graphics(Shell shell) {
        Graphics.shell = shell;
        this.score = 0;
        initializeGame();
        initializeColors();
    }

    private void initializeGame() {
        Cell initPos = new Cell(5, 5);
        Snake initSnake = new Snake(initPos);
        Board board = new Board(19, 20);
        game = new Game(initSnake, board);
        game.setDirection(Game.DIRECTION_NONE);
        game.setGameOver(false);
        board.generateFood();
    }

    private void initializeColors() {
        Display display = shell.getDisplay();
        snakeColor = new Color(display, 0, 255, 0);
        foodColor = new Color(display, 255, 0, 0);
        scoreFont = new Font(display, "system", 14, SWT.NORMAL);
    }

    public void start() {
        shell.addPaintListener(e -> {
            Rectangle clientArea = shell.getClientArea();
            e.gc.fillRectangle(clientArea);

            // Draw snake and food as before
            drawGameElements(e.gc);

            // Draw score
            e.gc.setFont(scoreFont);
            String scoreText = "Score: " + (game.getSnake().getSnakeParts().size() - 1);
            e.gc.drawString(scoreText, 10, 10, true);

            // Draw game over screen if game is over
            if (game.isGameOver()) {
                drawGameOverScreen(e.gc, clientArea);
            }
        });

        createTryAgainButton();
        
        // Game loop remains the same
        Runnable gameLoop = new Runnable() {
            @Override
            public void run() {
                if (!shell.isDisposed()) {
                    game.update();
                    if (game.isGameOver() && !isGameOver) {
                        isGameOver = true;
                        tryAgainButton.setVisible(true);
                    }
                    shell.redraw();
                    shell.getDisplay().timerExec(fps, this);
                }
            }
        };
        shell.getDisplay().timerExec(fps, gameLoop);
    }

    private void drawGameElements(GC gc) {
        // Draw snake
        gc.setBackground(snakeColor);
        for (Cell cell : game.getSnake().getSnakeParts()) {
            gc.fillRectangle(
                cell.getCol() * CELL_SIZE,
                cell.getRow() * CELL_SIZE,
                CELL_SIZE - 1,
                CELL_SIZE - 1
            );
        }

        // Draw food
        gc.setBackground(foodColor);
        Cell[][] cells = game.getBoard().getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].getCellType() == CellType.FOOD) {
                    gc.fillRectangle(
                        j * CELL_SIZE,
                        i * CELL_SIZE,
                        CELL_SIZE - 1,
                        CELL_SIZE - 1
                    );
                }
            }
        }
    }

    private void drawGameOverScreen(GC gc, Rectangle clientArea) {
        // Semi-transparent overlay
        gc.setAlpha(128);
        gc.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_BLACK));
        gc.fillRectangle(clientArea);
        gc.setAlpha(255);

        // Game Over text
        gc.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        gc.setFont(new Font(shell.getDisplay(), "system", 24, SWT.NORMAL));
        String gameOverText = "Game Over!";
        Point gameOverSize = gc.textExtent(gameOverText);
        gc.drawText(gameOverText, 
            (clientArea.width - gameOverSize.x) / 2,
            (clientArea.height - gameOverSize.y) / 2 - 30,
            true);

        // Final score
        String finalScore = "Final Score: " + (game.getSnake().getSnakeParts().size() - 1);
        Point scoreSize = gc.textExtent(finalScore);
        gc.drawText(finalScore,
            (clientArea.width - scoreSize.x) / 2,
            (clientArea.height - scoreSize.y) / 2 + 10,
            true);
    }

    private void createTryAgainButton() {
        tryAgainButton = new Button(shell, SWT.PUSH);
        tryAgainButton.setText("Try Again");
        tryAgainButton.setVisible(false);
        
        Rectangle clientArea = shell.getClientArea();
        tryAgainButton.setBounds(
            (clientArea.width - 100) / 2,
            (clientArea.height - 30) / 2 + 50,
            100,
            30
        );

        tryAgainButton.addListener(SWT.Selection, e -> {
            isGameOver = false;
            tryAgainButton.setVisible(false);
            initializeGame();
        });
    }

    public Game getGame() {
        return game;
    }

    public void dispose() {
        snakeColor.dispose();
        foodColor.dispose();
        backgroundColor.dispose();
        scoreFont.dispose();
        tryAgainButton.dispose();
    }
}
