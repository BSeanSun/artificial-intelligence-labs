package ProbabilisticSolver;

import java.util.ArrayList;

public class SensorModel {
	public static int R = 0;
	public static int G = 1;
	public static int B = 2;
	public static int Y = 3;
	
	private int[] readValue;
	private TransitionModel t;
	public SensorModel(int[] r,TransitionModel t){
		this.readValue = new int[r.length];
		this.readValue = r;
		this.t = t;
	}
	
	public void updateProb(Maze m){
		int count = 0;
		for(int i = 0;i<this.readValue.length;i++){
			
			for(int j = 0;j<m.width;j++){
				for(int k = 0;k<m.height;k++){
					if(m.getColor(j, k)==this.readValue[i]){
						double currentProb = m.getProb(j, k)*0.88;
						m.setProb(j, k, currentProb);
					}else{
						double currentNProb = m.getProb(j, k)*0.04;
						m.setProb(j, k, currentNProb);
					}
				}
			}
			
			//***************normalization***************
			double sum1 = 0.0;
			for(int ni=0;ni<m.width;ni++){
				for(int nj=0;nj<m.height;nj++){
					sum1 =sum1 + m.getProb(ni, nj);
				}
			}
			
			for(int ni=0;ni<m.width;ni++){
				for(int nj=0;nj<m.height;nj++){
					double temp = m.getProb(ni, nj)/sum1;
					m.setProb(ni, nj,temp);
				}
			}
			//*********************************************
			
			System.out.println("T("+(count)+"), Sensor Model: ");
			count++;
			m.printMaze();
			//***************************possible position*********************
			double max0 = 0.0;
			  int maxi0 = -1;
			  int maxj0 = -1;
			  for(int mi =0;mi<m.width;mi++){
				  for (int j = 0; j <m.height;j++){
					 if( m.getProb(mi, j)>max0){
						 max0 = m.getProb(mi, j);
						 maxi0 = mi;
						 maxj0 = j;
					 }
				  }
			  }
			  
			  ArrayList<Position> possibleP0 = new ArrayList<Position>();
			  for(int ti = 0;ti<m.width;ti++){
				  for(int j = 0;j<m.height;j++){
					  if(m.getProb(ti, j)==max0){
						  Position needToBeAdd = new Position(ti,j);
						  possibleP0.add(needToBeAdd);
					  }
				  }
			  }
			  System.out.print("Most possible position: ");
			  m.getPosition(possibleP0);
			  System.out.println();
			  System.out.println();
			//***************************possible position*********************
			
			
			
			if(i!=this.readValue.length-1){
				double[][] tempnewprob = new double[m.width][m.height];
			for(Position eachp: m.p){
				ArrayList<Position> neighbors = this.t.getRelationship(eachp);
				double temp = 0.0;
				for(Position eachneighbors: neighbors){
					double a = this.t.getTransitionProb(eachp, eachneighbors);
					double b = m.getProb(eachneighbors.getX(), eachneighbors.getY());
					temp = temp +a*b;
					//temp = temp + this.t.getTransitionProb(eachp, eachneighbors)*m.getProb(eachneighbors.getX(), eachneighbors.getY());
				}
				tempnewprob[eachp.getX()][eachp.getY()] = temp;
				//m.setProb(eachp.getX(),eachp.getY(),temp);
			}
			for(int x = 0; x<m.width;x++){
				for(int y = 0; y<m.height;y++){
					m.setProb(x,y,tempnewprob[x][y]);
				}
			  }
			}
			
			/*System.out.println("T("+(count)+"), Transition Model: ");
			count++;
			m.printMaze();
			//***************************possible position*********************
			double max = 0.0;
			  int maxi = -1;
			  int maxj = -1;
			  for(int mi =0;mi<m.width;mi++){
				  for (int j = 0; j <m.height;j++){
					 if( m.getProb(mi, j)>max){
						 max = m.getProb(mi, j);
						 maxi = mi;
						 maxj = j;
					 }
				  }
			  }
			  
			  ArrayList<Position> possibleP = new ArrayList<Position>();
			  for(int ti = 0;ti<m.width;ti++){
				  for(int j = 0;j<m.height;j++){
					  if(m.getProb(ti, j)==max){
						  Position needToBeAdd = new Position(ti,j);
						  possibleP.add(needToBeAdd);
					  }
				  }
			  }
			  System.out.print("Most possible position: ");
			  m.getPosition(possibleP);
			  System.out.println();
			  System.out.println();
			//***************************possible position*********************
*/		
			
		
		}
	}
	
	
}
