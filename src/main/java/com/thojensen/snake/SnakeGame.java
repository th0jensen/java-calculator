package com.thojensen.snake;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SnakeGame implements Runnable {
    private static Display display;
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
        display = new Display();
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
        shell.setSize(400, 400);

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
                case SWT.ARROW_UP:
                case 'W':
                case 'w':
                    if (game.getDirection() != Game.DIRECTION_DOWN) {
                        game.setDirection(Game.DIRECTION_UP);
                    }
                    break;
                case SWT.ARROW_LEFT:
                case 'A':
                case 'a':
                    if (game.getDirection() != Game.DIRECTION_RIGHT) {
                        game.setDirection(Game.DIRECTION_LEFT);
                    }
                    break;
                case SWT.ARROW_DOWN:
                case 'S':
                case 's':
                    if (game.getDirection() != Game.DIRECTION_UP) {
                        game.setDirection(Game.DIRECTION_DOWN);
                    }
                    break;
                case SWT.ARROW_RIGHT:
                case 'D':
                case 'd':
                    if (game.getDirection() != Game.DIRECTION_LEFT) {
                        game.setDirection(Game.DIRECTION_RIGHT);
                    }
                    break;

            }
        });
    }
}
