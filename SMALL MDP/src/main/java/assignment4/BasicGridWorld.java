package assignment4;

import assignment4.util.AgentPainter;
import assignment4.util.AtLocation;
import assignment4.util.FailPainter;
import assignment4.util.LocationPainter;
import assignment4.util.Movement;
import assignment4.util.WallPainter;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.Attribute;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.ObjectClass;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.MutableState;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.SADomain;
import burlap.oomdp.visualizer.StateRenderLayer;
import burlap.oomdp.visualizer.Visualizer;

public class BasicGridWorld implements DomainGenerator {

	public static final String ATTX = "x";
	public static final String ATTY = "y";

	public static final String CLASSAGENT = "agent";
	public static final String CLASSLOCATION = "location";
	public static final String CLASS_FAIL= "fail";

	public static final String ACTIONNORTH = "north";
	public static final String ACTIONSOUTH = "south";
	public static final String ACTIONEAST = "east";
	public static final String ACTIONWEST = "west";

	public static final String PFAT = "at";

	// ordered so first dimension is x
	protected int[][] map ;
	protected static int mapx;
	protected static int mapy;
	protected static int failx;
	protected static int faily;
	
	public BasicGridWorld(int[][] map,int mapx, int mapy, int failx, int faily){
		this.map = map;
		this.mapx = mapx;
		this.mapy = mapy;
		this.failx = failx;
		this.faily = faily;
		
	}


	@Override
	public Domain generateDomain() {

		SADomain domain = new SADomain();

		Attribute xatt = new Attribute(domain, ATTX,
				Attribute.AttributeType.INT);
		xatt.setLims(0, mapx);

		Attribute yatt = new Attribute(domain, ATTY,
				Attribute.AttributeType.INT);
		yatt.setLims(0, mapy);

		ObjectClass agentClass = new ObjectClass(domain, CLASSAGENT);
		agentClass.addAttribute(xatt);
		agentClass.addAttribute(yatt);

		ObjectClass locationClass = new ObjectClass(domain, CLASSLOCATION);
		locationClass.addAttribute(xatt);
		locationClass.addAttribute(yatt);

		ObjectClass failClass = new ObjectClass(domain, CLASS_FAIL);
		failClass.addAttribute(xatt);
		failClass.addAttribute(yatt);

		new Movement(ACTIONNORTH, domain, 0, map);
		new Movement(ACTIONSOUTH, domain, 1, map);
		new Movement(ACTIONEAST, domain, 2, map);
		new Movement(ACTIONWEST, domain, 3, map);

		new AtLocation(domain);

		return domain;
	}

	public static State getExampleState(Domain domain) {
		State s = new MutableState();
		ObjectInstance agent = new MutableObjectInstance(
				domain.getObjectClass(CLASSAGENT), "agent0");
		agent.setValue(ATTX, 0);
		agent.setValue(ATTY, 0);

		ObjectInstance location = new MutableObjectInstance(
				domain.getObjectClass(CLASSLOCATION), "goal");
		location.setValue(ATTX, mapx);
		location.setValue(ATTY, mapy);

		ObjectInstance location1 = new MutableObjectInstance(
				domain.getObjectClass(CLASS_FAIL), "fail");
		location1.setValue(ATTX, failx);
		location1.setValue(ATTY, faily);

		s.addObject(agent);
		s.addObject(location);
		s.addObject(location1);



		return s;
	}

	public StateRenderLayer getStateRenderLayer() {
		StateRenderLayer rl = new StateRenderLayer();
		rl.addStaticPainter(new WallPainter(map));
		rl.addObjectClassPainter(CLASSLOCATION, new LocationPainter(map));
		rl.addObjectClassPainter(CLASSAGENT, new AgentPainter(map));
		rl.addObjectClassPainter(CLASS_FAIL, new FailPainter(map));

		return rl;
	}

	public Visualizer getVisualizer() {
		return new Visualizer(this.getStateRenderLayer());
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}



}
