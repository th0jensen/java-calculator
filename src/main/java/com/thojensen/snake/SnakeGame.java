package com.thojensen.snake;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

public class SnakeGame implements Runnable {
    private static Shell parentShell;
    private Shell shell;
    private Graphics graphics;

    public SnakeGame(Shell parentShell) {
        SnakeGame.parentShell = parentShell;
    }

    @Override
    public void run() {
        open();
    }

    public static void main(String[] args) {
        Display display = new Display();
        SnakeGame snake = new SnakeGame(parentShell);
        snake.run();
        while (!display.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    public void open() {
        shell = new Shell(parentShell, SWT.CLOSE | SWT.MODELESS);
        shell.setText("Snake");
        shell.setSize(400, 428);

        graphics = new Graphics(shell);
        graphics.start();

        addKeyboardListener();

        shell.open();
        while (!shell.isDisposed()) {
            if (!shell.getDisplay().readAndDispatch()) {
                shell.getDisplay().sleep();
            }
        }
        graphics.dispose();
    }

    private void addKeyboardListener() {
        shell.addListener(SWT.KeyDown, e -> {
            Game game = graphics.getGame();
            switch (e.keyCode) {
                case 'w':
                case 'W':
                case SWT.ARROW_UP:
                    if (game.getDirection() != Game.DIRECTION_DOWN) {
                        game.setDirection(Game.DIRECTION_UP);
                    }
                    break;
                case 'a':
                case 'A':
                case SWT.ARROW_LEFT:
                    if (game.getDirection() != Game.DIRECTION_RIGHT) {
                        game.setDirection(Game.DIRECTION_LEFT);
                    }
                    break;
                case 's':
                case 'S':
                case SWT.ARROW_DOWN:
                    if (game.getDirection() != Game.DIRECTION_UP) {
                        game.setDirection(Game.DIRECTION_DOWN);
                    }
                    break;
                case 'd':
                case 'D':
                case SWT.ARROW_RIGHT:
                    if (game.getDirection() != Game.DIRECTION_LEFT) {
                        game.setDirection(Game.DIRECTION_RIGHT);
                    }
                    break;
                case SWT.CR:
                case SWT.LF:
                case SWT.TAB:
                    if (game.isGameOver()) {
                        graphics.isGameOver = false;
                        graphics.tryAgainButton.setVisible(false);
                        graphics.initializeGame();
                    }
                    break;
            }
        });
    }
}
