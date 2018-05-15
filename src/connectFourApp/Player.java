package connectFourApp;

public abstract class Player {

	protected boolean isActive;
	protected int score;
	protected String name;
	
	public int getScore() {
		return score;
	}

	public void addScore() {
		this.score += 1;
	}

	public String getName() {
		return name;
	}

	public boolean isActive() 
	{
		return this.isActive;
	}
	
	public void setActive(boolean turnChange) 
	{
		this.isActive = turnChange;
	}
}
