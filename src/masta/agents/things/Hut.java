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
						new File("./bin/masta/img/hut.png")
					);
				type_alloc_hut_img[AllocMod.STATIC.ordinal()] = 
						ImageIO.read(
								new File("./bin/masta/img/hut_static.png")
							);
				type_alloc_hut_img[AllocMod.MIN_BEFORE.ordinal()] = 
						ImageIO.read(
								new File("./bin/masta/img/hut_min_before.png")
							);
				type_alloc_hut_img[AllocMod.AFFINITY.ordinal()] = 
						ImageIO.read(
								new File("./bin/masta/img/hut_affinity.png")
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
		
		switch(Hut.alloc_state++ % AllocMod.values().length)
		{
		case 0:
			this.alloc_mod = AllocMod.STATIC;
			break;
		case 1:
			this.alloc_mod = AllocMod.MIN_BEFORE;
			break;
		case 2:
			this.alloc_mod = AllocMod.AFFINITY;
			break;
		}
		
		this.setImage(Hut.type_alloc_hut_img[this.alloc_mod.ordinal()]);
		
		Human h;
		for(int i=0; i<inhabitant_max_nbr; ++i)
		{
			h = new Human();
			h.setHut(this);
			this.createTurtle(h);
		}
		
		this.setColor(Color.black);
		this.setDimension(15, 15);
		this.playRole("Hut");
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
			return Job.HUNTER;
		case 1: 
			return Job.GATHERER;
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
		float cur, exp;
		Job job = null;
		
		for(Job j: Job.values())
		{
			exp = h.getExp(j);
			exp = (int)Math.max(1f, exp);
			cur = this.getStock(j.getResource()) / exp ; 
			
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
	
	public float getMinStock()
	{
		float cur;
		float min = Float.MAX_VALUE;

		for(Job j: Job.values())
		{
			cur = this.getStock(j.getResource());
			if(cur < min || (cur == min && Math.random() <= 0.33))
				min = cur;
		}
		return min;
	}
	
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
			bar_height = (int)(cell_size * (this.getStock(j.getResource())/1000));
			g.fillRect(
					x+(j.ordinal()*cell_size) - (this.getWidth()/2)*cell_size,
					y - bar_height + (this.getHeight()/2)*cell_size,
					cell_size,
					bar_height
				);
		}
		
	}
	

	//*************************************************************************
	//	PRIVATE CLASS
	//*************************************************************************
	
	public enum AllocMod
	{
		STATIC, MIN_BEFORE, AFFINITY;
	}

	//*************************************************************************
	//	ATTRIBUTS METHODS
	//*************************************************************************
	
	private static final long serialVersionUID = -1712079622696858987L;

	private static Image hut_img;
	private static Image[] type_alloc_hut_img = new Image[AllocMod.values().length];
	private static int alloc_state = 0;
	
	private int inhabitant_max_nbr = 9;
	private List<Human> inhabitant_list;
	
	protected float[] stock = new float[Resource.values().length];
	
	public static LinkedList<Hut> allHuts = new LinkedList<>();
	
	private int job_state = 0;
	public AllocMod alloc_mod = AllocMod.STATIC;

}
