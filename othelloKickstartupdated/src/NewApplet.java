/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author sushantbhat
 */
/*
    <applet code = "NewApplet" width = 1600 height = 1200>
    </applet>
*/
public class NewApplet extends Applet implements Runnable{
    int sec;
    final int BLACK =  1;	
    final int WHITE = -1;
    final int EMPTY =  0;

    final int WIDTH = 480;

    final int SPACE = 80;

    int whiteUndo;
    int blackUndo;
    
    private final int UPPER 	= 0;
    private final int LOWER 	= 1;
    private final int RIGHT 	= 2;
    private final int LEFT  	= 3;
    private final int UPPERLEFT 	= 4;
    private final int UPPERRIGHT	= 5;
    private final int LOWERRIGHT	= 6;
    private final int LOWERLEFT	= 7;
    boolean direction[] = 
      {false, false, false, false, false, false, false, false};
   
    boolean flag = true;
    public int turn;

    protected int stone[][] = new int[8][8];

    protected int counter_black = 0, counter_white = 0;

    
    String player1 = new String();
    String player2 = new String();
    int userid1;
    int userid2;
    
    public NewApplet(String name1 , String name2 , int userid1 , int userid2){
        
        
        player1 = name1;
        player2 = name2;
        this.userid1 = userid1;
        this.userid2 = userid2;
        whiteUndo = 2;
        blackUndo = 2;
    }

    public void init() {
        // TODO start asynchronous download of heavy resources
        stone = new int[8][8];
        /*NewJFrame2 frm = new NewJFrame2();
        
        //userid1 = frm.getId(1);
        //userid2 = frm.getId(2);
        //System.out.println(userid1 + " " + userid2);
        
        player1 = frm.user1;
        player2 = frm.user2;*/
        
        
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
              stone[i][j] = EMPTY;
            }
        }
     /********************************************************************

                           test cases 

      *******************************************************************/   
        
     /** 
        when all disks are black match should be ended and black declared winner
        stone[3][3] = BLACK; stone[4][3] =BLACK; stone[5][3] = BLACK;
        stone[3][4] = WHITE; stone[4][4] = BLACK;
        
      */  
        
      /** 
        when all disks are white match should be ended and white declared winner
        stone[3][3] = WHITE; stone[4][3] =WHITE; stone[5][3] = WHITE;
        stone[3][4] = BLACK; stone[4][4] = WHITE;
        turn = WHITE; //comment out the turn statement below
      */
        
      /** when white is left with no legal moves its turn will be skipped
        
        stone[0][0] = WHITE;
        stone[1][0] = WHITE;
        stone[2][0] = WHITE;
        stone[0][1] = WHITE;
        stone[1][1] = BLACK;
        stone[2][1] = WHITE;
        stone[0][2] = WHITE;
        stone[1][2] = WHITE;
        stone[2][2] = WHITE;
        stone[0][3] = WHITE;
        stone[2][3] = WHITE;
        stone[0][4] = WHITE;
        stone[1][4] = WHITE;
        stone[2][4] = WHITE;
        
       */
        
        /** when black is left with no legal moves its turn will be skipped
  
        stone[0][0] = BLACK;
        stone[1][0] = BLACK;
        stone[2][0] = BLACK;
        stone[0][1] = BLACK;
        stone[1][1] = WHITE;
        stone[2][1] = BLACK;
        stone[0][2] = BLACK;
        stone[1][2] = BLACK;
        stone[2][2] = BLACK;
        stone[0][3] = BLACK;
        stone[2][3] = BLACK;
        stone[0][4] = BLACK;
        stone[1][4] = BLACK;
        stone[2][4] = BLACK;
        turn = WHITE; //comment out the turn statement below
    
        */
        
        
        //regular initialization
        stone[3][3] = BLACK; stone[4][3] = WHITE;
        stone[3][4] = WHITE; stone[4][4] = BLACK;
        diskCount();
        turn = BLACK;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(NewApplet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // TODO overwrite start(), stop() and destroy() methods


    public void run() {
        
    }
    
    public void paint(Graphics g) {
        flag = true;
        
        createBoard(g);
        
        
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
              if (stone[i][j] != 0){
                placeDisk(i, j, g);
              }
            }
        }
        
        if(turn == BLACK){
            if(!legalCheck(BLACK)){
                if(!legalCheck(WHITE)){
                    terminateGame();
                
                }
                turn = WHITE;
            }
        }
        if(turn == WHITE){
            if(!legalCheck(WHITE)){
                if(!legalCheck(BLACK)){
                    terminateGame();
                   
                }
                turn = BLACK;
            }
        }
        
        showTurn(g);
        displayCount(g);

        if (counter_black + counter_white == 64) {
                terminateGame();
        }
        
    }
    public void createBoard (Graphics g) {

        
        Color c = new Color(0x1e824c);
        setBackground(c);
        

        g.setColor(Color.WHITE);
        g.drawLine(0,0, 0,WIDTH);		
        g.drawLine(WIDTH,0, WIDTH,WIDTH);	
        g.drawLine(0,0, WIDTH,0);		
        g.drawLine(0,WIDTH, WIDTH,WIDTH);
        
        g.drawLine(WIDTH, 0, WIDTH+60, 0);
        g.drawLine(WIDTH+60,0,WIDTH+60,60);
        g.drawLine(WIDTH+60,60,WIDTH,60);
        
        
        
        for(int i = 1; i < 8; i++){

            g.drawLine(WIDTH*i/8,0, WIDTH*i/8,WIDTH);
 
            g.drawLine(0,WIDTH*i/8, WIDTH,WIDTH*i/8);
        }
        g.setColor(Color.BLACK);
        g.fillRect(WIDTH,0, 60, 60);
        g.setColor(Color.WHITE);
        g.drawString("UNDO", WIDTH+10, 30);
    }
   
     
     
    public void placeDisk(int column, int row, Graphics g) {

        if (stone[column][row] == BLACK) {
          g.setColor(Color.black);
        } else if (stone[column][row] == WHITE){
          g.setColor(Color.white);
        }
        g.fillOval(column * WIDTH / 8 + 10, row * WIDTH / 8 + 10, 
                      WIDTH / 12, WIDTH / 12);
        
    }
  
    
    void undoMove(){
        if(turn == WHITE){
            if(blackUndo == 0){
                JOptionPane.showMessageDialog(null, "No more Undo moves left for Black", "Oops!", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            for(int k=0 ; k<8 ; k++)
                for(int u=0 ; u<8 ; u++){
                    if(undo[u][k] == 999){
                        stone[u][k] = 0;
                        
                    }
                    else if(undo[u][k] == 1){
                        stone[u][k] = WHITE;
                    }
                }
            diskCount();
            turn = BLACK;
            blackUndo--;
            update(getGraphics());
        }
        else if(turn == BLACK){
            if(whiteUndo == 0){
                JOptionPane.showMessageDialog(null, "No more Undo moves left for White", "Oops!", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            for(int k=0 ; k<8 ; k++)
                for(int u=0 ; u<8 ; u++){
                    if(undo[u][k] == 999){
                        stone[u][k] = 0;
                    }
                    else if(undo[u][k] == 1){
                        stone[u][k] = BLACK;
                    }
                }
            diskCount();
            turn = WHITE;
            whiteUndo--;
            update(getGraphics());
        }
    }
    
    public boolean mouseUp(Event e, int x, int y) {
        
       /* g.drawString("*", WIDTH+85, 150);
        g.drawString("*", WIDTH+85, 140);
        g.drawString("*", WIDTH+75, 150);
        g.drawString("*", WIDTH+75, 140);
        
        if(x>WIDTH+75 && x<WIDTH+85)
            if(y>WIDTH+140 && y<WIDTH+150){
                System.out.println("got it");
            }*/
        
        int column = (int)(x / (WIDTH / 8));
        int row	   = (int)(y / (WIDTH / 8));
        if(column == 8 && row == 0){
            undoMove();
        }
        if (turn == BLACK) {
                if (placeLegalCheck(column, row, turn) == true){
                    flipDisk(column, row, turn);
                    turn = - turn;	
                    diskCount();
                    
                    update(getGraphics());
                    
                    
                    try {
                      Thread.sleep(500);
                    } catch (Exception excep){
                    }
                }
        }

        if (turn == WHITE) {
                if (placeLegalCheck(column, row, turn) == true){
                    flipDisk(column, row, turn);
                    turn = - turn;	
                    diskCount();
                    
                    update(getGraphics());
	
                    try {
                      Thread.sleep(500);
                    } catch (Exception excep){
                    }
                }
        }
    return true;
    }
    
    
    
    void displayCount(Graphics g){
        g.setColor(Color.white);
        g.fill3DRect(WIDTH+15, 130, 30,20, false); 
        g.fill3DRect(WIDTH+15, 190, 30,20, false); 
        g.fill3DRect(WIDTH+5, 160, 20,20, true);
        g.setColor(Color.black);
        g.fill3DRect(WIDTH+5, 100, 20,20, true);
        
        
        g.drawString(Integer.toString(counter_black), WIDTH+20, 145); 
        g.drawString(Integer.toString(counter_white), WIDTH+20, 205);
        g.setColor(Color.white);
        g.drawString(player1, WIDTH+30, 115);		
        g.drawString(player2, WIDTH+30, 175);
    }
    
    void showTurn(Graphics g){
    	
       	
        String comment = "'s turn";		

        g.setColor(Color.WHITE);
        if (turn == BLACK) {
            g.drawString(player1 + comment, WIDTH/2, WIDTH + 35);
            g.setColor(Color.black);
           
        } else {
            g.drawString(player2 + comment, WIDTH/2, WIDTH + 35);
            g.setColor(Color.white);
            
        }

        g.fill3DRect(WIDTH/2 - 20, WIDTH+20, 20, 20, true);
    }
    
    
    void diskCount() {
        counter_black = 0;
        counter_white = 0;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(stone[i][j] == BLACK) counter_black++;
                if(stone[i][j] == WHITE) counter_white++;
            }
        }

        if (counter_black + counter_white == 64) {
            terminateGame();
        }
    }
    
    public void terminateGame(){
        String winnr = new String("");
        int winnrid = 0 ;
        int winnerScore = 0;
        int loserid = 0;
        if (counter_black > counter_white) {
            winnr = player1;
            winnrid = userid1;
            loserid = userid2;
            winnerScore = counter_black;
        } else if (counter_black < counter_white) {
            winnr = player2;
            winnrid = userid2;
            loserid = userid1;
            winnerScore = counter_black;
        } else {
            
        }
        WinnerN wn = new WinnerN(winnr , winnrid , winnerScore , loserid);
        wn.setVisible(true);
        this.setVisible(false);
        
    }
    
    public boolean legalCheck(int turn) {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(placeLegalCheck(i, j, turn)){
                    return true;
                }
            }
            
        }
        return false;
    }
    
    public boolean placeLegalCheck(int column, int row, int color){
      
        
        int i, j;

        for (i = 0; i < 8; i++){
          direction[i] = false;
        }
        
        if(stone[column][row] != 0) {

          return false;
        }
        
        else { 

            if (column > 1 && stone[column-1][row] == -color) {
                for (i = column-2; i > 0 && stone[i][row] == -color; i--);
                if (stone[i][row] == color) {
                    
                    direction[LEFT] = true;
                }
            }

            if (column < 6 && stone[column+1][row] == -color) {
                for (i = column+2; i < 7 && stone[i][row] == -color; i++);
                if (stone[i][row] == color) {

                    direction[RIGHT] = true;
                }
            } 

            if (row > 1 && stone[column][row-1] == -color) {
                for (j = row-2; j > 0 && stone[column][j] == -color; j--);
                if (stone[column][j] == color) {

                    direction[UPPER] = true;
                }
            }

            if (row < 6 && stone[column][row+1] == -color) {
                for (j = row+2; j < 7 && stone[column][j] == -color; j++);
                if (stone[column][j] == color) {
                  
                    direction[LOWER] = true;
                }
            }

            if (column > 1 && row > 1 && stone[column-1][row-1] == -color) {
                for (i = column-2, j = row-2; i > 0 && j > 0 
                     && stone[i][j] == -color; i--, j--);
                if (stone[i][j] == color) {
                   
                    direction[UPPERLEFT] = true;
                }
            }

            if (column < 6 && row > 1 && stone[column+1][row-1] == -color) {
                for (i = column+2, j = row-2; i < 7 && j > 0 
                     && stone[i][j] == -color; i++, j--);
                if (stone[i][j] == color) {
                 
                  direction[UPPERRIGHT] = true;
                }
            }

            if (column < 6 && row < 6 && stone[column+1][row+1] == -color) {
                for (i = column+2, j = row+2; i < 7 && j < 7 
                     && stone[i][j] == -color; i++, j++);
                if (stone[i][j] == color) {
            
                  direction[LOWERRIGHT] = true;
                }
            }
      
            if (column > 1 && row < 6 && stone[column-1][row+1] == -color) {
                for (i = column-2, j = row+2; i > 0 && j < 7 
                     && stone[i][j] == -color; i--, j++);
                if (stone[i][j] == color) {
                 
                  direction[LOWERLEFT] = true;
                }
            } 

   
            for (i = 0; i < 8; i++){
                if (direction[i] == true){
                  return true;
                }
            }

            return false;
        }
    }
    int undo[][];
    public void flipDisk(int column, int row, int color) {
        undo = new int[8][8];
        stone[column][row] = color;
        undo[column][row] = 999;
        
        int i,j;

        if (direction[LEFT] == true){

            for (i = column-1; stone[i][row] != color; i--){
              stone[i][row] = - stone[i][row];
              undo[i][row] = 1;
            }
        }
   
        if (direction[RIGHT] == true){

            for (i = column + 1; stone[i][row] != color; i++){
              stone[i][row] = - stone[i][row];
              undo[i][row] = 1;
            }
        }

        if (direction[UPPER] == true){
 
            for (j = row - 1; stone[column][j] != color; j--){
              stone[column][j] = - stone[column][j];
              undo[column][j] = 1;
            }
        }

        if (direction[LOWER] == true){

            for (j = row + 1; stone[column][j] != color; j++){
              stone[column][j] = - stone[column][j];
              undo[column][j] = 1;
            }
        }
 
        if (direction[UPPERLEFT] == true){

            for (i = column-1, j = row-1; stone[i][j] != color; i--, j--){
              stone[i][j] = - stone[i][j];
              undo[i][j] = 1;
            }
        }

        if (direction[UPPERRIGHT] == true){

            for (i = column+1, j = row-1; stone[i][j] != color; i++, j--){
              stone[i][j] = - stone[i][j];
              undo[i][j] = 1;
            }
        }

        if (direction[LOWERRIGHT] == true){

            for (i = column+1, j = row+1; stone[i][j] != color; i++, j++){
              stone[i][j] = - stone[i][j];
              undo[i][j] = 1;
            }
        }

        if (direction[LOWERLEFT] == true){
 
            for (i = column-1, j = row+1; stone[i][j] != color; i--, j++){
              stone[i][j] = - stone[i][j];
              undo[i][j] = 1;
            }
        }
        
    }
}


