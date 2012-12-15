package masta.agents.animals;

import edu.turtlekit2.kernel.environment.Patch;

public abstract class AnimalBody 
{	
	//*************************************************************************
	//	CONSTRUCTOR
	//*************************************************************************

	public AnimalBody()
	{}
	
	//*************************************************************************
	//	PUBLIC METHODS
	//*************************************************************************

	public void update()
	{
		if(this.useEnergie(1) <= 0)
			this.getAgent().die();
	}
	
	public void fd()
	{
		this.getAgent().fd((int)this.getSpeed());
	}
	
	
	//*************************************************************************
	//	GETTERS/SETTERS
	//*************************************************************************

	public abstract float useEnergie(float qty);
	public abstract float getEnergie();
	public abstract float getMounthfulQty();
	
	public float getVision()
	{
		return this.vision;
	}
	
	public void setVision(float vision)
	{
		this.vision = vision;
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
	
	public void setSpeed(float speed) 
	{
		this.speed = speed;
	}
	
	//*************************************************************************
	//	Protected METHODS
	//*************************************************************************

	protected abstract Animal getAgent();
	
	//*************************************************************************
	//	ATTRIBUTS
	//*************************************************************************
	
	protected float vision = 1;
	protected float speed = 1;

	
}
