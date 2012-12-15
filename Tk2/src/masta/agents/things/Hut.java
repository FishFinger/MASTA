package masta.agents.things;

import java.awt.Color;
import java.awt.Image;

import java.io.File;
import java.io.IOException;

import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import masta.StockableThing;
import masta.agents.AAgent;
import masta.agents.FixedAgent;
import masta.agents.animals.humans.Human;

public class Hut extends FixedAgent
{

	private static Image hut_img = null;
	
	private int inhabitant_max_nbr = 20;
	private List<Human> inhabitant_list;
	
	protected float[] stock = new float[3];
	
	public Hut()
	{
		super();
		
		if(hut_img == null)
		{
			try {
				hut_img = ImageIO.read(
						new File("/home/clement/m2/masta/Tk2/src/masta/img/hut.png")
					);
			} catch (IOException e) {}
		}
		this.setImage(hut_img);
		this.inhabitant_list = new LinkedList<Human>();
		this.setDimension(
				this.getImage().getWidth(null), 
				this.getImage().getHeight(null)
			);
	}
	
	public void setup()
	{
		super.setup();
		this.setColor(Color.black);
		this.setDimension(15, 15);
		this.setHidden(true);
		this.playRole("Hut");
	}
	
	@Override
	protected void beforeUpdateTest() {
		if(this.smell("sea") > 0)
			this.die();
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void afterUpdateTest() {
		// TODO Auto-generated method stub

	}
	
	public boolean isFull()
	{
		return this.inhabitant_list.size() >= this.inhabitant_max_nbr;
	}
	
	public boolean addAnInhabitant(Human h)
	{
		if(!this.isFull())
		{
			this.inhabitant_list.add(h);
			return true;
		}
		else
			return false;
	}
	
	public boolean removeAnInhabitant(Human h)
	{
		return this.inhabitant_list.remove(h);
	}
	
	

	//*************************************************************************
	//	GETTERS / SETTERS
	//*************************************************************************
	
	public float getStock(int thing) 
	{
		return stock[thing];
	}

	public void incrStock(int thing, float stock) 
	{
		this.stock[thing] += stock;
	}

	
}
