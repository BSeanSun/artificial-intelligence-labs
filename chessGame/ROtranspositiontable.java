package chai;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import com.sun.javafx.collections.MappingChange.Map;

import chesspresso.move.IllegalMoveException;
import chesspresso.position.Position;

public class ROtranspositiontable implements ChessAI{
	int maxdepth;
	//short bestmove;//At each depth, the best move might be saved in an instance variable
	long states=0;
	int tempdepth=0;
	int currentmaxdepth = 0;
	//int bestevluationscore=0;
	int maxplayer;
	int minplayer;
	int EXACT = 0;
	int UPPER = 1;
	int LOWER = -1;
	short finalmove = 0;
	int finalvalue = Integer.MIN_VALUE;
	
	public ROtranspositiontable(int maxdepth){
		this.maxdepth = maxdepth;
	}
	
	public class tableEntry{
		int type;
		int score;
		int depthleft;
		public tableEntry(int t, int s, int d){
			this.type = t;
			this.score = s;
			this.depthleft = d;
		}

	}
	
	HashMap<Long, tableEntry> table = new HashMap<Long, tableEntry>();
	
	
	void addNodeToTable(HashMap<Long, tableEntry> t,long zobristKey, int TYPE, int SCORE, int DEPTHLEFT)
	{
	// add node with parameters
		tableEntry existingEntry = t.get(zobristKey);
        if (existingEntry == null || DEPTHLEFT >= existingEntry.depthleft) {
            t.put(zobristKey, new tableEntry(TYPE, SCORE, DEPTHLEFT));
        }
	}

	
	
	 public tableEntry getResult(HashMap<Long, tableEntry> t,long zobristKey) {
	        return t.get(zobristKey);
	    }
	
	public void minimax_decision(Position position) throws IllegalMoveException{
		for (int j = 1; j <= maxdepth; j++){
			short bestmove = 0;
			int bestevluationscore = 0;
			states = 0;
			currentmaxdepth =j;
			maxplayer = position.getToPlay();
			short[] moves = position.getAllMoves();
			minplayer = 1-maxplayer;
			//bestmove = moves[0];
			if(moves.length==0){
				finalmove = -1000;
			}else{
				bestmove = moves[0];
				int[] op = new int[moves.length];
				states = states+moves.length;
				int max = -10000;
				short pos = 0;
				for (int i=0;i<moves.length;i++){
					position.doMove(moves[i]);
					op[i] = minimax(position,j);
					if(op[i]>=max){
						max = op[i];
						pos= moves[i];
						bestmove = pos;
						bestevluationscore = max;
					}
				position.undoMove();
				}
			}
			
			//Move reordering
			if(bestevluationscore > finalvalue){
			finalmove = bestmove;
			finalvalue = bestevluationscore;
			}
			if(bestevluationscore == Integer.MAX_VALUE){
				tempdepth =j;
				return;
			}
		}
		tempdepth = this.maxdepth;
	}
	
	public int minimax(Position p, int depth){
			return min_value(Integer.MIN_VALUE,Integer.MAX_VALUE,p,depth);
	}

	
	public int max_value(int alpha, int beta, Position p, int depth){
		if(this.cutoffTest(p, depth)==true){
			return utility(p,maxplayer);
		}
		
	//	tableEntry entry = getResult(table,p.getHashCode());
		/*tableEntry entry = null;
		if(table.containsKey(p.getHashCode()) == true) {
			entry = table.get(p.getHashCode());
		
	    if (entry.depthleft >= (currentmaxdepth-depth))
	    {
	    	System.out.println("maxxxxx"+entry.type+" "+entry.depthleft+" "+entry.score);
	        if (entry.type == 0)
	            return entry.score;
	        else if (entry.type == 1 && entry.score <= alpha)
	                return alpha;
	        else if (entry.type == -1 && entry.score >= beta)
	                return beta;
	    }
		}*/
		
		if (table.containsKey(p.getHashCode()) == true) {
			tableEntry existNode = table.get(p.getHashCode());
			if (existNode.depthleft >= currentmaxdepth - depth){
				//System.out.println("maxxxxx"+existNode.type);
				if (existNode.type == 0){
					//position.undoMove();	
					return existNode.score;	
				}
				//value_min = existNode.value;
				else if (existNode.type == 1 && existNode.score <= alpha){
					//position.undoMove();	
					return alpha;
					//alpha = existNode.score;
				}
				else if (existNode.type == -1 && existNode.score >= beta){
					//position.undoMove();	
					return beta;
					//beta = existNode.score;
				}
			}						
		}


		int v = alpha;
		short [] moves = p.getAllMoves();
		states = states+moves.length;
		if(moves!=null){
			for(short move: moves){
				try {
					p.doMove(move);
				} catch (IllegalMoveException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			    
				int score = min_value(v,beta,p,depth-1);
				
				//p.undoMove();
				
				if(score >v){
					//addNodeToTable(table,p.getHashCode(),0,score,currentmaxdepth-depth);
					v = score;
				}
				if(v >= beta){
					addNodeToTable(table,p.getHashCode(),-1, beta,currentmaxdepth-depth);
					p.undoMove();
					return beta;
				}
				
				
				addNodeToTable(table,p.getHashCode(),0,score,currentmaxdepth-depth);
				p.undoMove();
			}
		}
		//addNodeToTable(table,p.getHashCode(),0,v,currentmaxdepth-depth);
		return v;
	}
	
	public int min_value(int alpha, int beta, Position p, int depth){
		if(this.cutoffTest(p, depth)==true){
			return utility(p,minplayer);

		}
		
	//	tableEntry entry = getResult(table,p.getHashCode());
				/*if(entry!=null)
    		System.out.println("~~取得hashtable值"+entry.type+" "+entry.depthleft+" "+entry.score);*/
		//System.out.println(table.size());
		if (table.containsKey(p.getHashCode()) == true) {
			tableEntry existNode = table.get(p.getHashCode());
			if (existNode.depthleft >= currentmaxdepth - depth){
				//System.out.println("minnnn"+existNode.type);
				if (existNode.type == 0){
					//position.undoMove();	
					return existNode.score;	
				}
				//value_min = existNode.value;
				else if (existNode.type == 1 && existNode.score <= alpha){
					//position.undoMove();	
					return alpha;
					//alpha = existNode.score;
				}
				else if (existNode.type == -1 && existNode.score >= beta){
					//position.undoMove();	
					return beta;
					//beta = existNode.score;
				}
			}						
		}
		
		int v = beta;
		short [] moves = p.getAllMoves();
		states = states+moves.length;
		if(moves!=null){
			for(short move: moves){
				try {
					p.doMove(move);
				} catch (IllegalMoveException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int score = max_value(alpha, v, p,depth-1);
				//p.undoMove();
				 
				if(score< v){
					//addNodeToTable(table,p.getHashCode(),0,score,currentmaxdepth-depth);
					v = score;
				}
				if(v <= alpha){
					//p.undoMove();
					addNodeToTable(table,p.getHashCode(),1, alpha,currentmaxdepth-depth);
					p.undoMove();
					return alpha;
				}
				
				addNodeToTable(table,p.getHashCode(),0,score,currentmaxdepth-depth);
				p.undoMove();
			}
		}
		//addNodeToTable(table,p.getHashCode(),0,v,currentmaxdepth-depth);
		return v;
	}
	
	
	public boolean cutoffTest(Position p,int depth){
		if(p.isMate() == true||p.isStaleMate()==true||depth == 0||p.isTerminal()){
			return true;
		}else{
			return false;
		}
	}
	
	public int utility(Position p,int player){
		if(p.isMate() == true&&player==maxplayer){
			return Integer.MIN_VALUE;
			}else if(p.isMate() == true&&player==minplayer){
				return Integer.MAX_VALUE;
		}else if(p.isStaleMate()==true){
			return 0;
			}else{
				return (int) evaluate(p,player);
		}
	}

	
	public double evaluate(Position position,int player){
		//System.out.println(position.getMaterial());
		double value = position.getMaterial();
		if(player == minplayer){
				return -value - position.getDomination();
			
		}
		else{
				return value + position.getDomination();
				
		}
	}

	@Override
	public short getMove(Position position) {
		// TODO Auto-generated method stub
		try {
			minimax_decision(position);
		} catch (IllegalMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(finalmove == -1000){
			//System.out.println("Winning move evluation score: "+evluationscore);
			return finalmove;
		}else{
		System.out.println("Maximum depth: "+tempdepth);
		System.out.println("Number of states has been visited: "+states);
		System.out.println("Best evluation score: "+finalvalue);
		

		states=0;
		tempdepth=0;
		finalvalue=Integer.MIN_VALUE;
		
		return finalmove;
		}
	}
	
	
}


