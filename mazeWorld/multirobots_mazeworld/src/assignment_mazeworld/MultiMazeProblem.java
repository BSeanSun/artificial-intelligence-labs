package assignment_mazeworld;

import java.util.ArrayList;
import java.util.Arrays;

// Find a path for a single agent to get from a start location (xStart, yStart)
//  to a goal location (xGoal, yGoal)

public class MultiMazeProblem extends InformedSearchProblem {

	private static int actions[][] = {Maze.NORTH, Maze.EAST, Maze.SOUTH, Maze.WEST, Maze.NOTMOVE}; 
	
	private Maze maze;
    private int flag = 0;
    
    private int[] xGoal = new int[Maze.k];
    private int[] yGoal = new int[Maze.k];
    private int[] xStart = new int[Maze.k];
    private int[] yStart = new int[Maze.k];
    //private double[] eachcost = new double[Maze.k];
	public MultiMazeProblem(Maze m, int[] sx, int[] sy, int[] gx, int[] gy) {
		
		for(int i = 0; i<Maze.k;i++){
		xStart[i] = sx[i];
		yStart[i] = sy[i];
		xGoal[i] = gx[i];
		yGoal[i] = gy[i];
		//eachcost[i] = 0;
		}
		startNode = new MultiMazeNode(sx, sy, 0);
		maze = m;	
	}
	
	
	
	// node class used by searches.  Searches themselves are implemented
	//  in SearchProblem.
	public class MultiMazeNode implements SearchNode {

		// location of the agent in the maze
		protected int[] state; 
		
		// how far the current node is from the start.  Not strictly required
		//  for uninformed search, but useful information for debugging, 
		//  and for comparing paths
		private double cost;  

	/*	public MultiMazeNode(int x, int y, double c) {
			//state = new int[2*Maze.k];
				this.state[2*flag] = x;
				this.state[2*flag+1] = y;
			cost = c;
		}
		*/
		public MultiMazeNode(int x[], int y[], double c) {
			state = new int[2*Maze.k];
			for(int i= 0; i<Maze.k;i++){
				this.state[2*i] = x[i];
				this.state[2*i+1] = y[i];
			}
			cost = c;
		}
		public int getX(int k) {
			return state[2*k];
		}
		
		public int getY(int k) {
			return state[2*k+1];
		}

		public ArrayList<SearchNode> getSuccessors() {

			ArrayList<SearchNode> successors = new ArrayList<SearchNode>();
			
			int[] xNew = new int[Maze.k];
			int[] yNew = new int[Maze.k];
			
			for(int i=0; i<Maze.k;i++){
				xNew[i] = state[2*i];
				yNew[i] = state[2*i+1];
			}
			
			for (int[] action: actions) {
					xNew[flag] = this.state[2*flag] + action[0];
					yNew[flag] = this.state[2*flag+1] + action[1];
					
				
				
				if(maze.isLegal(xNew, yNew)) {
					//System.out.println("legal successor found " + " " + xNew + " " + yNew);
					SearchNode succ = new MultiMazeNode(xNew, yNew, getCost() + 1.0);
					successors.add(succ);
				}
				//System.out.println("testing successor " + xNew + " " + yNew);
				
				}
			flag++;
			if(flag > Maze.k-1)
				flag = 0;
			return successors;

		}
		
		
		
		
		@Override
		public boolean goalTest() {
			for(int i = 0; i<Maze.k;i++){
				if(state[2*i]!=xGoal[i]||state[2*i+1]!=yGoal[i])
					return false;
			}	
			return true;
		}


		// an equality test is required so that visited sets in searches
		// can check for containment of states
		@Override
		public boolean equals(Object other) {
			return Arrays.equals(state, ((MultiMazeNode) other).state);
		}

		@Override
		public int hashCode() {
			return state[0] * 100 + state[1]; 
		}

		@Override
		public String toString() {
			//return new String("Maze state " + state[0] + ", " + state[1] + " "
			//		+ " depth " + getCost());
			return Arrays.toString(this.state);
		}

		@Override
		public double getCost() {
			return cost;
		}
		

		@Override
		public double heuristic() {
			// manhattan distance metric for simple maze with one agent:
			double[] dx = new double[Maze.k];
			double[] dy = new double[Maze.k];
			double sumx = 0.0;
			double sumy = 0.0;
			
			for(int i=0; i<Maze.k;i++){
				dx[i]= xGoal[i]-state[2*i];
				dy[i] = yGoal[i]-state[2*i+1];
			}
			
			for(int i=0;i<Maze.k;i++){
				//sumx = sumx + Math.abs(dx[i]);
				//sumy = sumy + Math.abs(dy[i]);
				sumx = sumx + dx[i];
				sumy = sumy + dy[i];
				//sumx = sumx + (dx[i])*(dx[i]);
				//sumy = sumy + (dy[i])*(dy[i]);
			}
			return Math.abs(sumx) + Math.abs(sumy);
			//return Math.sqrt(sumy*sumy+sumx*sumx);
			//return (sumx + sumy);
		}
		
	/*	   public double heuristic() {
			   // manhattan distance 
			     double[] dx = new double[Maze.k];
			     double[] dy = new double[Maze.k];
			     double sumx = 0.0;
			     double sumy = 0.0;
			     
			     //record each robot's manhattan distance
			     for(int i=0; i<Maze.k;i++){
			        dx[i]= xGoal[i]-state[2*i];
			        dy[i] = yGoal[i]-state[2*i+1];
			        }
			      
			     double minx = dx[0];
			     double miny = dy[0];
			     for(int i=1;i<Maze.k;i++){
			       if(minx > dx[i]){
			          minx = dx[i];
			       }
			       if(miny >dy[i]){
			          miny = dy[i];
			       }
			       }
			       return Math.abs(sumx) + Math.abs(sumy);
			     }
		
		*/

		
		
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
