package chai;
import java.util.Random;

import chesspresso.move.IllegalMoveException;
import chesspresso.position.Position;

public class NullMoveAlphaBeta implements ChessAI{
	int maxdepth;
	short bestmove;//At each depth, the best move might be saved in an instance variable
	long states=0;
	int tempdepth=0;
	int bestevluationscore=0;
	int maxplayer;
	int minplayer;
	int alpha = Integer.MIN_VALUE;
	int beta = Integer.MAX_VALUE;
	int null_flag = 0;
	public NullMoveAlphaBeta(int maxdepth){
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
			return min_value(Integer.MIN_VALUE,Integer.MAX_VALUE,p,depth);
	}

	
	public int max_value(int alpha, int beta, Position p, int depth){
		if(this.cutoffTest(p, depth)==true){
			return utility(p,maxplayer);
		}

		int score;
		if(!p.isCheck() && null_flag==0 ){
			null_flag = 1;
			p.setToPlay(1-p.getToPlay());
			score = min_value(beta-1,beta,p,depth-3);
			if(score>=beta){
				return score;
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
				score = min_value(v,beta,p,depth-1);
				
				if(score >v){
					v = score;
				}
				if(v >= beta){
					p.undoMove();
					return beta;
				}
				p.undoMove();
				
			}
		}
		return v;
	}
	
	public int min_value(int alpha, int beta, Position p, int depth){
		if(this.cutoffTest(p, depth)==true){
			return utility(p,minplayer);

		}
		
		int score;
		if(!p.isCheck() && null_flag==0 ){
			null_flag = 1;
			p.setToPlay(1-p.getToPlay());
			score = max_value(alpha-1,alpha,p,depth-3);
			if(score<=alpha){
				return score;
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
				score = max_value(alpha, v, p,depth-1);
				
				if(score< v){
					v = score;
				}
				if(v <= alpha){
					p.undoMove();
					return alpha;
				}
				p.undoMove();
				
			}
		}
		return v;
	}
	
	
	public boolean cutoffTest(Position p,int depth){
		if(p.isMate() == true||p.isStaleMate()==true||depth <= 0||p.isTerminal()){
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
		System.out.println("Best evluation score: "+bestevluationscore);

		states=0;
		tempdepth=0;
		bestevluationscore=0;
		
		return bestmove;
		}
	}
}
