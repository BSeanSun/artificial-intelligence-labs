package chai;
import java.util.Random;

import chesspresso.move.IllegalMoveException;
import chesspresso.position.Position;

public class EVAminiMaX implements ChessAI{
	int maxdepth;
	short bestmove;//At each depth, the best move might be saved in an instance variable
	long states=0;
	int tempdepth=0;
	int evluationscore=0;
	int bestevluationscore=0;
	int maxplayer;
	int minplayer;
	
	public EVAminiMaX(int maxdepth){
		this.maxdepth = maxdepth;
	}
	
	
	public void minimax_decision(Position position) throws IllegalMoveException{
		for (int j = 1; j <= maxdepth; j++){
			states = 0;
		maxplayer = position.getToPlay();
		short[] moves = position.getAllMoves();
		minplayer = 1-maxplayer;
		//bestmove = moves[0];
		if(moves.length==0){
			bestmove = -1000;
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
		if(bestevluationscore == Integer.MAX_VALUE){
			tempdepth =j;
			return;
		}
		}
		tempdepth = this.maxdepth;
	}
	
	public int minimax(Position p, int depth){
			return min_value(p,depth);
	}

	
	public int max_value(Position p, int depth){
		//System.out.println("max value is called");
		int best = Integer.MIN_VALUE;
		if(this.cutoffTest(p, depth)==true){
			//System.out.println("Maximum depth: "+depth);
			//System.out.println("Number of states has been visited: "+states);
			//tempdepth=this.maxdepth-depth;
			return utility(p,maxplayer);
			//return evaluate(p,0);
		}

		
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
				int val = min_value(p,depth-1);
				if(val>best){
					best = val;
				}
				p.undoMove();
				
			}
		}
		return best;
	}
	
	public int min_value(Position p, int depth){
		//System.out.println("min");
		int best = Integer.MAX_VALUE;
		if(this.cutoffTest(p, depth)==true){
			//System.out.println("Maximum depth: "+depth);
			//System.out.println("Number of states has been visited: "+states);
			//tempdepth=this.maxdepth-depth;
			return utility(p,minplayer);
			//return evaluate(p,1);
		}
		
		
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
				int val = max_value(p,depth-1);
				if(val<best){
					best = val;
				}
				p.undoMove();
				
			}
		}
		return best;
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
				evluationscore =  (int) evaluate(p,player);
				return (int) evaluate(p,player);
		}
	}

/*	public int evaluate(Position p,int player){
		int materialscore = Math.abs(p.getMaterial());
		int dominationscore = (int) Math.abs(p.getDomination());
	    int score = materialscore + dominationscore;
	    if(player==maxplayer){
	    return -score;
	    }else{
	    	return score;
	    }
	}*/
	
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
		
		if(bestmove == -1000){
			//System.out.println("Winning move evluation score: "+evluationscore);
			return bestmove;
		}else{
		System.out.println("Maximum depth: "+tempdepth);
		System.out.println("Number of states has been visited: "+states);
		System.out.println("Evluation score: "+evluationscore);

		states=0;
		tempdepth=0;
		return bestmove;
		}
	}
}
