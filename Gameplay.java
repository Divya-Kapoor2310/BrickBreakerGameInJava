package brickBreaker;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.util.Timer;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Gameplay extends JPanel implements KeyListener, ActionListener {
	private boolean play=false;
	private int score=0;
	
	private int totalBricks=21;
	
	private Timer timer;
	private int delay=15;
	
	private int playerX=310;
	
	private int ballposX=120;
	private int ballposY=350;
	private int ballXdir=-1;
	private int ballYdir=-2;
	private MapGenerator map;
	
	public Gameplay() {
		map=new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer=new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1,1,792,692);
		
		//drawing map
		map.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 692);
		g.fillRect(0, 0, 792, 3);
		g.fillRect(782, 0, 3, 692);
		
		//scores
		g.setColor(Color.pink);
        g.setFont(new Font("serif", Font.BOLD,30));
        g.drawString("Score: "+score, 600,35);
		
		//slider
		g.setColor(Color.green);
		g.fillRect(playerX, 650, 100, 25);
		
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 25, 25);
		
		if(totalBricks<=0) {
			play=false;
        	ballXdir=0;
        	ballYdir=0;
        	g.setColor(Color.RED);
        	g.setFont(new Font("Tahoma", Font.BOLD,35));
            g.drawString("YOU WON THE GAME", 260,300);
            
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Press Enter to Restart:", 350,350);
		}
		
		if(ballposY>670) {
        	play=false;
        	ballXdir=0;
        	ballYdir=0;
        	g.setColor(Color.RED);
        	g.setFont(new Font("Tahoma", Font.BOLD,35));
            g.drawString("GAME OVER, SCORES:"+score, 190,300);
            
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Press Enter to Restart:", 250,350);
        }
        
		
		g.dispose();
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		
		if(play) {
			if(new Rectangle(ballposX,ballposY,25,25).intersects(new Rectangle(playerX,650,100,8))) {
				ballYdir=-ballYdir;
			}
			A: for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX=j*map.brickWidth +80;
						int brickY=i*map.brickHeight +50;
						int brickwidth=map.brickWidth;
						int brickheight=map.brickHeight;
						
						
						//int brickWidth = 0;
						Rectangle rect= new Rectangle(brickX, brickY, brickwidth, brickheight);
						Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect=rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score+=5;
							
							if(ballposX+19<=brickRect.x||ballposX+1>=brickRect.x+brickRect.width) {
								ballXdir=-ballXdir;
							}else {
								ballYdir=-ballYdir;
							}
							
							break A;
						}
						
					}
				}
			}
		}
		if(play) {
			ballposX+=ballXdir;
			ballposY+=ballYdir;
			if(ballposX<0) {
				ballXdir=-ballXdir;
				
			}
			if(ballposY<0) {
				ballYdir=-ballYdir;
				
			}
			if(ballposX>770) {
				ballXdir=-ballXdir;
				
			}
		}
		repaint();
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}


	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX>=700) {
				playerX=700;
			}else {
				moveRight();
			}
		
		}
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
        	if(playerX<10) {
				playerX=10;
			}else {
				moveLeft();
			}
			
		}
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        	if(!play) {
        		play=true;
        		ballposX=120;
        		ballposY=350;
        		ballXdir=-1;
        		ballYdir=-2;
        		playerX=310;
        		score=0;
        		totalBricks=21;
        		map=new MapGenerator(3,7);
        		repaint();
        	}
        }
		
	}
	public void moveRight() {
		play=true;
		playerX+=20;
	}
	public void moveLeft() {
		play=true;
		playerX-=20;
	}

	

}
