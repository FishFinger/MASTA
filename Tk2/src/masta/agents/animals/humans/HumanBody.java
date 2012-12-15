package masta.agents.animals.humans;

import masta.agents.animals.AnimalBody;
import masta.StockableThing;

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
		for (int i=0; i<StockableThing.getMaxIndex(); ++i)
			weight += this.getStock(i);
		
		return weight > this.getMaxPortableWeight();
	}
	
	public void eatMeat()
	{
		float qty = Math.min(this.stock[StockableThing.MEAT], (float)this.mounthful_qty); 
		this.stock[StockableThing.MEAT] -= qty;
		this.protein += qty;
	}
	
	protected void eatFruit()
	{
		float qty = Math.min(this.stock[StockableThing.FRUIT], (float)this.mounthful_qty); 
		this.stock[StockableThing.FRUIT] -= qty;
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
	
	protected float getStock(int thing)
	{
		return this.stock[thing];
	}
	
	protected void setStock(int thing, float value)
	{
		this.stock[thing] = value;
	}
	
	protected void incrStock(int thing, float value)
	{
		this.stock[thing] += value;
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
	
	private float[] stock = new float[StockableThing.getMaxIndex()];


	//*************************************************************************
	//	CONSTANTS
	//*************************************************************************
	
	private static final float STRENGTH2WEIGHT_COEFF = 1000f;

}
