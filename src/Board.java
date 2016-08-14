/**
 * Created by raynerhayl on 27/07/16.
 */

import ecs100.*;

import java.awt.Color;
import java.util.*;

/**
 * Write a description of class Board here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Board {
    // instance variables - replace the example below with your own
    private int top;
    private int left;
    private int width;
    private int height;

    public int cols = 0;
    public int rows = 0;

    private int emptyCol;
    private int emptyRow;

    private int[][] grid;

    private List<Tromino> trominos = new ArrayList<Tromino>();

    public int index = 0;

    /**
     * Constructor for objects of class Board
     */
    public Board(int top, int left, int width, int height) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;

        grid = new int[width][height];
    }

    public void setSize(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
    }

    public void moveForward() {
        index = index + 1;
        if (index >= trominos.size()) {
            index = trominos.size() - 1;
        }
    }

    public void moveBackward() {
        index = index - 1;
        if (index < 0) {
            index = 0;
        }
    }

    public void drawBoard() {

        UI.setColor(Color.lightGray);
        for (int col = 1; col < cols; col++) {
            UI.drawLine(left + col * (double) width / (double) cols, top + height, left + col * (double) width / (double) cols, top);
        }

        for (int row = 0; row < rows + 1; row++) {
            UI.drawLine(left, top + row * (double) height / (double) rows, left + width, top + row * (double) height / (double) rows);
        }

        UI.setColor(Color.black);

        UI.drawLine(left, top, left, top + height);
        UI.drawLine(left, top + height, left + width, top + height);

        fillTile(emptyCol, emptyRow, Color.blue);

        for (int i = 0; i <= index && i < trominos.size(); i++) {
            Color c = Color.red;
            if (i == index) {
                c = Color.blue;
            }
            trominos.get(i).draw(c);
        }


    }

    public void setEmptyTile(int col, int row) {
        grid[col][row] = 1;
        emptyCol = col;
        emptyRow = row;
    }

    public void setGrid(int col, int row) {
        if (col >= 0 && row >= 0 && col < grid.length && row < grid[0].length) {
            grid[col][row] = 1;
        }
    }

    public int getGrid(int col, int row) {
        if (col >= 0 && row >= 0 && col < grid.length && row < grid[0].length) {

            return grid[col][row];
        } else {
            return 0;
        }
    }

    public void addTromino(Tromino t) {
        trominos.add(t);
        index = index + 1;
    }

    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        double sX1 = left + x1 * ((double) width / (double) rows);
        double sX2 = left + x2 * ((double) width / (double) rows);

        double sY1 = top + height - y1 * ((double) height / (double) cols);
        double sY2 = top + height - y2 * ((double) height / (double) cols);

        UI.setColor(color);
        UI.drawLine(sX1, sY1, sX2, sY2);
    }

    public void reset() {
        grid = new int[width][height];
        trominos = new ArrayList<Tromino>();
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        drawLine(x1, y1, x2, y2, Color.black);
    }

    public void fillTile(int col, int row, Color color) {
        UI.setColor(color);
        UI.fillRect(left + col * ((double) width / (double) rows), top + height - (row + 1) * ((double) height / (double) cols), (double) width / (double) rows, (double) height / (double) cols);
    }

    public void fillTile(int col, int row) {
        fillTile(col, row, Color.blue);
    }
}
