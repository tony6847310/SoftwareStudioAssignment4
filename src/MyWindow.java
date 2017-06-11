import java.awt.Color;

import javax.swing.*;

public class MyWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	private Typing tp;
	private GameStage gs;
	
	public MyWindow() {
		//initialize settings and create two thread, one for right side, one for the left
		//start both of them and add them to myWindow
		setLayout(null);
		setTitle("Human-OCR");
		setSize(1000,500);
		setLocation(100,100);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gs = new GameStage();
		gs.setBounds(400, 0, 600, 500);
		gs.setBackground(Color.decode("#4dd2ff"));
		gs.start();

		tp = new Typing(gs);
		tp.setLayout(null);
		tp.setBounds(0, 0, 400, 500);
		tp.setBackground(Color.decode("#b3ecff"));
		tp.start();
		
		add(tp);
		add(gs);
	}
}
