package chai;

import java.util.Random;

import chesspresso.position.Position;

public class RandomAI implements ChessAI {
	public short getMove(Position position) {
		short [] moves = position.getAllMoves();
		
		if(moves.length==0){
			short move = -1000;
			return move;
		}else{
			short move = moves[new Random().nextInt(moves.length)];
			return move;
		}
		
	}
}
