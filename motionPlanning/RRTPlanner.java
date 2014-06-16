package assignment_robots;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class RRTPlanner {
	private int k;
	private int x;
	private int y;
	private double theta;
	CarRobot startnode = new CarRobot();
	
	public RRTPlanner(int sk, int sx, int sy, double theta) {
		this.k = sk;
		this.x = sx;
		this.y = sy;
		this.theta = theta;
		CarState state = new CarState(sx, sy, theta);
		startnode.set(state);
	}
	
	public Tree CreateTree(World check){
		Tree tree = new Tree();
		tree.addVertex(startnode);
		ArrayList<CarRobot> car_list = new ArrayList<CarRobot>();
		
		SteeredCar control = new SteeredCar();
		
		for(int i = 0 ; i<k;i++){
			
		    
			Random rand1 = new Random(); 
			double rx = (double)rand1.nextInt(600);
		
			Random rand2 = new Random(); 
			double ry = (double)rand2.nextInt(400);  
			
			Random rand3 = new Random();
			int temp = rand3.nextInt(628);
			double rtheta = (double)temp/100;
			
			CarRobot carrand = new CarRobot();
			CarState carrand_state = new CarState(rx,ry,rtheta);
			carrand.set(carrand_state);

			for (int j = 0; j < tree.mVertices.size(); j ++){
				double Xget = tree.mVertices.get(j).getCarState().getX();
				double Yget = tree.mVertices.get(j).getCarState().getY();
				double distance = LocalDistance(Xget,rx,Yget,ry);;
				tree.mVertices.get(j).setDistance(distance);
				car_list.add(tree.mVertices.get(j));
				car_list.sort(comparator);
			}
			
			
			CarRobot current = new CarRobot();
			current = car_list.get(0);
			car_list.clear();
			
			for(int j = 0; j < control.control.length;j ++){
				/*double xNew = cx + control.getControl(j)[0];
				double yNew = cy + control.getControl(j)[1];
				double thetaNew = ctheta + control.getControl(j)[2];
				
				CarRobot newleaf = new CarRobot();
				CarState newleaf_state = new CarState(xNew, yNew, thetaNew);
				newleaf.set(newleaf_state);
				double leafdistance = LocalDistance(xNew,rx,yNew,ry);
				newleaf.setDistance(leafdistance);
				*/
				CarRobot newleaf = new CarRobot();
				CarState newleaf_state = control.move(current.s, j, 1);
				if(newleaf_state.getX() < 0 || newleaf_state.getY() < 0)
					continue;
				newleaf.set(newleaf_state);
				double leafdistance = LocalDistance(newleaf_state.getX(),rx,newleaf_state.getY(),ry);
				newleaf.setDistance(leafdistance);
				
				if(check.carCollision(newleaf)==false &&
						check.carCollisionPath(newleaf, newleaf_state,j,1)==false){
					car_list.add(newleaf);
					car_list.sort(comparator);
					}
			}
			
			CarRobot child = new CarRobot();
			child = car_list.get(0);
			tree.addVertex(child);
			try {
				tree.addEdge(current, child);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			car_list.clear();
			
			
		}
		return tree;
	}
	
	
	private double LocalDistance(double cx, double rx, double cy, double ry){
		return Math.sqrt((cx-rx)*(cx-rx)+(cy-ry)*(cy-ry));
	}
	
	private Comparator<CarRobot> comparator =  new Comparator<CarRobot>(){
		@Override
		public int compare(CarRobot arg0, CarRobot arg1) {
			// TODO Auto-generated method stub
			if(arg0.getDistance() < arg1.getDistance())
				return -1;
			else if(arg0.getDistance() > arg1.getDistance())
				return 1;
			return 0;
		}   
	};
	
}
