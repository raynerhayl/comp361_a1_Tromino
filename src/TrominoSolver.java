/**
 * Created by raynerhayl on 27/07/16.
 */

import ecs100.*;

import java.awt.Color;
import java.util.*;

/**
 * Write a description of class Tromino here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TrominoSolver {

    final int width = 500;
    final int height = 500;

    final int top = 30;
    final int left = 20;

    private int emptyCol;
    private int emptyRow;

    List<String> text = new ArrayList<String>();
    int textIndex = 0;

    Board board;
    Tromino trom;

    /**
     * Constructor for objects of class Tromino
     */
    public TrominoSolver() {
        UI.initialise();

        UI.addButton("New Grid", this::newGrid);
        UI.addButton("Move forward", this::moveForward);
        UI.addButton("Move backward", this::moveBackward);


        board = new Board(top, left, width, height);

    }

    public void newGrid() {
        board.reset();
        UI.clearText();
        text.clear();

        int cols = UI.askInt("Enter the number of coloums");
        int rows = UI.askInt("Enter the number of rows");

        emptyCol = UI.askInt("Enter coloum of empty tile");
        while (emptyCol >= cols && emptyCol < 0) {
            emptyCol = UI.askInt("Coloum must be greater than -1 and less than " + cols);
        }

        emptyRow = UI.askInt("Enter row of empty tile");
        while (emptyRow >= rows && emptyRow < 0) {
            emptyRow = UI.askInt("Row must be greater than -1 and less than " + rows);
        }

        board.setEmptyTile(emptyCol, emptyRow);

        UI.println("Displaying grid: " + cols + " x " + rows);

        UI.clearGraphics();

        //tile(2, emptyCol, emptyRow, 0, rows - 1);

        board.setSize(cols, rows);
        board.drawBoard();

        text.add("Displaying grid: " + cols + " x " + rows + "\nMissing tile at: " + emptyCol + ", " + emptyRow);

        tile(cols, emptyCol, emptyRow, 0, 0);

        //UI.clearGraphics();
    }

    public void moveForward() {
        board.moveForward();
        UI.clearGraphics();
        textIndex = textIndex + 1;
        if (textIndex >= text.size()) {
            textIndex = text.size() - 1;
        }
        displayText();
        board.drawBoard();
    }

    public void moveBackward() {
        board.moveBackward();
        UI.clearGraphics();
        textIndex = textIndex - 1;
        if (textIndex <= 1) {
            textIndex = 1;
        }
        displayText();
        board.drawBoard();
    }

    public void displayText() {
        UI.clearText();
        if (text.size() > 0) {
            for (int i = 0; i <= textIndex && i < text.size(); i++) {
                UI.println(text.get(i));
            }
        }
    }

    /**
     * Draws an arrow head at the given position with the
     * given size. The arrow is either horizontal or vertical
     * depending on the argument given.
     * <p>
     * The arrow is draw within a square,the size argument
     * represent the dimensions of this square.
     * <p>
     * The position is the top left corner of this box around
     * the arrow.
     */
    public void drawArrow(boolean horizontal, double x, double y, int size) {
        if (horizontal) {
            y = (y - (double) size / 2.0);

            UI.drawLine(x + size, y + (double) size / 2.0, x, y);
            UI.drawLine(x + size, y + (double) size / 2.0, x, y + size);
        } else {
            x = (x - (double) size / 2.0);
            y = (y - size);
            UI.drawLine(x + (double) size / 2.0, y, x, y + size);
            UI.drawLine(x + (double) size / 2.0, y, x + size, y + size);
        }
    }

    /**
     * Tile a board of size n with the board posiiton starting at
     * pX, pY as the top left tile and missing a tile at mX, mY
     */

    public void tile(int n, int mX, int mY, int lX, int lY) {
        if (n == 2) {
            String text1 = "Missing: " + mX + ", " + mY;
            int x = mX;
            int y = mY;
            Tromino.Type type = Tromino.Type.NULL;
            String sType = "Null";

            if (mX % 2 == 0) {
                x = mX + 1;
            }

            if (mY % 2 == 0) {
                y = mY + 1;
            }

            if (mX % 2 == 0 && mY % 2 != 0) {
                type = Tromino.Type.LR;
                sType = "LR";

            } else if (mX % 2 != 0 && mY % 2 != 0) {
                type = Tromino.Type.LL;
                sType = "LL";

            } else if (mX % 2 != 0 && mY % 2 == 0) {
                type = Tromino.Type.UL;
                sType = "UL";

            } else if (mX % 2 == 0 && mY % 2 == 0) {
                type = Tromino.Type.UR;
                sType = "UR";
            }

            text.add(x + " " + y + " " + sType + " : " + text1);
            textIndex = textIndex + 1;
            displayText();
            board.addTromino(new Tromino(x, y, type, board));

            board.drawBoard();

            return;

        } else if(n>2){

            int x = lX;
            int y = lY;
            Tromino.Type type = Tromino.Type.NULL;
            String sType = "NULL";

            if (mX < lX && mY >= lY) {
                type = Tromino.Type.LR;
                sType = "LR";
            } else if (mX >= lX && mY >= lY) {
                type = Tromino.Type.LL;
                sType = "LL";
            } else if (mX < lX && mY < lY) {
                type = Tromino.Type.UR;
                sType = "UR";
            } else if (mX >= lX && mY < lY) {
                type = Tromino.Type.UL;
                sType = "UL";
            }

            text.add(x + " " + y + " " + sType);
            textIndex = textIndex + 1;
            displayText();

            Tromino t = new Tromino(x, y, type, board);
            int[] newEmptys = t.getTiles();

            board.addTromino(t);

            board.drawBoard();

            if (sType == "LL") {
                tile(n / 2, mX, mY, lX+n/2, lY+n/2);
                tile(n / 2, newEmptys[0], newEmptys[1], lX-n/2, lY+n/2);
                tile(n / 2, newEmptys[2], newEmptys[3], lX+n/2, lY-n/2);
                tile(n / 2, newEmptys[4], newEmptys[5], lX-n/2, lY-n/2);
            } else if(sType == "LR"){
                tile(n / 2, mX, mY, lX-n/2, lY+n/2);
                tile(n / 2, newEmptys[0], newEmptys[1], lX+n/2, lY+n/2);
                tile(n / 2, newEmptys[2], newEmptys[3], lX+n/2, lY-n/2);
                tile(n / 2, newEmptys[4], newEmptys[5], lX-n/2, lY-n/2);
            } else if(sType == "UL"){
                tile(n / 2, mX, mY, lX+n/2, lY-n/2);
                tile(n / 2, newEmptys[0], newEmptys[1], lX-n/2, lY+n/2);
                tile(n / 2, newEmptys[2], newEmptys[3], lX+n/2, lY-n/2);
                tile(n / 2, newEmptys[4], newEmptys[5], lX-n/2, lY-n/2);
            } else if(sType == "UR"){
                tile(n / 2, mX, mY, lX-n/2, lY-n/2);
                tile(n / 2, newEmptys[0], newEmptys[1], lX-n/2, lY+n/2);
                tile(n / 2, newEmptys[2], newEmptys[3], lX+n/2, lY+n/2);
                tile(n / 2, newEmptys[4], newEmptys[5], lX+n/2, lY-n/2);
            }
        }
    }

    public static void main(String[] args) {
        new TrominoSolver();
    }
}
