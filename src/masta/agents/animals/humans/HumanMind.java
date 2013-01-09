package masta.agents.animals.humans;

import java.awt.Color;

import edu.turtlekit2.kernel.agents.Turtle;
import masta.Resource;
import masta.agents.AAgent;
import masta.agents.animals.Animal;
import masta.agents.animals.sheep.Sheep;
import masta.agents.things.Hut;

public class HumanMind {

	
	//*************************************************************************
	//	CONSTRUCTORS
	//*************************************************************************

	public HumanMind(Human agent)
	{
		this.agent = agent;
	}

	
	//*************************************************************************
	//	METHODS
	//*************************************************************************

	protected void findJob()
	{	
		this.agent.setJob(this.agent.getHut().giveJob(this.agent));					
	}
	
	protected Animal locatePrey()
	{			
		return (Animal)locateAgent("Sheep");
	}
	
	protected AAgent locateAgent(String role)
	{
		AAgent agent = null;
		
		agent = locateAgent(role, 0,0);
		if(agent != null)
			return agent;
		
		for(int r=1; r<= (int)this.agent.getVision(); ++r)
		{
			for(int x=-r; x<=r; x++)
			{
				agent = locateAgent(role, x,r);
				if(agent != null)
					return agent;
				agent = locateAgent(role, x,-r);
				if(agent != null)
					return agent;
				agent = locateAgent(role, r,x);
				if(agent != null)
					return agent;
				agent = locateAgent(role, -r,x);
				if(agent != null)
					return agent;
			}
		}
				
		return null;
	}
	
	protected AAgent locateAgent(String role, int x, int y)
	{
		AAgent agent = null;

		Turtle[] tur = this.agent.turtlesAt(x,y);
		if (tur != null) //instead of "instanceof". So prey can be another java class
			for(Turtle t: tur)
				if( t.isPlayingRole(role))
				{
					agent = (AAgent)t;
					return agent;
				}
		
		return null;
	}
	
	protected boolean testHut(Hut h)
	{
		if(h != null && h.addAnInhabitant(this.agent))
		{
			this.agent.setHut(h);
			return true;
		}
		else
			return false;
	}
	
	//*************************************************************************
	//	ATTRIBUTS
	//*************************************************************************

	protected Human agent;
}
