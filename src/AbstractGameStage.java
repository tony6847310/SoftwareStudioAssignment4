import javax.swing.*;

public abstract class AbstractGameStage extends JPanel{
	public int score = 0;
	public int goal;
	
	public abstract void start();
	public abstract void addScore(int add);
	public abstract int getScore();
	public abstract void setGoal(int goal);
}
