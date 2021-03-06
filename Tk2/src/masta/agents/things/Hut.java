package masta.agents.things;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import java.io.File;
import java.io.IOException;

import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.media.j3d.WakeupOrOfAnds;

import masta.Job;
import masta.Resource;
import masta.agents.AAgent;
import masta.agents.FixedAgent;
import masta.agents.animals.humans.Human;

public class Hut extends FixedAgent
{
	
	//*************************************************************************
	//	CONSTRUCTORS
	//*************************************************************************
	
	public Hut()
	{
		super();
		allHuts.add(this);
		
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
		
		this.setHidden(true); // bricolage pour le mettre à jour que tous les 100 tours

	}
	
	public void setup()
	{
		super.setup();
		this.setColor(Color.black);
		this.setDimension(15, 15);
		this.playRole("Hut");
		
		Human h;
		for(int i=0; i<inhabitant_max_nbr; ++i)
		{
			h = new Human();
			h.setHut(this);
			this.createTurtle(h);
		}
	}
	
	//*************************************************************************
	//	METHODS
	//*************************************************************************

	
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
	
	public Job giveJob(Human h)
	{
		switch(alloc_mod)
		{
		case STATIC:
			return staticAlloc(h);
		case MIN_BEFORE:
			return minBeforeAlloc(h);
		case AFFINITY:
			return affinityAlloc(h);
		}
		
		return null;
	}

	
	private Job staticAlloc(Human h)
	{
		if(h.getJob() != null)
			return h.getJob();
		
		switch(++job_state % 3)
		{
		case 0:
			return Job.GATHERER;
		case 1: 
			return Job.HUNTER;
		case 2:
			return Job.WOODCUTTER;
		}
		
		return null;
	}
	
	private Job minBeforeAlloc(Human h)
	{
		float min = Float.MAX_VALUE;
		float cur;
		Job job = null;
		for(Job j: Job.values())
		{
			cur = this.getStock(j.getResource());
			if(cur < min || (cur == min && Math.random() <= 0.33))
			{
				min = cur;
				job = j;
			}				
		}
		
		return job;
	}
	
	private Job affinityAlloc(Human h)
	{
		float min = Float.MAX_VALUE;
		float cur;
		Job job = null;
		
		for(Job j: Job.values())
		{
			cur = this.getStock(j.getResource()) / h.getExp(j); // divid by level
			if(cur < min || (cur == min && Math.random() <= 0.33))
			{
				min = cur;
				job = j;
			}				
		}
		
		return job;
	}

	//*************************************************************************
	//	GETTERS / SETTERS
	//*************************************************************************
	
	public float getStock(Resource resource) 
	{
		return stock[resource.ordinal()];
	}

	public void incrStock(Resource resource, float stock) 
	{
		this.stock[resource.ordinal()] += stock;
	}

	//*************************************************************************
	//	OVERRIDE METHODS
	//*************************************************************************
	
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
	
	@Override
	public void die()
	{
		allHuts.remove(this);
		super.die();
	}
	
	@Override
	public void paint(Graphics g, int x, int y, int cell_size)
	{
		super.paint(g,x,y,cell_size);
		
		int bar_height;
		for(Job j: Job.values())
		{
			g.setColor(j.getColor());
			bar_height = (int)(cell_size * (this.getStock(j.getResource())/200));
			g.fillRect(
					x+(j.ordinal()*cell_size) - this.getWidth(),
					y - bar_height + this.getHeight(),
					cell_size,
					bar_height
				);
		}
		
	}
	

	//*************************************************************************
	//	PRIVATE CLASS
	//*************************************************************************
	
	private enum AllocMod
	{
		STATIC, MIN_BEFORE, AFFINITY;
	}

	//*************************************************************************
	//	ATTRIBUTS METHODS
	//*************************************************************************
	
	private static final long serialVersionUID = -1712079622696858987L;

	private static Image hut_img = null;
	
	private int inhabitant_max_nbr = 10;
	private List<Human> inhabitant_list;
	
	protected float[] stock = new float[Resource.values().length];
	
	public static LinkedList<Hut> allHuts = new LinkedList<>();
	
	private int job_state = 0;
	private AllocMod alloc_mod = AllocMod.MIN_BEFORE;

}
