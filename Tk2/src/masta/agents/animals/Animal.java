package masta.agents.animals;

import utils.CardinalDirection;
import masta.agents.AAgent;
import masta.agents.MovableAgent;
import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.kernel.environment.Patch;



public abstract class Animal extends MovableAgent {

	private static final long serialVersionUID = -1117320818063161817L;
		
	protected float fertility_rate = 0.f;
	protected float max_sea_depth_without_die = 0.1f;
	protected int old_xcor, old_ycor;

	public Animal()
	{
		super();
	}
	
	public void setup()
	{
		super.setup();
		this.playRole("Animal");
	}
	
	//*************************************************************************
	//	PUBLIC METHODS
	//*************************************************************************
	
	public void fd()
	{
		this.getBody().fd();
	}
	
	public void wiggle()
	{
		turnRight(Math.random()*45);
		turnLeft(Math.random()*45);
		this.fd();
	}
	
	public void fd(int nb)
	{
		this.getBody().useEnergie(nb);
		super.fd(nb);
	}
	
	public void bk(int nb)
	{
		this.fd(-nb);
	}
	
	public void goTowardsPatchVar(String patch_var)
	{
		int vision = (int)this.getVision();
		if( vision % 2 != 0)
			vision += 1;
		
		int demi_vision = vision/2;
		
		int var_qty = 0;
		int[] dir_qty = new int[9];
		
		// compute value for each direction
		for(int x=-vision; x<=vision; ++x)
			for(int y=-vision; y<=vision; ++y)
				{
					var_qty = (int)this.getPatchAt(x, y).smell(patch_var);
					
					if(x<=0 && y>=0) // NW
						dir_qty[CardinalDirection.NW] += var_qty;
					else if(x>=0 && y>=0) // NE
						dir_qty[CardinalDirection.NE] += var_qty;
					else if(x>=0 && y<=0) // SE
						dir_qty[CardinalDirection.SE] += var_qty;
					else if(x<=0 && y<=0) // SW
						dir_qty[CardinalDirection.SW] += var_qty;
					
					// NORTH
					if(x>=-demi_vision && x<=demi_vision && y>=0)
						dir_qty[CardinalDirection.NORTH] += var_qty;
					
					// SOUTH
					if(x>=-demi_vision && x<=demi_vision && y<=0) 
						dir_qty[CardinalDirection.SOUTH] += var_qty;
					
					// EAST
					if(y>=-demi_vision && y<=demi_vision && x>=0) 
						dir_qty[CardinalDirection.EAST] += var_qty;
					
					// WEST
					if(y>=-demi_vision && y<=demi_vision && x<=0) 
						dir_qty[CardinalDirection.WEST] += var_qty;
					
					// NONE
					if(x>=-demi_vision && x<=demi_vision 
							&& y>=-demi_vision && y<=demi_vision)
						dir_qty[CardinalDirection.NONE] += var_qty;
				}
		
		// FIND MAX @TODO add proba system
		int angle_dir = 135;
		var_qty = dir_qty[CardinalDirection.NW];
		if(dir_qty[CardinalDirection.NE] > var_qty)
		{
			angle_dir = 45;
			var_qty = dir_qty[CardinalDirection.NE];
		}
		if(dir_qty[CardinalDirection.SE] > var_qty)
		{
			angle_dir = -45;
			var_qty = dir_qty[CardinalDirection.SE];
		}
		if(dir_qty[CardinalDirection.SW] > var_qty)
		{
			angle_dir = -135;
			var_qty = dir_qty[CardinalDirection.SW];
		}
		if(dir_qty[CardinalDirection.NORTH] > var_qty)
		{
			angle_dir = 90;
			var_qty = dir_qty[CardinalDirection.NORTH];
		}
		if(dir_qty[CardinalDirection.EAST] > var_qty)
		{
			angle_dir = 0;
			var_qty = dir_qty[CardinalDirection.EAST];
		}
		if(dir_qty[CardinalDirection.SOUTH] > var_qty)
		{
			angle_dir = -90;
			var_qty = dir_qty[CardinalDirection.SOUTH];
		}
		if(dir_qty[CardinalDirection.WEST] > var_qty)
		{
			angle_dir = 180;
			var_qty = dir_qty[CardinalDirection.WEST];
		}
		
		if(dir_qty[CardinalDirection.NONE] > var_qty)
		{
			this.wiggle();
			/*Patch p = this.getPatchWithMaxOf(patch_var, demi_vision);
			angle_dir = (int)this.towards(p.x, p.y);*/
		}
		else
		{
			this.setHeading(angle_dir);
			this.fd((int)this.getSpeed());
		}
	}
	
	public int distance(Turtle t)
	{
		return Math.abs(this.xcor() - t.xcor()) 
				+ Math.abs(this.ycor() - t.ycor());
	}
	
	//*************************************************************************
	//	GETTERS/SETTERS
	//*************************************************************************

	public float getEnergie()
	{ 
		return this.getBody().getEnergie(); 
	}
	
	public float getSpeed()
	{
		return this.getBody().getSpeed();
	}
	
	public float getVision()
	{
		return this.getBody().getVision();
	}
	
	//*************************************************************************
	//	PRIVATE METHODS
	//*************************************************************************

	protected abstract AnimalBody getBody();
	
	protected void reproduce()
	{
		if(Math.random() < this.fertility_rate)
		{
			Animal a;
			try {
				a = (Animal)this.getClass().newInstance();
				this.createTurtle(a);
				this.getBody().useEnergie(a.getEnergie());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	protected void beforeUpdateTest()
	{
		this.getBody().update();
		if(this.smell("sea") > this.max_sea_depth_without_die)
			this.die();
		
		this.old_xcor = this.xcor();
		this.old_ycor = this.ycor();
		this.reproduce();
	}
	
	protected void afterUpdateTest()
	{	
		if(this.smell("sea") > 0)
			this.moveTo(this.old_xcor, this.old_ycor);
	}
	
}


