package ProbabilisticSolver;

public class Position {
private int x;
private int y;
public Position(int posx,int posy){
	this.x = posx;
	this.y = posy;
}

public void setX(int x){
	this.x = x;
}
public void setY(int y){
	this.y = y;
}

public int getX(){
	return this.x;
}

public int getY(){
	return this.y;
}

public void setPosition(int x,int y){
	this.setX(x);
	this.setY(y);
}

}
