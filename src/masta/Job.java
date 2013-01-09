package masta;

import java.awt.Color;

public enum Job
{
	GATHERER, HUNTER, WOODCUTTER;
	
	public Color getColor()
	{
		switch(this)
		{
		case GATHERER:
			return Color.green;
		case HUNTER:
			return Color.red;
		case WOODCUTTER:
			return Color.yellow;
		default:
			return Color.black;
		}
	}
	
	public Resource getResource()
	{
		switch(this)
		{
		case GATHERER:
			return Resource.FRUIT;
		case HUNTER:
			return Resource.MEAT;
		case WOODCUTTER:
			return Resource.WOOD;
		default:
			return null;
		}
	}
}
/*public class Job 
{
	public static final Job GATHERER = new Job();
	public static final Job HUNTER = new Job();
	public static final Job WOODCUTTER = new Job();
	
	//*************************************************************************
	//	CONSTRUCTORS
	//*************************************************************************
	
	public Job()
	{
		this.idx = nb_job++;
	}
	
	public int getIdx()
	{
		return this.idx;
	}
	
	//*************************************************************************
	//	ATTRIBUTS
	//*************************************************************************

	private int idx;
	private static int nb_job = 0;
}*/