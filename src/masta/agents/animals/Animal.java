package masta.agents.animals;


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
	
	public float decrEnergie(float qty)
	{
		return this.getBody().decrEnergie(qty);
	}
	
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
		
		Patch p;	
		for(int r=1; r<= (int)this.getVision(); ++r)
		{
			for(int x=-r; x<=r; x++)
			{
				p = this.getPatchAt(x, r);
				if(p.smell(patch_var) > 1)
				{
					this.setHeading(this.towards(p.x, p.y));
					this.fd();
					return;
				}
				
				p = this.getPatchAt(x, -r);
				if(p.smell(patch_var) > 1)
				{
					this.setHeading(this.towards(p.x, p.y));
					this.fd();
					return;
				}
				
				p = this.getPatchAt(r, x);
				if(p.smell(patch_var) > 1)
				{
					this.setHeading(this.towards(p.x, p.y));
					this.fd();
					return;
				}
				
				p = this.getPatchAt(-r, x);
				if(p.smell(patch_var) > 1)
				{
					this.setHeading(this.towards(p.x, p.y));
					this.fd();
					return;
				}
				
			
			}
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


