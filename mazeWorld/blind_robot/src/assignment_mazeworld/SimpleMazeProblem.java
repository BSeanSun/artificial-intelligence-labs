package assignment_mazeworld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javafx.scene.paint.Color;
import assignment_mazeworld.SearchProblem.SearchNode;

// Find a path for a single agent to get from a start location (xStart, yStart)
//  to a goal location (xGoal, yGoal)

public class SimpleMazeProblem extends InformedSearchProblem {

	private static int actions[][] = {Maze.NORTH, Maze.EAST, Maze.SOUTH, Maze.WEST}; 
	
	private int xStart, yStart, xGoal, yGoal;
    private ArrayList<SimpleMazeNode> initialset;
	private Maze maze;
	private SimpleMazeNode startpos;
	/*
	public SimpleMazeProblem(Maze m, int sx, int sy, int gx, int gy) {
		startNode = new SimpleMazeNode(sx, sy, 0);
		xStart = sx;
		yStart = sy;
		xGoal = gx;
		yGoal = gy;
		
		maze = m;		
	}
	*/
	
	
	public SimpleMazeProblem(Maze m,int startx, int starty, int goalx, int goaly) {
		startpos = new SimpleMazeNode(startx, starty, 0);
		xStart = startx;
		yStart = starty;
		xGoal = goalx;
		yGoal = goaly;
		maze = m;	
		initialset = get_all_possible_states();
		startNode = new BlindSearchNode(initialset,0,"Begin");
	}
	
	public ArrayList<SimpleMazeNode> get_all_possible_states()
	{ 
	ArrayList<SimpleMazeNode> allstates = new ArrayList<SimpleMazeNode>();
	for (int y = 0; y < maze.height; y++) {
		for (int x = 0; x < maze.width; x++) {
            if (maze.isLegal(x, y)){
            	SimpleMazeNode node = new SimpleMazeNode(x,y,0);   
            	allstates.add(node);
            }
    
		}
	}
	return allstates;
	}
	
	
	// node class used by searches.  Searches themselves are implemented
	//  in SearchProblem.
	public class SimpleMazeNode{

		// location of the agent in the maze
		protected int[] state; 
		
		// how far the current node is from the start.  Not strictly required
		//  for uninformed search, but useful information for debugging, 
		//  and for comparing paths
		private double cost;  

		public SimpleMazeNode(int x, int y, double c) {
			state = new int[2];
			this.state[0] = x;
			this.state[1] = y;
		
			cost = c;

		}
		
		public int getX() {
			return state[0];
		}
		
		public int getY() {
			return state[1];
		}


}

public class BlindSearchNode implements SearchNode{

	// location of the agent in the maze
			protected ArrayList<SimpleMazeNode> possible_states; 
			
			// how far the current node is from the start.  Not strictly required
			//  for uninformed search, but useful information for debugging, 
			//  and for comparing paths
			private double cost;  
			public String direction;  
			
			public BlindSearchNode(ArrayList<SimpleMazeNode> s, double c, String d) {
				possible_states = new ArrayList<SimpleMazeNode>();
				possible_states =s;
				cost = c;
				direction = d;
				
			}
			
			public int[] getX() {
				int[] x = new int[possible_states.size()];
				for(int i=0;i<this.possible_states.size();i++){
					x[i] = possible_states.get(i).state[0];
				}
				return x;
			}
			
			public int[] getY() {
				int[] y = new int[possible_states.size()];
				for(int i=0;i<this.possible_states.size();i++){
					y[i] = possible_states.get(i).state[0];
				}
				return y;
			}
			
			public ArrayList<SearchNode> getSuccessors() {

				ArrayList<SearchNode> successors = new ArrayList<SearchNode>();
				
				for (int[] action: actions) {

					ArrayList<SimpleMazeNode> new_possible_states = new ArrayList<SimpleMazeNode>();
					String direction = "";
					if(action[0]==0&&action[1]==1){
						direction = "North";
					}else if(action[0]==0&&action[1]==-1){
						direction = "South";
					}else if(action[0]==1&&action[1]==0){
						direction="East";
					}else{
						direction="West";
					}
					for(SimpleMazeNode eachstate: this.possible_states){
						int xNew = eachstate.state[0] + action[0];
						int yNew = eachstate.state[1] + action[1]; 
						int xOld = eachstate.state[0];
						int yOld = eachstate.state[1];
						
						if(maze.isLegal(xNew, yNew)) {
							//System.out.println("legal successor found " + " " + xNew + " " + yNew);
							SimpleMazeNode succ = new SimpleMazeNode(xNew, yNew, getCost() + 1.0);
							
							if(new_possible_states.size()==0){
								new_possible_states.add(succ);
							}else{
								//new_possible_states.contains(succ)
								int flag = 0;
								for (int i = 0; i < new_possible_states.size();i++){
									if (new_possible_states.get(i).state[0] == xNew&&new_possible_states.get(i).state[1] == yNew ){
										break;
									}
									flag++;
								}
								if(flag == new_possible_states.size()){
									new_possible_states.add(succ);
								}
							}
							
							
						}
						else{
							
							SimpleMazeNode oldsucc = new SimpleMazeNode(xOld,yOld, getCost() + 1.0);
							
							if(new_possible_states.size()==0){
								new_possible_states.add(oldsucc);
							}else{
								int flag=0;
								for (int i = 0; i < new_possible_states.size();i++){
									if (new_possible_states.get(i).state[0] == xOld&&new_possible_states.get(i).state[1] == yOld){
										break;
									}
									flag++;
								}
								if(flag==new_possible_states.size()){
									new_possible_states.add(oldsucc);	
								}
								
								
							}
							
						}
						
	
					}
				SearchNode eachsuccessor = new BlindSearchNode(new_possible_states, getCost() + 1.0,direction);
				successors.add(eachsuccessor);
				}
				return successors;
			}

			
			
	
	@Override
	public boolean goalTest() {
		return (possible_states.get(0).state[0] == xGoal
				&&possible_states.get(0).state[1] == yGoal
				//&&possible_states.size() == 1
				);
	}


	// an equality test is required so that visited sets in searches
	// can check for containment of states
	
	@Override
	public boolean equals(Object other) {
		for(int i = 0; i<possible_states.size();i++){
			if ((possible_states.get(i).state[0] != ((BlindSearchNode)other).possible_states.get(i).state[0]) ||
					(possible_states.get(i).state[1] != ((BlindSearchNode)other).possible_states.get(i).state[1])
					){
				return false;
			}
		}
		return true;
	}

	



	@Override
	public int hashCode() {
		int hashcode = 0;
		for(int i =0 ; i<possible_states.size(); i++){
			hashcode += 1000*possible_states.size() + 10*(possible_states.get(i).state[0])+possible_states.get(i).state[1];
		}
		return hashcode; 
	}

	@Override
	public String toString() {
		//return new String("size "+ possible_states.size() + "depth " + getCost());
		return direction;
	}

	@Override
	public double getCost() {
		return cost;
	}
	

	@Override
	public double heuristic() {
		// manhattan distance metric for simple maze with one agent:
	/*	double dx = xGoal - state[0];
		double dy = yGoal - state[1];
		return Math.abs(dx) + Math.abs(dy);
		*/
		
		//return this.possible_states.size()/(100*getCost());
		
		
		double sumx=0.0;
		double sumy=0.0;
		double []dx = new double[this.possible_states.size()];
		double []dy = new double[this.possible_states.size()];
		
		for(int i=0;i<this.possible_states.size();i++){
			dx[i] = xGoal - possible_states.get(i).state[0];
			dy[i] = yGoal - possible_states.get(i).state[1];
		}
		for(int i=0;i<this.possible_states.size();i++){
			sumx += dx[i];
			sumy += dy[i];
		}
		return Math.abs(sumx) + Math.abs(sumy);
		
	}
	

	@Override
	public int compareTo(SearchNode o) {
		return (int) Math.signum(priority() - o.priority());
	}
	
	@Override
	public double priority() {
		return heuristic() + getCost();
	}

}
}