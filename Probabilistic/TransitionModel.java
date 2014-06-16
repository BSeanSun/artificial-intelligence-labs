package ProbabilisticSolver;

import java.util.ArrayList;
import java.util.HashMap;

public class TransitionModel {

	HashMap<Position,ArrayList<Position>> relation;
    HashMap<Position,Position> neighbor;
    HashMap<HashMap<Position,Position>, Double> transitionProb;
	int width;
	int height;
	public TransitionModel(int w, int h){
		this.relation = new HashMap<Position,ArrayList<Position>>();
		neighbor = new HashMap<Position,Position>();
		transitionProb = new HashMap<HashMap<Position,Position>,Double>();
		this.width = w;
		this.height = h;
	}
	
	
	
	public void setTransitionProb(Position a, Position b, double value){
		this.neighbor.put(a, b);
		HashMap<Position,Position> current = new HashMap<Position,Position>();
		current.put(a, b);
		this.transitionProb.put(current, value);
	}
	
	public double getTransitionProb(Position a, Position b){
		HashMap<Position,Position> current = new HashMap<Position,Position>();
		current.put(a, b);
		return this.transitionProb.get(current);
	}
	public void setRelationship(Position a, ArrayList<Position> b){
	   this.relation.put(a, b);
	}
	public TransitionModel setTransitionMatrix(ArrayList<Position> posList,TransitionModel t){
		for(Position eachp: posList){
			if(eachp.getX()!=0&&eachp.getX()!=this.width&&eachp.getY()!=0&&eachp.getY()!=this.height){
				ArrayList<Position> recordneighbors = new ArrayList<Position>();
				for(Position eachlittlep:posList){
					if(eachlittlep.getX()==eachp.getX()+1 && eachlittlep.getY() == eachp.getY()
				       ||eachlittlep.getX()==eachp.getX()-1 && eachlittlep.getY() == eachp.getY()
				       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()+1
				       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()-1){
						recordneighbors.add(eachlittlep);
					}
				}
				t.setRelationship(eachp, recordneighbors);
			}else if(eachp.getX()==0&&eachp.getY()==0||eachp.getX()==0&&eachp.getY()==this.width
					 ||eachp.getX()==this.height&&eachp.getY()==0||eachp.getX()==this.width&&eachp.getY()==this.height){
				if(eachp.getX()==0&&eachp.getY()==0){
					ArrayList<Position> recordneighbors = new ArrayList<Position>();
					for(Position eachlittlep:posList){
						if(eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()
					       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()+1
					       ||eachlittlep.getX()==eachp.getX()+1 && eachlittlep.getY() == eachp.getY()){
							recordneighbors.add(eachlittlep);
						}
					}
					t.setRelationship(eachp, recordneighbors);
				}
			  if(eachp.getX()==0&&eachp.getY()==this.width){
				  ArrayList<Position> recordneighbors = new ArrayList<Position>();
					for(Position eachlittlep:posList){
						if(eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()
					       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()-1
					       ||eachlittlep.getX()==eachp.getX()+1 && eachlittlep.getY() == eachp.getY()){
							recordneighbors.add(eachlittlep);
						}
					}
					t.setRelationship(eachp, recordneighbors);
			  }
			  if(eachp.getX()==this.height&&eachp.getY()==0){
				  ArrayList<Position> recordneighbors = new ArrayList<Position>();
					for(Position eachlittlep:posList){
						if(eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()
					       ||eachlittlep.getX()==eachp.getX()-1 && eachlittlep.getY() == eachp.getY()
					       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()+1){
							recordneighbors.add(eachlittlep);
						}
					}
					t.setRelationship(eachp, recordneighbors);
			  }
			  if(eachp.getX()==this.width&&eachp.getY()==this.height){
				  ArrayList<Position> recordneighbors = new ArrayList<Position>();
					for(Position eachlittlep:posList){
						if(eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()
					       ||eachlittlep.getX()==eachp.getX()-1 && eachlittlep.getY() == eachp.getY()
					       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()-1){
							recordneighbors.add(eachlittlep);
						}
					}
					t.setRelationship(eachp, recordneighbors);
			  }
			}else if(eachp.getY()==0){
				ArrayList<Position> recordneighbors = new ArrayList<Position>();
				for(Position eachlittlep:posList){
					if(eachlittlep.getX()==eachp.getX()-1 && eachlittlep.getY() == eachp.getY()
				       ||eachlittlep.getX()==eachp.getX()+1 && eachlittlep.getY() == eachp.getY()
				       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()+1
				       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()){
						recordneighbors.add(eachlittlep);
					}
				}
				t.setRelationship(eachp, recordneighbors);
			}else if(eachp.getY()==this.width){
				ArrayList<Position> recordneighbors = new ArrayList<Position>();
				for(Position eachlittlep:posList){
					if(eachlittlep.getX()==eachp.getX()-1 && eachlittlep.getY() == eachp.getY()
				       ||eachlittlep.getX()==eachp.getX()+1 && eachlittlep.getY() == eachp.getY()
				       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()-1
				       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()){
						recordneighbors.add(eachlittlep);
					}
				}
				t.setRelationship(eachp, recordneighbors);
			}else if(eachp.getX()==0){
				ArrayList<Position> recordneighbors = new ArrayList<Position>();
				for(Position eachlittlep:posList){
					if(eachlittlep.getX()==eachp.getX()+1 && eachlittlep.getY() == eachp.getY()
				       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()-1
				       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()+1
				       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()){
						recordneighbors.add(eachlittlep);
					}
				}
				t.setRelationship(eachp, recordneighbors);
			}else if(eachp.getX()==this.height){
				ArrayList<Position> recordneighbors = new ArrayList<Position>();
				for(Position eachlittlep:posList){
					if(eachlittlep.getX()==eachp.getX()-1 && eachlittlep.getY() == eachp.getY()
				       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()-1
				       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()+1
				       ||eachlittlep.getX()==eachp.getX() && eachlittlep.getY() == eachp.getY()){
						recordneighbors.add(eachlittlep);
					}
				}
				t.setRelationship(eachp, recordneighbors);
			}
		}
		
		return t;
	}
	
	
	public ArrayList<Position> getRelationship(Position a){
		return this.relation.get(a);
	}
	
	public TransitionModel CalculateTransitionMatrix(Maze m,TransitionModel t){
		ArrayList<Position> p = m.p;
		for(Position eachp:p){
			ArrayList<Position> neighbors = t.getRelationship(eachp);
			if(neighbors.contains(eachp)){
				if(neighbors.size()==3){
					for(Position eachneighbor: neighbors)
						if(eachneighbor.getX()==eachp.getX()&&eachneighbor.getY()==eachp.getY()){
							t.setTransitionProb(eachp, eachneighbor, 0.5);
						}else{
							t.setTransitionProb(eachp, eachneighbor, 0.25);
						}
				}else if(neighbors.size()==4){
					for(Position eachneighbor: neighbors){
						t.setTransitionProb(eachp, eachneighbor,0.25);
					}
				}
			}else{
				for(Position eachneighbor: neighbors){
					t.setTransitionProb(eachp, eachneighbor,0.25);
				}
			
			}
			
		}
		return t;
	}
	

}
