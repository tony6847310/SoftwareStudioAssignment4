import java.awt.image.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.imageio.*;

public class Typing extends JPanel implements KeyListener, Runnable{
	private static final long serialVersionUID = 1L;
	private Thread gameThread;
	private Words words;
	private FileWriter fw;
	private BufferedWriter bw;
	private JTextField input;
	private int selected1, selected2;
	private int wordsX, wordsY;
	private BufferedImage leftWord, rightWord; 
	private String ans, leftAns, rightAns;
	private GameStage gs;
	private boolean isEnding = false;
	
	public Typing(GameStage gamestage) {
		//initialize each parameter
		//pass in a GameStage object to allow me to modify the gamestage panel here
		gs = gamestage;
		words = new Words();
		input = new JTextField(25);
		
		input.selectAll();
		input.setBounds(0, 430, 400, 30);
		input.addKeyListener(this);
		this.add(input);
		//create a bufferedwriter
		File file = new File ("output.txt");
		try{
			if(!file.exists()) {
				file.createNewFile() ;
			}
			fw = new FileWriter(file.getAbsolutePath()) ;
			bw = new BufferedWriter(fw);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		//randomly generate a number to represent the chosen image
		Random r = new Random();
		try {
		wordsX = r.nextInt(50) + 1;
		selected1 = r.nextInt(words.known.size());
		//read the chosen image
		leftWord = ImageIO.read(new File("img/known/" + words.knownKeys[selected1] ));
		selected2 = r.nextInt(words.unknown.size());
		rightWord = ImageIO.read(new File("img/unknown/" + words.unknownKeys[selected2] ));
		
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		Thread cur = Thread.currentThread();
		//check if current is equal to this
		while (cur == gameThread) {
			
			this.repaint();
			//stop when game has ended
			if(this.isEnding){
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!isEnding){
			if(this.gs.getScore() == 12) input.setEditable(false) ;
			this.wordsY++; // make the two words slowly moves down
			if (wordsY >= 350)
				wordsY = 0; //back to top
			g.drawImage(leftWord, wordsX, wordsY, 100, 50, null);
			g.drawImage(rightWord, wordsX + 110, wordsY, 100, 50, null);
		}else {
			//
		}
	}
	@Override
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			ans = input.getText(); 
			//separate the user's input string into two part
			//first part should be matched with the known words
			for (int i = 0 ;i < ans.length(); i++ ){ 
				char c = ans.charAt(i);
				//separate it when the space character shows up
				if(c == ' '){
					leftAns = ans.substring(0, i) ;  
					rightAns = ans.substring(i + 1, ans.length() ); 
					break ;
				}	
			}
			//if the left part of answer is correct, assume right part is correct too.
			if(leftAns.equals( words.known.get(words.knownKeys[selected1]))){
				//write down the answer and the unknown image path
				try{
					bw.write(words.unknownKeys[selected2]);   
					bw.write(" ");
					bw.write(rightAns); 
					bw.newLine();
					//if the score is equal to the goal, end the game
					if(this.gs.getScore() == this.gs.goal){
						this.gs.isEnding = true;
						this.isEnding = true;
						bw.close(); 
					}
				}
				catch(IOException e) {
					e.printStackTrace();
				}
				try {
					//randomly read another two image
					Random r = new Random();
					this.wordsY = 0 ;  
					wordsX = r.nextInt(50) + 1;
					selected1 = r.nextInt(words.known.size());
					leftWord = ImageIO.read(new File("img/known/" + words.knownKeys[selected1] ));
					selected2 = r.nextInt(words.unknown.size());
					rightWord = ImageIO.read(new File("img/unknown/" + words.unknownKeys[selected2] ));
					this.gs.addScore(1);
					this.gs.delay = 0; //make the duck move forward, by reseting the delay
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			input.setText("");
		}
	}
	@Override
	public void keyReleased(KeyEvent ke) {
		//
	}
	@Override
	public void keyTyped(KeyEvent ke) {
		//
	}
	
	public void start() {
		//create an Thread of this and start it
		if(gameThread == null){
			gameThread = new Thread(this);
			gameThread.start();
		}
	}
}
