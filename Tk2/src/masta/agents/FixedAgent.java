package masta.agents;

public abstract class FixedAgent extends AAgent {

	public void setup()
	{
		super.setup();
		this.setHidden(true);
	}
	
	public boolean isMovable()
	{
		return false;
	}

}
