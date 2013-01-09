package masta.world;

import edu.turtlekit2.kernel.agents.Observer;
import edu.turtlekit2.kernel.environment.Patch;

public class WorldCtrl extends Observer
{
	public void setup()
	{
		super.setup();
		World.getInstance().setup();
	}
	
	public void watch()
	{
		if(++step % 50 == 0)
		{
			Patch p;
			for(int i=0;i<world.getWidth();i++)
				for(int j=0;j<world.getHeight();j++)
				{
					p = world.grid[i][j];
					if(p.smell("sea") <= 0)
						p.incrementPatchVariable("grass", 0.1);
					
					if (p.smell("wood") > 0)
						p.incrementPatchVariable("wood", 0.1);
					  
					if(p.smell("berry") > 0)
						p.incrementPatchVariable("berry", 0.1);

				}
		}
	}
	
	private int step = 0;
	private World world = World.getInstance();
}
