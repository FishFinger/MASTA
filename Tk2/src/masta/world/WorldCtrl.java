package masta.world;

import edu.turtlekit2.kernel.agents.Observer;

public class WorldCtrl extends Observer
{
	public void setup()
	{
		super.setup();
		World.getInstance().setup();
	}
}
