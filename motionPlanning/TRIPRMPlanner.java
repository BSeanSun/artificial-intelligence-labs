package assignment_robots;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.PriorityQueue;






public class TRIPRMPlanner {

	private int k;
	private int x;
	private int y;
	private int sampleNum;
	private int armLength;
	
	public TRIPRMPlanner(int sk, int sx, int sy, int sarmlength,int sample) {
		this.k = sk;
		this.x = sx;
		this.y = sy;
		this.armLength = sarmlength;	
		this.sampleNum = sample;
	}
	
	
public Graph CreateGraph(World check){
		
		double[] theta1 = new double[this.sampleNum];
		double[] theta2 = new double[this.sampleNum];
		double[] theta3 = new double[this.sampleNum];
		Graph graph = new Graph();
		for(int i = 0; i<this.sampleNum; i++){
			
			Random rand1 = new Random(); 
			int temp1 = rand1.nextInt(314);
			theta1[i] = (double)temp1/100;
						
			Random rand2 = new Random();
			int temp2 = rand2.nextInt(314);
			theta2[i] = (double)temp2/100;
			
			Random rand3 = new Random();
			int temp3 = rand3.nextInt(628);
			theta3[i] = (double)temp3/100;
			
			double[] temp_config = {x,y,armLength,theta1[i],armLength,theta2[i],armLength,theta3[i]};
			ArmRobot temp_arm = new ArmRobot(ArmRobot.LinkNum);
			temp_arm.set(temp_config);
			graph.addVertex(temp_arm);
		}

			
		for(int j = 0; j<this.sampleNum; j++){
			ArrayList<ArmRobot> arm_list = new ArrayList<ArmRobot>();//600
			ArrayList<ArmRobot> list = new ArrayList<ArmRobot>();//15
			
			double[] current_config = {x,y,armLength,theta1[j],armLength,theta2[j],armLength,theta3[j]};
			ArmRobot current_arm = new ArmRobot(ArmRobot.LinkNum);
			current_arm.set(current_config);
			//graph.addVertex(current_arm);

			for(int i = 0; i<this.sampleNum;i++){
				if(j!=i){
				double[] new_config = {x,y,armLength, theta1[i], armLength, theta2[i],armLength,theta3[i]};
				ArmRobot new_arm = new ArmRobot(ArmRobot.LinkNum);
				ArmRobot new_arm_fake = new ArmRobot(ArmRobot.LinkNum);
				new_arm.set(new_config);
				new_arm_fake.set(new_config);
				
				if(check.armCollision(new_arm)==false &&
						check.armCollisionPath(new_arm,current_arm.config,new_arm.config)==false){
				
					ArmLocalPlanner ap = new ArmLocalPlanner();
					double time = ap.moveInParallel(current_arm.config, new_arm_fake.config);
					new_arm_fake.setTime(time);
					
					arm_list.add(new_arm_fake);
					arm_list.sort(comparator);
					}
				}else{
					continue;
				}
			}
			
			for(int i = 0 ; i<k; i++){
				ArmRobot temp_arm = new ArmRobot(ArmRobot.LinkNum);
				temp_arm = arm_list.get(i);
				list.add(temp_arm);
			//	graph.addVertex(temp_arm);
				try {
					graph.addEdge(current_arm, temp_arm);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		return graph;
		}
	
public List<ArmRobot> QPhase(Graph graph){
	  PriorityQueue <ArmRobot> OpenList = new PriorityQueue<ArmRobot>();
		HashMap<ArmRobot, ArmRobot> visited = new HashMap<ArmRobot, ArmRobot>();
		ArmRobot startNode = new ArmRobot(ArmRobot.LinkNum);
		ArmRobot currentNode = new ArmRobot(ArmRobot.LinkNum);
		ArmRobot goalNode = new ArmRobot(ArmRobot.LinkNum);
		
		startNode = graph.mVertices.get(0);
		System.out.println("Start config "+startNode);
		goalNode = graph.mVertices.get(15);
		System.out.println("Goal config "+goalNode);
		
	    OpenList.add(startNode);
	    visited.put(startNode,null);
	    
			while(!OpenList.isEmpty()){
				 currentNode = OpenList.poll();
				
				if (currentNode.equals(goalNode)) {
					return backchain(currentNode, visited);
				}
		        
				ArrayList<ArmRobot> successors = graph.getSuccessor(currentNode);
				for (ArmRobot child : successors){
					if(!visited.containsKey(child)){
							OpenList.add(child);
							visited.put(child,currentNode);
					}else{
						if(!child.equals(startNode) && 
							visited.get(child).getTime() > child.getTime()){
							visited.remove(child);
							visited.put(child,currentNode); 
							}
					}
					
					
		        }
		    }
		   
			 OpenList.clear();
		     visited.clear();
		return null;
	}


protected List<ArmRobot> backchain(ArmRobot node,
		HashMap<ArmRobot, ArmRobot> visited) {

	LinkedList<ArmRobot> solution = new LinkedList<ArmRobot>();

	// chain through the visited hashmap to find each previous node,
	// add to the solution
	while (node != null) {
		solution.addFirst(node);
		node = visited.get(node);
	}

	return solution;
}
	
	private Comparator<ArmRobot> comparator =  new Comparator<ArmRobot>(){
		@Override
		public int compare(ArmRobot arg0, ArmRobot arg1) {
			// TODO Auto-generated method stub
			if(arg0.getTime() < arg1.getTime())
				return -1;
			else if(arg0.getTime() > arg1.getTime())
				return 1;
			return 0;
		}   
	};
	
	
	
	
	
	}
	

