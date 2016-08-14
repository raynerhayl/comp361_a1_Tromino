/**
 * Created by raynerhayl on 27/07/16.
 */
import java.util.*;
import java.awt.Color;
import ecs100.*;

/**
 * Write a description of class Tromino here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Tromino
{

    private int x;
    private int y;

    private Board board;

    private int[] tiles = new int[6];

    public enum Type{
        UL, UR, LL, LR, NULL
    }

    private Type type;

    /**
     * Constructor for objects of class Tromino
     */
    public Tromino(int x , int y, Type type, Board board)
    {
        this.x = x;
        this.y = y;
        this.type = type;
        this.board = board;

        switch(type){
            case UL:
                tiles = new int[]{x-1,y,x,y,x-1,y-1};
                board.setGrid(x-1,y);
                board.setGrid(x,y);
                board.setGrid(x-1,y-1);
                break;

            case UR:
                tiles = new int[]{x-1,y,x,y,x,y-1};
                board.setGrid(x-1,y);
                board.setGrid(x,y);
                board.setGrid(x,y-1);
                break;

            case LL:
                tiles = new int[]{x-1,y,x,y-1,x-1,y-1};
                board.setGrid(x-1,y);
                board.setGrid(x,y-1);
                board.setGrid(x-1,y-1);

                break;

            case LR:
                tiles = new int[]{x,y,x,y-1,x-1,y-1};
                board.setGrid(x,y);
                board.setGrid(x,y-1);
                board.setGrid(x-1,y-1);

                break;
        }

    }

    public void draw(Color color){
        List<Line> lines = new ArrayList<Line>();

        Color c = color;

        UI.setLineWidth(3);

        switch(type){
            case UL :
                board.drawLine(x,y,x,y-1, c);
                board.drawLine(x,y-1,x-1,y-1, c);
                board.drawLine(x-1,y-1,x-1,y+1, c);
                board.drawLine(x-1,y+1,x+1,y+1, c);
                board.drawLine(x+1,y+1,x+1,y, c);
                board.drawLine(x+1,y,x,y, c);
                break;

            case UR :
                board.drawLine(x,y,x-1,y,c);
                board.drawLine(x-1,y,x-1,y+1,c);
                board.drawLine(x-1,y+1,x+1,y+1,c);
                board.drawLine(x+1,y+1,x+1,y-1,c);
                board.drawLine(x+1,y-1,x,y-1,c);
                board.drawLine(x,y-1,x,y,c);
                break;

            case LL:
                board.drawLine(x,y,x+1,y,c);
                board.drawLine(x+1,y,x+1,y-1,c);
                board.drawLine(x+1,y-1,x-1,y-1,c);
                board.drawLine(x-1,y-1,x-1,y+1,c);
                board.drawLine(x-1,y+1,x,y+1,c);
                board.drawLine(x,y+1,x,y,c);
                break;

            case LR:
                board.drawLine(x,y,x,y+1,c);
                board.drawLine(x,y+1,x+1,y+1,c);
                board.drawLine(x+1,y+1,x+1,y-1,c);
                board.drawLine(x+1,y-1,x-1,y-1,c);
                board.drawLine(x-1,y-1,x-1,y,c);
                board.drawLine(x-1,y,x,y,c);
                break;

        }

        UI.setLineWidth(1);

    }

    public int[] getTiles(){
        return tiles;
    }

    class Line{
        int x1;
        int y1;
        int x2;
        int y2;

        public Line(int x1, int y1, int x2, int y2){
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

    }
}
