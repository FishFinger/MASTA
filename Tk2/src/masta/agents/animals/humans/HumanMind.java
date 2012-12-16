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
		/*float stock_meat = this.agent.getHut().getStock(StockableThing.MEAT);
		float stock_fruit = this.agent.getHut().getStock(StockableThing.FRUIT);
		float stock_wood = this.agent.getHut().getStock(StockableThing.WOOD);
		
		if (stock_meat >= stock_fruit && stock_meat >= stock_wood)
			this.agent.setJob(Human.Job.HUNTER);
		else if(stock_fruit >= stock_meat && stock_fruit >= stock_wood)
			this.agent.setJob(Human.Job.GATHERER);
		else
			this.agent.setJob(Human.Job.WOODCUTTER);*/
		
		/*this.setColor(Color.black);
		wiggle();
		for(int r=1; r<= vision; ++r)
			for(int x=-r; x<=r; x++)
			{
				if(this.findJob(x,r))
					break;
			}*/
		
		this.agent.setJob(this.agent.getHut().giveJob());
					
	}
		
	/*protected boolean findJob(int x, int y)
	{
		if(this.locateAgent("Sheep",x,y) != null)
		{
			this.current_job = Job.HUNTER;
			return true;
		}
		
		if(this.smellAt("berry",x,y) > 0)
		{
			this.current_job = Job.GATHERER;
			return true;
		}
		
		return false;
	}*/
	
	protected boolean lookForHut()
	{
		Hut tmp_hut = (Hut)locateAgent("Hut",0,0);
		if(testHut(tmp_hut))
			return true;
		
		for(int r=1; r<= this.agent.getVision(); ++r)
		{
			for(int x=-r; x<=r; x++)
			{
				tmp_hut = (Hut)locateAgent("Hut",x,r);
				if(testHut(tmp_hut))
					return true;	
				
				tmp_hut = (Hut)locateAgent("Hut",x,-r);
				if(testHut(tmp_hut))
					return true;
				
				tmp_hut = (Hut)locateAgent("Hut",r,x);
				if(testHut(tmp_hut))
					return true;
				
				tmp_hut = (Hut)locateAgent("Hut",-r,x);
				if(testHut(tmp_hut))
					return true;
			}
		}
		
		return false;
	}
	
	protected Animal locatePrey()
	{
		Animal prey = null;
		
		prey = (Sheep)locateAgent("Sheep", 0,0);
		if(prey != null)
			return prey;
		for(int r=1; r<= (int)this.agent.getVision(); ++r)
		{
			for(int x=-r; x<=r; x++)
			{
				prey = (Sheep)locateAgent("Sheep", x,r);
				if(prey != null)
					return prey;
				prey = (Sheep)locateAgent("Sheep", x,-r);
				if(prey != null)
					return prey;
				prey = (Sheep)locateAgent("Sheep", r,x);
				if(prey != null)
					return prey;
				prey = (Sheep)locateAgent("Sheep", -r,x);
				if(prey != null)
					return prey;
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
