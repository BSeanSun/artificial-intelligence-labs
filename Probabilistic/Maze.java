package ProbabilisticSolver;

import java.util.ArrayList;
import java.util.Random;

public class Maze {
	public static int R = 0;
	public static int G = 1;
	public static int B = 2;
	public static int Y = 3;
	
	public int width;
	public int height;
	public int[][] color;
	public double[][] prob;
	public ArrayList<Position> p;
	
	public Maze(int x, int y){
		this.width = x;
		this.height = y;
		this.color = new int[x][y];
		this.prob = new double[x][y];
		this.p = new ArrayList<Position>();
		
		for(int i = 0;i<x;i++){
			for(int j=0;j<y;j++){
				this.color[i][j] = new Random().nextInt(4);
				this.prob[i][j] = 1.0/(this.width*this.height); 
			}
		}
		
	}
	
	
	public void setColorandPosition(int posx, int posy, int color){
		this.color[posx][posy] = color;
		this.p.add(new Position(posx,posy));
	}
	
	public int getColor(int posx, int posy){
		return this.color[posx][posy];
	}
	
	public void setProb(int posx, int posy, double value){
		this.prob[posx][posy] = value;
	}
	
	public double getProb(int posx,int posy){
		return this.prob[posx][posy];
	}
	public void getPosition(ArrayList<Position> possibleP){
		for(Position p:possibleP){
			System.out.print("["+p.getX()+","+p.getY()+"] ");
		}
		
	}
	public void printMaze(){
		int count = 0;
		for(int i = 0; i<this.width;i++){
			for(int j = 0; j<this.height;j++){
				if(this.color[i][j]==R){
					System.out.print(" R:"+"("+i+","+j+"),"+this.getProb(i, j));
					count++;
					if(count==this.height){
						count=0;
						System.out.println();
					}
				}else if(this.color[i][j]==G){
					System.out.print(" G:"+"("+i+","+j+"),"+this.getProb(i, j));
					count++;
					if(count==this.height){
						count=0;
						System.out.println();
					}
				}else if(this.color[i][j]==B){
					System.out.print(" B:"+"("+i+","+j+"),"+this.getProb(i, j));
					count++;
					if(count==this.height){
						count=0;
						System.out.println();
					}
				}else if(this.color[i][j]==Y){
					System.out.print(" Y:"+"("+i+","+j+"),"+this.getProb(i, j));
					count++;
					if(count==this.height){
						count=0;
						System.out.println();
					}
				}
				
			}
		}
		System.out.println();
	}
	
}
