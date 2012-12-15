
package masta.agents.animals.humans;

import java.awt.Color;

import masta.StockableThing;
import masta.agents.AAgent;
import masta.agents.animals.Animal;
import masta.agents.animals.AnimalBody;
import masta.agents.animals.sheep.Sheep;
import masta.agents.things.Hut;

import edu.turtlekit2.kernel.agents.Turtle;


public class Human extends Animal
{

	private static final long serialVersionUID = 2274910957067261405L;
	
	protected HumanBody body;
	protected HumanMind mind;
	
	protected Hut hut = null;
	protected Job current_job = Job.NONE;

	protected float hunter_level = 1;	
	protected float gatherer_level = 1;	
	protected float woodcutter_level = 1;
	
	//*************************************************************************
	//	CONSTRUCTORS
	//*************************************************************************

	public Human()
	{
		super();
	}

	public void setup()
	{
		super.setup();
		this.setColor(Color.pink);
		this.playRole("Human");

		this.body = new HumanBody(this, 1000, 30);
		this.mind = new HumanMind(this);
		
		this.body.setVision(100f);
		this.fertility_rate = 0.0005f;
		
		this.setDimension(3, 7);
	} 
	
	//*************************************************************************
	//	METHODS
	//*************************************************************************

	public void update()
	{
		this.eat();
		if(this.hut == null)
		{
			if(!this.mind.lookForHut())
				this.wiggle();
		}
		else if(this.getBody().isHeavy())
		{
			this.setJob(Job.RETURN2HUT);
		}
		else
		{
			switch(current_job)
			{
			case HUNTER:
				this.hunterUpdate();
				break;
			case GATHERER:
				this.gathererUpdate();
				break;
			case WOODCUTTER:
				this.woodcutterUpdate();
				break;
			case RETURN2HUT:
				this.return2hutUpdate();
				break;
			default:
				this.mind.findJob();
				break;
			}
		}
			
	}
	
	protected void hunterUpdate()
	{
		// look for meat
		this.setColor(Color.red);
		Animal prey = this.mind.locatePrey();
		if (prey != null)
		{
			int distance = this.distance(prey);
			
			if(distance <= hunter_level)
			{
				this.getBody().incrStock(
								StockableThing.MEAT,
								prey.getEnergie()
								);
				prey.die();
			}
			else
			{
				if(Math.random() < 0.1)
					wiggle();
				else
				{
					setHeading(towards(prey.xcor(),prey.ycor()));
					this.fd((int)this.getSpeed());
				}
			}
		}
		else	
		{
			this.wiggle();
		}
	}
	
	protected void gathererUpdate()
	{
		// look for fruits
		this.setColor(Color.pink);
		this.goTowardsPatchVar("berry");
		float berry_level = (float)this.smell("berry");
		if(berry_level > 0)
		{
			this.getBody().incrStock(StockableThing.FRUIT, berry_level);
			this.incrementPatchVariableAt("berry", -berry_level, 0, 0);
		}
	}

	protected void woodcutterUpdate()
	{
		// look for wood
		this.setColor(Color.green);
		this.goTowardsPatchVar("wood");
		float wood_level = (float)this.smell("wood");
		if(wood_level > 0)
		{
			this.getBody().incrStock(StockableThing.WOOD, wood_level);
			this.incrementPatchVariableAt("wood", -wood_level, 0, 0);
		}
	}
	
	protected void return2hutUpdate()
	{
		if(this.distance(this.getHut()) < 10)
		{
			//this.shareResources();
			this.setJob(Job.NONE);
		}
		this.setHeading(this.towards(this.getHut().x, this.getHut().y));
		this.fd();
	}
	
	protected void eat()
	{
		if(this.getBody().getFiber() > this.getBody().getProtein())
			this.getBody().eatFruit();
		else
			this.getBody().eatMeat();
	}
	
	protected void shareResources()
	{
		float qty_min = 300f;
		int thing = StockableThing.MEAT;
		if(this.getBody().getStock(thing) > qty_min)
		{
			this.getHut().incrStock(thing, this.getBody().getStock(thing) - qty_min);
			this.getBody().setStock(thing, qty_min);
		}
		
		thing = StockableThing.FRUIT;
		if(this.getBody().getStock(thing) > qty_min)
		{
			this.getHut().incrStock(thing, this.getBody().getStock(thing) - qty_min);
			this.getBody().setStock(thing, qty_min);
		}
		
		thing = StockableThing.WOOD;
		this.getHut().incrStock(thing, this.getBody().getStock(thing));
		this.getBody().setStock(thing, 0f);
	}

	
	//******************************************************************
	//	GETTERS / SETTERS
	//******************************************************************

	public Job getJob()
	{
		return this.current_job;
	}
	
	protected void setJob(Job job)
	{
		this.current_job = job;
	}
	
	public Hut getHut()
	{
		return this.hut;
	}
	
	protected void setHut(Hut hut)
	{
		this.hut = hut;
	}
	
	//******************************************************************
	//	OVERRIDE METHODS
	//******************************************************************


	@Override
	protected HumanBody getBody() {
		return this.body;
	}
	
	@Override
	public void die()
	{
		if(this.hut != null)
			this.hut.removeAnInhabitant(this);
		
		super.die();
	}
	
	//******************************************************************
	//	PRIVATE CLASS
	//******************************************************************
	
	protected enum Job
	{
		NONE, GATHERER, HUNTER, WOODCUTTER, RETURN2HUT;
	}
}










