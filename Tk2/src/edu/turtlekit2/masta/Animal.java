package edu.turtlekit2.masta;

import edu.turtlekit2.kernel.agents.Turtle;


public abstract class Animal extends Turtle {

	protected int energie;
	protected int visionRadius;
	protected int speed = 1;
	protected float reproduction_rate;

	public Animal()
	{
		super();
	}
	
	public Animal(String init_method)
	{
		super(init_method);
	}

	public int getEnergie()
	{
		return this.energie;
	}
	
	public void wiggle()
	{
		turnRight(Math.random()*45);
		turnLeft(Math.random()*45);
		fd(speed);
	}
	
	public void fd(int nb)
	{
		this.energie -= nb*(this.energie/100);
		super.fd(nb);
	}
	
	public void bk(int nb)
	{
		this.energie -= nb*(this.energie/100);
		super.bk(nb);
	}
}
