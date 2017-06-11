import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class GameStage extends AbstractGameStage implements Runnable{
	private static final long serialVersionUID = 1L;
	
	private int bgCurrentX;
	private int  ballCurrentX, ballCurrentY;
	private int  duckCurrentX, duckCurrentY, duckAnchorY;
	private String duckDirection;
	private BufferedImage background = null;
	private BufferedImage background2 = null;
	private BufferedImage duck = null;
	private BufferedImage ball = null;
	private BufferedImage win = null;
	private Thread game = null;
	private JLabel currentScore;
	protected boolean isEnding = false;
	protected int delay = 40;
	
	public GameStage(){
		//initialize parameters
		bgCurrentX = 0;
		ballCurrentX = 600;
		ballCurrentY = 200;
		duckCurrentX = 50;
		duckCurrentY = 180;
		duckAnchorY = duckCurrentY;
		duckDirection = "up";
		
		this.setGoal(12);
		//add Score JLabel
		currentScore = new JLabel();
		currentScore.setFont(new Font(Font.DIALOG, Font.BOLD, 35));
		currentScore.setText("Score: " + this.getScore());
		add(currentScore);
		//read images
		try {
			background = ImageIO.read(new File("res/h.png"));
			background2 = ImageIO.read(new File("res/h.png"));
			duck = ImageIO.read(new File("res/duck.png"));
			ball = ImageIO.read(new File("res/b.png"));
			win = ImageIO.read(new File("res/win.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		long lastTime = System.currentTimeMillis();
		
		Thread  cur = Thread.currentThread();
		while(cur == game){
			try{
				this.repaint();
				currentScore.setText("Score: " + this.getScore());
				//check if the game has ended
				if(this.isEnding){	
					break;
				}
				
				if( delay < 60) {
					delay++;
					if(this.score <= this.goal-4){ // Near the end of game
						bgCurrentX--;
						if(this.score > this.goal-8){ // Can show the ball now 
							ballCurrentX--;
						}
					} else {
						duckCurrentX++;
					}
				}
				
				if (duckDirection == "up" && duckCurrentY > duckAnchorY+10) {
					duckDirection = "down";
				} else if (duckDirection == "down" && duckCurrentY < duckAnchorY-10){
					duckDirection = "up";
				}
					// if direction is UP, increase duckCurrentY, otherwise decrease duckCurrentY
				if (duckDirection == "up") duckCurrentY ++;
				else if( duckDirection == "down") duckCurrentY --;
				/* Game Loop End */
				lastTime = lastTime + delay;
				Thread.sleep(Math.max(0, lastTime - System.currentTimeMillis()));
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public void start(){
		//create an Thread instance of this and start it 
		if(game == null){
			game = new Thread(this);
			game.start();
		}
	}
	public void addScore(int add){
		//add score
		this.score += add;
	}
	public int getScore(){
		return this.score;
	}
	public void setGoal(int goal){
		this.goal = goal;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(!isEnding){
			g.drawImage(background, bgCurrentX, -50, null);
			g.drawImage(background2, bgCurrentX + background.getWidth(), -50, null);
			g.drawImage(ball, ballCurrentX, ballCurrentY, null);
			g.drawImage(duck, duckCurrentX, duckCurrentY, null);
		} else {
			//if the game has ended, show win image
			g.drawImage(win, 100, 100, null);
		}
	}
}
