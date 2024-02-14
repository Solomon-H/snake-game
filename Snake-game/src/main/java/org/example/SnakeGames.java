package org.example;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class SnakeGames {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 10;

    private LinkedList<Point> snake;
    private Point fruit;
    private char direction;
    private boolean isGameOver;
    private Random random;

    public SnakeGames() {
        snake = new LinkedList<>();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));
        fruit = new Point(0, 0); // initial fruit position, will be updated later
        direction = 'R'; // initial direction is right
        isGameOver = false;
        random = new Random();

        placeFruit();
    }

    public void placeFruit() {
        int x, y;
        do {
            x = random.nextInt(WIDTH);
            y = random.nextInt(HEIGHT);
        } while (snake.contains(new Point(x, y)));
        fruit.setLocation(x, y);
    }

    public void move() {
        Point head = snake.getFirst();
        Point newHead = new Point(head.x, head.y);

        switch (direction) {
            case 'U':
                newHead.y--;
                break;
            case 'D':
                newHead.y++;
                break;
            case 'L':
                newHead.x--;
                break;
            case 'R':
                newHead.x++;
                break;
        }

        // Check if new head hits the wall or itself
        if (newHead.x < 0 || newHead.x >= WIDTH || newHead.y < 0 || newHead.y >= HEIGHT ||
                snake.contains(newHead)) {
            isGameOver = true;
            return;
        }

        // Check if new head is on fruit
        if (newHead.equals(fruit)) {
            snake.addFirst(newHead);
            placeFruit();
        } else {
            snake.addFirst(newHead);
            snake.removeLast();
        }
    }

    public void display() {
        char[][] grid = new char[HEIGHT][WIDTH];

        // Initialize grid with empty cells
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j] = ' ';
            }
        }

        // Place snake on grid
        for (Point p : snake) {
            grid[p.y][p.x] = 'o';
        }

        // Place fruit on grid
        grid[fruit.y][fruit.x] = 'X';

        // Display the grid
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(grid[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (!isGameOver) {
            display();
            System.out.println("Enter direction (W/A/S/D): ");
            direction = scanner.next().charAt(0);
            move();
        }

        System.out.println("Game Over!");
        scanner.close();
    }

}

