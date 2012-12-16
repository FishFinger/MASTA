package masta.agents.animals.humans;

import masta.agents.animals.AnimalBody;
import masta.Resource;

public class HumanBody extends AnimalBody 
{
	
	//*************************************************************************
	//	CONSTRUCTOR
	//*************************************************************************

	public HumanBody(Human agent, int energie, int mounthful_qty) {
		super();
		this.setAgent(agent);
		this.fiber = energie/2;
		this.protein = energie/2;
		this.mounthful_qty = mounthful_qty;
	}

	//*************************************************************************
	//	PUBLICS METHODS
	//*************************************************************************

	public boolean isHeavy() 
	{
		float weight = 0;
		for (Resource r: Resource.values())
			weight += this.getStock(r);
		
		return weight >= this.getMaxPortableWeight();
	}
	
	public void eatMeat()
	{
		float qty = Math.min(this.getStock(Resource.MEAT), (float)this.mounthful_qty); 
		this.incrStock(Resource.MEAT, -qty);
		this.protein += qty;
	}
	
	protected void eatFruit()
	{
		float qty = Math.min(this.getStock(Resource.FRUIT), (float)this.mounthful_qty); 
		this.incrStock(Resource.FRUIT, -qty);
		this.fiber += qty;
	}
	
	//*************************************************************************
	//	GETTERS/SETTERS
	//*************************************************************************

	protected int getFiber()
	{
		return this.fiber;
	}
	
	protected int getProtein()
	{
		return this.protein;
	}
	
	protected void setAgent(Human a) {
		this.agent = a;
	}
	
	protected float getStock(Resource resource)
	{
		return this.stock[resource.ordinal()];
	}
	
	protected void setStock(Resource resource, float value)
	{
		this.stock[resource.ordinal()] = value;
	}
	
	protected void incrStock(Resource resource, float value)
	{
		this.stock[resource.ordinal()] += value;
	}
	
	protected float getMaxPortableWeight()
	{
		return this.strength_level*STRENGTH2WEIGHT_COEFF;
	}
	
	
	//*************************************************************************
	//	OVERRIDE METHODS
	//*************************************************************************

	@Override
	protected Human getAgent() {
		return this.agent;
	}

	@Override
	public float useEnergie(float qty) {
		this.fiber -= qty/2;
		this.protein -= qty/2;
		return getEnergie();
	}

	@Override
	public float getEnergie() {
		return this.fiber + this.protein;
	}

	@Override
	public float getMounthfulQty() {
		return this.mounthful_qty;
	}
	
	//*************************************************************************
	//	ATTRIBUTS
	//*************************************************************************

	private Human agent;
	
	// energie
	private int fiber;
	private int protein;
	
	private float mounthful_qty;

	private float strength_level = 1;
	
	private float[] stock = new float[Resource.values().length];


	//*************************************************************************
	//	CONSTANTS
	//*************************************************************************
	
	private static final float STRENGTH2WEIGHT_COEFF = 100f;

}
