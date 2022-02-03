//importing all the packages needed
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.awt.*;
import javax.swing.*;

// here we declare all the public classes to be used in the project
public class gui extends JFrame {
// to rest the program
    public boolean resetter = false;
//for timer
    Date startDate = new Date();
    Date endDate;
//the message
    String vicMes = "not yet";

    int spacing = 5;
    int neighs = 0;
//the x and y axis for the block spacing
    public int mx = -100;
    public int my = -100;
//boolean value for the flag
    public boolean flagger = false;
//emoji allignment
    public int circleX = 350;
    public int circleY = 5;
//boolean value for the emoji
    public boolean happy = true;
//spacing for eyes and mouth for emoji
    public int circleCenterX = circleX + 35;
    public int circleCenterY = circleY + 35;
//flag allignment
    public int flaggerX = 300;
    public int flaggerY = 5;
//bock space allignment
    public int spacingX = 80;
    public int spacingY = 5;
//axis for the sign + and -
    public int plusX = 150;
    public int plusY = 4;

    public int minusX = 100;
    public int minusY = 4;

    public int flaggerCenterX = flaggerX + 35;
    public int flaggerCenterY = flaggerY + 35;
//timer label
    public int timeX = 700;
    public int timeY = 5;
//victory message
    public int vicMesX = 500;
    public int vicMesY = 150;

    public int sec = 0;
//boolean values for the victory and defeat message
    public boolean victory = false;

    public boolean defeat = false;

    //inputing the random so it works on the basis of random
    Random rand = new Random();

    //designing the window
    int[][] mines = new int [16][9];
    int[][] nears = new int[16][9];
    boolean[][] reveal = new boolean[16][9];
    boolean[][] flag = new boolean[16][9];
    public gui(){
        this.setTitle("minesweeper");
        this.setSize(830,  600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        //the cubes are placed here
        for (int i = 0; i< 16; i++){
            for(int j = 0; j< 9; j++) {
                if (rand.nextInt(100)<20){
                    mines[i][j] = 1;
                } else {
                    mines[i][j] = 0;
                }
                reveal[i][j] = false;
            }
        }

        //the near by mines will be shown
        for (int i = 0; i< 16; i++) {
            for (int j = 0; j < 9; j++) {
                neighs = 0;
                for (int m = 0; m < 16; m ++){
                    for(int n = 0; n < 9; n ++){
                        if (!(m == i && n ==j)){
                            if (isN(i,j,m,n) == true)
                                neighs++;
                        }
                    }
                }
                nears[i][j] = neighs;
            }
        }

        //declaring the new board, move, click
        Board board = new Board();
        this.setContentPane(board);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click() {
            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        this.addMouseListener(click);

    }

    // the status which shows wheather we win or lose
    public void checkVictoryStatus() {
        if (defeat == false){
            for(int i = 0; i<16; i++){
                for(int j = 0; j<9; j++){
                    if(reveal[i][j] == true && mines[i][j] == 1){
                        defeat = true;
                        happy = false;
                        endDate = new Date();
                    }
                }
            }
        }

        if (totalboxrevealed() >= 144 - totalmines() && victory == false){
            victory = true;
            endDate = new Date();
        }
    }

    // declaring the total mines
    private int totalmines() {
        int total = 0;
        for (int i = 0; i< 16; i++){
            for(int j = 0; j< 9; j++) {
                if(mines[i][j] == 1){
                    total++;
                }
            }
        }
        return total;
    }

    // number of boxes revealaed
    private int totalboxrevealed() {int total = 0;
        for(int i = 0; i<16; i++){
            for(int j = 0; j<9; j++){
                if(reveal[i][j] == true){
                    total++;
                }
            }
        }
        return total;

    }

    // designing the colour of the window
    public class Board extends JPanel{
        public void paintComponent(Graphics g){
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0,900,800);
            for (int i = 0; i< 16; i++){
                for(int j = 0; j< 9; j++){
                    g.setColor(Color.gray);
                    if (reveal[i][j] == true){
                        g.setColor(Color.white);
                        if (mines[i][j] == 1) {
                            g.setColor(Color.red);
                        }
                    }

                    // showing the space
                    if (mx >= spacing+i*50 && mx < spacing+i*50+50-2*spacing && my>= spacing+j*50+50+26 && my < spacing+j*50+10+50+50-2*spacing){
                        g.setColor(Color.lightGray);
                    }
                    g.fillRect(spacing+i*50, spacing+j*50+50, 50-1*spacing, 50-1*spacing);

                    //coding the numbers in colours
                    if (reveal[i][j] == true){
                        g.setColor(Color.black);
                        if (mines[i][j] == 0 && nears[i][j] != 0) {
                            if (nears[i][j] == 1){
                                g.setColor(Color.blue);
                            } else if(nears[i][j] == 2) {
                                g.setColor(Color.green);
                            }
                            else if(nears[i][j] == 3) {
                                g.setColor(Color.red);
                            }
                            else if(nears[i][j] == 4) {
                                g.setColor(new Color(0,0,128) );
                            }
                            else if(nears[i][j] == 5) {
                                g.setColor(new Color(178,34,34));
                            }
                            else if(nears[i][j] == 6) {
                                g.setColor(new Color(72,209,204));
                            }
                            else if(nears[i][j] == 8) {
                                g.setColor(new Color(0,0,128));
                            }
                            g.setFont(new Font("tahoma", Font.BOLD, 30));
                            g.drawString(Integer.toString(nears[i][j]), i * 50 + 13, j * 50 + 50 + 40);
                        } else if (mines[i][j] == 1) {
                            g.fillOval(i*50+3+10, j*50+47+15, 30,30);
                        }
                    }

                    // flag painting
                    if (flag[i][j]==true){
                        g.setColor(Color.BLACK);
                        g.fillRect(i*50+5+20,j*50+50+10+2, 4, 30);
                        g.fillRect(i*50-1+20, j*50+50+35+2, 20, 5);
                        g.setColor(Color.red);
                        g.fillRect(i*50-5+20, j*50+50+10+2, 15  , 10);
                        g.setColor(Color.black);
                        g.drawRect(i*50-5+20,j*50+50+10+2, 16, 10);
                        g.drawRect(i*50-5+20,j*50+50+9+2, 14, 14);
                        g.drawRect(i*50-5+20,j*50+50+9+2, 15, 12);
                    }
                }
            }
            //spacing
            g.setColor(Color.black);
            g.fillRect(spacingX+30, spacingY, 80,50);
            g.setColor(Color.white);
            g.fillRect(minusX+5,minusY+5, 40,40);
            g.fillRect(plusX+5,plusY+5, 40,40);

            g.setFont(new Font ("tahoma", Font.PLAIN, 15));
            g.drawString("Block size", spacingX-70, spacingY+30);

            g.setColor(Color.BLACK);

            g.fillRect(minusX+15, minusY+23,20,6);
            g.fillRect(minusX+65, minusY+23,20,6);
            g.fillRect(minusX+72, minusY+16,6,20);

            //smiley
            g.setColor(Color.red);
            g.fillOval(circleX, circleY, 50, 50);
            g.setColor(Color.black);
            g.fillOval(circleX+10, circleY+10, 10,10);
            g.fillOval(circleX+30, circleY+10, 10,10);

            if (happy == true ){
                g.fillRect(circleX+10, circleY+30, 30,5);
            } else {
                g.fillOval(circleX+17 , circleY+30, 15,15);
            }

            //flag
            g.setColor(Color.BLACK);
            g.fillRect(flaggerX+8,flaggerY+10, 4, 30);
            g.fillRect(flaggerX-1, flaggerY+35, 20, 5);
            g.setColor(Color.red);
            g.fillRect(flaggerX-5, flaggerY+10, 15  , 10);
            g.setColor(Color.black);
            g.drawRect(flaggerX-5,flaggerY+10, 16, 10);
            g.drawRect(flaggerX-6,flaggerY+9, 15, 14);
            g.drawRect(flaggerX-6,flaggerY+9, 15, 12);
            if (flagger == true){
                g.setColor(Color.red);
            }
            g.drawOval(flaggerX-16, flaggerY+3 , 50,50);
            g.drawOval(flaggerX-16, flaggerY+1, 50,50);
            g.drawOval(flaggerX-16, flaggerY+2, 50,  50);

            //timer
            g.setColor(Color.white);
            g.fillRect(timeX, timeY, 100, 50);
            if (defeat == false && victory == false){
                sec = (int)((new Date().getTime()-startDate.getTime())/1000);
            }
            if (sec> 999){
                sec = 999;
            }
            g.setColor(Color.black);
            if (victory == true){
                g.setColor(Color.green);
            } else if (defeat == true){
                g.setColor(Color.red);
            }
            g.setFont(new Font("tahoma",Font.PLAIN,40));
            if (sec<10){
                g.drawString("00"+ Integer.toString(sec),timeX, timeY+40);
            }else if (sec < 100){
                g.drawString("0"+ Integer.toString(sec),timeX, timeY+40);
            } else {
                g.drawString( Integer.toString(sec),timeX, timeY+40);
            }
            //message
            if(victory == true){
                g.setColor(Color.green);
                vicMes = "WON";
            } else if (defeat == true) {
                g.setColor(Color.red);
                vicMes = "Lose";
            }
            if (victory == true || defeat == true){
                vicMesY = 150+(int) (new Date().getTime() - endDate.getTime()) / 10;
                if (vicMesY > 45){
                    vicMesY = 45;
                }
                g.setFont(new Font("Tahoma", Font.PLAIN, 45));
                g.drawString(vicMes, vicMesX, vicMesY);
            }
        }
    }
    public class Move implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent arg0){

        }
        @Override
        public void mouseMoved(MouseEvent e){
            mx = e.getX();
            my = e.getY();
        }
    }
    public abstract class Click implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            mx = e.getX();
            my = e.getY();

            // the space moves in and out
            if(mx >= minusX+20 && mx < minusX+60 && my >= minusY+20 && my <  minusY+60){
                spacing--;
                if(spacing < 1){
                    spacing = 1;
                }
            }
            if(mx >= plusX+20 && mx < plusX+60 && my >= plusY+20 && my <  plusY+60){
                spacing--;
                if(spacing < 15){
                    spacing = 15;
                }
            }
            if (inBoxX() != -1 && inBoxY() != -1){
                System.out.println("the mouse is in the [" + inBoxX() + "," + inBoxY() + "], number of " + nears [inBoxX()][inBoxY()]);
                if (flagger == true && reveal[inBoxX()][inBoxY()]==false){
                    if(flag[inBoxX()][inBoxY()] == false){
                        flag[inBoxX()][inBoxY()] = true;
                    } else {
                        flag[inBoxX()][inBoxY()] = false;
                    }
                } else {
                    if (flag[inBoxX()][inBoxY()]==false){
                        reveal[inBoxX()][inBoxY()] = true;
                    }
                }
            }else {
                System.out.println("pointer is not inside the box");
            }
            if(inCircle() ==  true){
                resetAll();
                System.out.println("inside the smiley");
            }
            if (inFlagger() == true){
                if(flagger == false){
                    flagger = true;
                    System.out.println("in flagger = true");
                }else{
                    flagger = false;
                    System.out.println("in flagger = false");
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent arg0) {

        }

        @Override
        public void mouseReleased(MouseEvent arg0) {

        }

        //resets the entire program
        public void resetAll(){
            resetter = true;
            flagger = false;
            startDate = new Date();
            vicMesX = 100;
            vicMes = "not yet";
            happy = true;
            victory = false;
            defeat = false;

            for (int i = 0; i< 16; i++){
                for(int j = 0; j< 9; j++) {
                    if (rand.nextInt(100)<20    ){
                        mines[i][j] = 1;
                    } else{
                        mines[i][j] = 0;
                    }
                    reveal[i][j] = false;
                    flag[i][j] =  false;
                }
            }
            for (int i = 0; i< 16; i++) {
                for (int j = 0; j < 9; j++) {
                    neighs = 0;
                    for (int m = 0; m < 16; m ++){
                        for(int n = 0; n < 9; n ++){
                            if (!(m == i && n ==j)){
                                if (isN(i,j,m,n) == true)
                                    neighs++;
                            }
                        }
                    }
                    nears[i][j] = neighs;
                }
            }
            resetter = false;
        }

        public boolean inCircle(){
            int dif = (int) Math.sqrt(Math.abs(mx-circleCenterX)*Math.abs(mx-circleCenterX)+Math.abs(my-circleCenterY)*Math.abs(my-circleCenterY));
            if (dif<35){
                return true;
            }
            return false;
        }

        public boolean inFlagger(){
            int dif = (int) Math.sqrt(Math.abs(mx-flaggerCenterX)*Math.abs(mx-flaggerCenterX)+Math.abs(my-flaggerCenterY)*Math.abs(my-flaggerCenterY));
            if (dif<35){
                return true;
            }
            return false;
        }
        public int inBoxX(){
            for (int i = 0; i< 16; i++){
                for(int j = 0; j< 9; j++){
                    if (mx >= spacing+i*50 && mx < spacing+i*50+50-2*spacing && my>= spacing+j*50+50+26 && my < spacing+j*50+10+50+50-2*spacing){
                        return i;
                    }
                }
            }
            return -1;
        }
        public int inBoxY(){
            for (int i = 0; i< 16; i++) {
                for (int j = 0; j < 9; j++) {
                    if (mx >= spacing + i * 50 && mx < spacing + i * 50 + 50 - 2 * spacing && my >= spacing + j * 50 + 50 + 26 && my < spacing + j * 50 + 10 + 50 + 50 - 2 * spacing) {
                        return j;
                    }
                }
            }
            return -1;
        }

    }
    public boolean isN(int mX, int mY, int cX, int cY){
        if (mX - cX < 2 && mX - cX > - 2 && mY - cY < 2 && mY - cY > - 2 && mines[cX][cY] == 1 ){
            return true;
        }
        return false;
    }


}
