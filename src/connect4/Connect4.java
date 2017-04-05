
package connect4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Connect4 extends JPanel implements Runnable{

    JFrame frame = new JFrame("Connect 4");
    int timer = 0;
    int[][] board;
    int player = 1;
    boolean didWin = false;
    int playerThatWon = 0;
    int x1,x2;
    
    
    public static void main(String args[]){    
        (new Thread(new Connect4())).run();
    }
  
    public void run(){
        
        Scanner s=new Scanner(System.in);
        System.out.println("Enter the  number of rows and columns");
        x1=s.nextInt();
        x2=s.nextInt();
        
        frame.setSize(new Dimension(x1*50,x2*50));        
        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        frame.setLocationRelativeTo(null);
        board = new int[x1][x2];
        
        for(int r = 0; r<board.length; r++){
            for(int c = 0; c<board[r].length; c++) {
                board[r][c]=0;
            }
        }
        
        this.setFont(new Font("serif", Font.TRUETYPE_FONT,26));

       this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                if(!didWin) {
                    
                    System.out.println("("+e.getY()/50+" "+e.getX()/50+")");
                    
                    if(e.getY()>0) {
                        addToColumn(e.getY()/50,e.getX()/50, player);                                              
                    }
                }             
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        frame.add(this);
        this.setPreferredSize(frame.getSize());
        frame.pack();
        frame.setVisible(true);
        while(true){
            if(timer%15==0){
                this.repaint();
            }
            try{Thread.sleep(1);timer++;}catch (InterruptedException e){}
        }
    }

    public void addToColumn(int row,int column, int p){
        
        if(isAvailable(row,column))        
        {
            board[row][column] = p; 
            
            didWin = isWin(p);
            if (player == 1) {
                player = 2;
            }
            else if(player == 2){
                player = 1;
            }
        }
        
        //printing the board
        for(int i=0;i<x1;i++)
        {
            for(int j=0;j<x2;j++)
            {
                    System.out.print(board[i][j]);
            }
            System.out.println();
        }
        
    }
    
    
    public boolean isAvailable(int r, int c){
        if(board[r][c]==0) {
            return true;
        }
        else return false;
    }

    public boolean isWin(int p){
        int count = 0;
        int winNum = 4;
        
        //horizontal 4        
        for(int i = 0;i<board.length; i++){
            for(int u = 0; u<board[i].length; u++){
                if(board[i][u] == p) count+=1;
                else count = 0;
                if (count >= winNum) {
                    playerThatWon = p;
                    return true;
                }
            }
        }
        count = 0;
        
        //vertical 4
        for(int u = 0;u<board[0].length; u++){
            for(int i = 0; i<board.length; i++){
                if(board[i][u] == p) count+=1;
                else count = 0;
                if (count >= winNum) {
                    playerThatWon = p;
                    return true;
                }
            }
        }
        count = 0;
        
        //Diagnol 4
        //For Loop Row first, then Columns
        for(int i = 0;i<board.length; i++) {
            for (int u = 0; u < board[i].length; u++) {
                if(i<board.length-(winNum-1)){
                    //CAN CHECK UP
                    if(u<board[0].length-(winNum-1)){
                        //CAN CHECK RIGHT
                        if(board[i][u]==p && board[i+1][u+1]==p && board[i+2][u+2]==p && board[i+3][u+3]==p){
                            playerThatWon = p;
                            return true;
                        }
                    }
                    if(u>winNum-1){
                        //CAN CHECK LEFT
                        if(board[i][u]==p && board[i+1][u-1]==p && board[i+2][u-2]==p && board[i+3][u-3]==p){
                            playerThatWon = p;
                            return true;
                        }
                    }
                }
                if(i>winNum-1){
                    //CAN CHECK DOWN
                    if(u<board[0].length-(winNum-1)){
                        //CAN CHECK RIGHT
                        if(board[i][u]==p && board[i-1][u+1]==p && board[i-2][u+2]==p && board[i-3][u+3]==p){
                            playerThatWon = p;
                            return true;
                        }
                    }
                    if(u>winNum-1){
                        //CAN CHECK LEFT
                        if(board[i][u]==p && board[i-1][u-1]==p && board[i-2][u-2]==p && board[i-3][u-3]==p){
                            playerThatWon = p;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.WHITE);
        
        for(int r = 0; r<board.length; r++){
            for(int c = 0; c<board[r].length; c++){
                g.setColor(Color.BLACK);
                g.drawRect(c*50,r*50,50,50);
                if(board[r][c] == 1){
                    g.setColor(Color.RED);
                    g.fillOval( c * 50, r * 50, 40, 40);
                }
                if(board[r][c] == 2){
                    g.setColor(Color.YELLOW);
                    g.fillOval( c * 50, r * 50, 40, 40);
                }
            }
        }
     
        if(didWin){
            if(playerThatWon==1){
                g.setColor(Color.GREEN);
                g.drawString("Player-1 Wins", 50,50);
            }
            if(playerThatWon==2){
                g.setColor(Color.GREEN);
                g.drawString("Player-2 Wins", 50,50);
            }
        }
      
    }
}
