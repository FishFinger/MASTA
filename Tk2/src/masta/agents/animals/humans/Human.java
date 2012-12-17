
package masta.agents.animals.humans;

import java.awt.Color;
import java.util.LinkedList;

import javax.media.j3d.Link;

import masta.Job;
import masta.Resource;
import masta.agents.AAgent;
import masta.agents.animals.Animal;
import masta.agents.animals.AnimalBody;
import masta.agents.animals.sheep.Sheep;
import masta.agents.things.Hut;

import edu.turtlekit2.kernel.agents.Turtle;


public class Human extends Animal
{
	
	public static LinkedList<Human> allHumans = new LinkedList<>();
	
	//*************************************************************************
	//	CONSTRUCTORS
	//*************************************************************************

	public Human()
	{
		super();
		allHumans.add(this);
	}

	public void setup()
	{
		super.setup();
		this.setupJobProp();
		this.setColor(Color.pink);
		this.playRole("Human");

		this.body = new HumanBody(this, 1000, 30);
		this.mind = new HumanMind(this);
		
		this.body.setVision(100f);
		this.fertility_rate = 0.0005f;
		
		this.setDimension(5, 12);
	} 
	
	protected void setupJobProp()
	{
		for(Job j: Job.values())
		{
			this.job_exp[j.ordinal()] = 1f;
			this.job_exp_step[j.ordinal()] = 0.01f;
		}
		
		this.job_exp_step[Job.HUNTER.ordinal()] = 0.1f;

	}
	
	//*************************************************************************
	//	METHODS
	//*************************************************************************

	public void update()
	{
		//this.eat();
		
		if(job != null)
			this.setColor(this.job.getColor());
		
		if(this.getBody().isHeavy())
			this.setState(State.RETURN2HUT);
		
		switch(state)
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
	
	protected void hunterUpdate()
	{
		// look for meat
		Animal prey = this.mind.locatePrey();
		if (prey != null)
		{
			int distance = this.distance(prey);
			
			if(distance <= this.job_exp[Job.HUNTER.ordinal()])
			{
				this.getBody().incrStock(
								Resource.MEAT,
								Math.min(
										prey.getEnergie(),
										this.body.getMaxPortableWeight()
										)
								);
				prey.die();
				this.incrExp(Job.HUNTER);
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
		float berry_qty = (float)this.smell("berry");
		if(berry_qty > 0f)
		{
			this.getBody().incrStock(Resource.FRUIT, this.job_exp[Job.GATHERER.ordinal()]);
			this.incrementPatchVariableAt("berry", -this.job_exp[Job.GATHERER.ordinal()], 0, 0);
			this.incrExp(Job.GATHERER);
		}
		else
		{
			this.goTowardsPatchVar("berry");
		}
	}

	protected void woodcutterUpdate()
	{
		// look for wood
		float wood_qty = (float)this.smell("wood");
		if(wood_qty > 0f)
		{
			this.getBody().incrStock(Resource.WOOD, this.job_exp[Job.WOODCUTTER.ordinal()]);
			this.incrementPatchVariableAt("wood", -this.job_exp[Job.WOODCUTTER.ordinal()], 0, 0);
			this.incrExp(Job.WOODCUTTER);
		}
		else
		{
			this.goTowardsPatchVar("wood");
		}
	}
	
	protected void return2hutUpdate()
	{
		if(this.distance(this.getHut()) < 10)
		{
			this.dropResources();
			this.setJob(this.getHut().giveJob(this));
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
	
	protected void dropResources()
	{
		for(Resource r: Resource.values())
		{
			this.getHut().incrStock(r, this.body.getStock(r));
			this.body.setStock(r, 0);
		}
	}
	
	//******************************************************************
	//	GETTERS / SETTERS
	//******************************************************************

	public State getState()
	{
		return this.state;
	}
	
	protected void setState(State state)
	{
		this.state = state;
	}
	
	public Job getJob()
	{
		return this.job;
	}
	
	protected void setJob(Job job)
	{
		this.job = job;
		switch(job)
		{
		case GATHERER:
			this.state = State.GATHERER;
			break;
		case HUNTER:
			this.state = State.HUNTER;
			break;
		case WOODCUTTER:
			this.state = State.WOODCUTTER;
			break;
		}
	}
	
	public Hut getHut()
	{
		return this.hut;
	}
	
	public void setHut(Hut hut)
	{
		this.hut = hut;
	}
	
	public float getExp(Job job)
	{
		return this.job_exp[job.ordinal()];
	}
	
	protected void incrExp(Job job)
	{
		this.job_exp[job.ordinal()] += this.job_exp_step[job.ordinal()];
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
		// humans never die
		allHumans.remove(this);
		if(this.hut != null)
			this.hut.removeAnInhabitant(this);
		
		super.die();
	}
	
	@Override
	protected void reproduce()
	{
	}
	
	//******************************************************************
	//	ATTRIBUTS
	//******************************************************************

	private static final long serialVersionUID = 2274910957067261405L;
	
	protected HumanBody body;
	protected HumanMind mind;
	
	protected Hut hut = null;
	protected State state = State.DEFAULT;
	protected Job job = null;
	
	protected float job_exp[] = new float[Job.values().length];
	protected float job_exp_step[] = new float[Job.values().length];
	
	//******************************************************************
	//	PRIVATE CLASS
	//******************************************************************
	
	protected enum State
	{
		DEFAULT, GATHERER, HUNTER, WOODCUTTER, RETURN2HUT;
	}
}










