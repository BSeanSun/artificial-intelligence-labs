package chai;

import chesspresso.Chess;
import chesspresso.game.Game;
import chesspresso.move.IllegalMoveException;
import chesspresso.move.Move;
import chesspresso.position.Position;

public class ChessGame {

	public Position position;

	public int rows = 8;
	public int columns = 8;

	public ChessGame() {
		position = new Position(
				"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		/*position = new Position(
				"k1111111/3Q4/1R6/8/8/8/11111111/K1111111 w KQkq - 0 1");*/

	}
	
	public ChessGame(Game g){
		position = g.getPosition();
	}

	public int getStone(int col, int row) {
		return position.getStone(Chess.coorToSqi(col, row));
	}
	
	public boolean squareOccupied(int sqi) {
		return position.getStone(sqi) != 0;
		
	}

	public boolean legalMove(short move) {
		
		for(short m: position.getAllMoves()) {
			if(m == move) return true;
		}
		System.out.println(java.util.Arrays.toString(position.getAllMoves()));
		System.out.println(move);
		return false;
	
	}

	// find a move from the list of legal moves from fromSqi to toSqi
	// return 0 if none available
	public short findMove(int fromSqi, int toSqi) {
		
		for(short move: position.getAllMoves()) {
			if(Move.getFromSqi(move) == fromSqi && 
					Move.getToSqi(move) == toSqi) return move;
		}
		return 0;
	}
	
	public int checkWin(Position p){
		if(p.isMate()){
			if(p.getToPlay()==1){
				return 1;
				}else{
					return -1;
				}
		}
		return 0 ;
	}
	
	public int checkDraw(Position p){
		//need to implement
		if(p.isStaleMate()){		
			return 1;
		}
		return -1;
	}
	
	public void doMove(short move) {
		try {

			System.out.println("making move " + move);

			position.doMove(move);
			//CHECK WIN CONDITION
			//int a = this.checkWin(position);
			if(position.isMate()==true&&position.getToPlay()==1){
				System.out.println("White wins");
				return;
			}else if(position.isMate()==true&&position.getToPlay()==0){
				System.out.println("Black wins");
				return;
			}else if(position.isTerminal()==true){
				System.out.println("time out");
				return;
			}
			//Check Draw Condition
			//int b = this.checkDraw(position);
			if(position.isStaleMate()==true){
				System.out.println("Draw");
				return;
			}
			
			
			System.out.println(position);
		} catch (IllegalMoveException e) {
			System.out.println("illegal move!");
		}
	}

	public static void main(String[] args) {
		System.out.println();

		// Create a starting position using "Forsyth–Edwards Notation". (See
		// Wikipedia.)
		Position position = new Position(
				"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		/*Position position = new Position(
				"k1111111/3Q4/1R6/8/8/8/11111111/K1111111 w KQkq - 0 1");*/

		System.out.println(position);

	}
	
	

}
