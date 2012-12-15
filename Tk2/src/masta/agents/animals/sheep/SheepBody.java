package masta.agents.animals.sheep;

import edu.turtlekit2.kernel.environment.Patch;
import masta.agents.animals.Animal;
import masta.agents.animals.AnimalBody;
import masta.agents.animals.humans.Human;

class SheepBody extends AnimalBody 
{
	//*************************************************************************
	//	CONSTRUCTORS 
	//*************************************************************************

	public SheepBody(Sheep agent, int energie, int mounthful_qty) {
		super();
		this.setAgent(agent);
		this.energie = energie;
		this.mounthful_qty = mounthful_qty;
	}
	
	//*************************************************************************
	//	METHODS
	//*************************************************************************

	public void eat(Patch p, String var)
	{
		float qty = Math.min((float)p.smell(var), this.getMounthfulQty());
		p.incrementPatchVariable(var, -qty);
		this.energie += qty;
	}
	
	//*************************************************************************
	//	GETTERS/SETTERS 
	//*************************************************************************

	@Override
	public float useEnergie(float qty) {
		return (this.energie -= qty);
	}

	@Override
	public float getEnergie() {
		return this.energie;
	}

	@Override
	public float getMounthfulQty() {
		return this.mounthful_qty;
	}

	@Override
	protected Sheep getAgent() {
		return this.agent;
	}
	
	protected void setAgent(Sheep agent) {
		this.agent = agent;
	}
	
	//*************************************************************************
	//	ATTRIBUTS
	//*************************************************************************

	private Sheep agent;
	private float energie;
	private float mounthful_qty;
}
