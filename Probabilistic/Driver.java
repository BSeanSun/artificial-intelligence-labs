package ProbabilisticSolver;

import java.util.ArrayList;

public class Driver {
	public static int R = 0;
	public static int G = 1;
	public static int B = 2;
	public static int Y = 3;
	public static void main(String[] args)
	{
	  Maze m = new Maze(4,4);
	  
	  m.setColorandPosition(0, 0, G);
	  m.setColorandPosition(0, 1, R);
	  m.setColorandPosition(0, 2, Y);
	  m.setColorandPosition(0, 3, B);
	  m.setColorandPosition(1, 0, Y);
	  m.setColorandPosition(1, 1, G);
	  m.setColorandPosition(1, 2, R);
	  m.setColorandPosition(1, 3, Y);
	  m.setColorandPosition(2, 0, G);
	  m.setColorandPosition(2, 1, Y);
	  m.setColorandPosition(2, 2, B);
	  m.setColorandPosition(2, 3, R);
	  m.setColorandPosition(3, 0, R);
	  m.setColorandPosition(3, 1, B);
	  m.setColorandPosition(3, 2, Y);
	  m.setColorandPosition(3, 3, B);
	  System.out.println("The original maze is: ");
	  m.printMaze();
	  System.out.println("********************************************************");
	  Position p00 = m.p.get(0);
	  Position p01 = m.p.get(1);
	  Position p02 = m.p.get(2);
	  Position p03 = m.p.get(3);
	  Position p10 = m.p.get(4);
	  Position p11 = m.p.get(5);
	  Position p12 = m.p.get(6);
	  Position p13 = m.p.get(7);
	  Position p20 = m.p.get(8);
	  Position p21 = m.p.get(9);
	  Position p22 = m.p.get(10);
	  Position p23 = m.p.get(11);
	  Position p30 = m.p.get(12);
	  Position p31 = m.p.get(13);
	  Position p32 = m.p.get(14);
	  Position p33 = m.p.get(15);
	  TransitionModel t = new TransitionModel(4,4);
	  /*t.setTransitionProb(p00, p00, 0.5);
	  t.setTransitionProb(p00, p01, 0.25);
	  t.setTransitionProb(p00, p10, 0.25);
	  
	  t.setTransitionProb(p01, p01, 0.25);
	  t.setTransitionProb(p01, p00, 0.25);
	  t.setTransitionProb(p01, p02, 0.25);
	  t.setTransitionProb(p01, p11, 0.25);
	  
	  t.setTransitionProb(p02, p02, 0.25);
	  t.setTransitionProb(p02, p01, 0.25);
	  t.setTransitionProb(p02, p12, 0.25);
	  t.setTransitionProb(p02, p03, 0.25);
	  
	  t.setTransitionProb(p03, p03, 0.5);
	  t.setTransitionProb(p03, p02, 0.25);
	  t.setTransitionProb(p03, p13, 0.25);
	  
	  t.setTransitionProb(p10, p10, 0.25);
	  t.setTransitionProb(p10, p00, 0.25);
	  t.setTransitionProb(p10, p20, 0.25);
	  t.setTransitionProb(p10, p11, 0.25);
	  
	  t.setTransitionProb(p11, p01, 0.25);
	  t.setTransitionProb(p11, p10, 0.25);
	  t.setTransitionProb(p11, p21, 0.25);
	  t.setTransitionProb(p11, p12, 0.25);
	  
	  t.setTransitionProb(p12, p02, 0.25);
	  t.setTransitionProb(p12, p11, 0.25);
	  t.setTransitionProb(p12, p22, 0.25);
	  t.setTransitionProb(p12, p13, 0.25);
	  
	  t.setTransitionProb(p13, p03, 0.25);
	  t.setTransitionProb(p13, p13, 0.25);
	  t.setTransitionProb(p13, p23, 0.25);
	  t.setTransitionProb(p13, p12, 0.25);
	  
	  t.setTransitionProb(p20, p10, 0.25);
	  t.setTransitionProb(p20, p20, 0.25);
	  t.setTransitionProb(p20, p30, 0.25);
	  t.setTransitionProb(p20, p21, 0.25);
	  
	  t.setTransitionProb(p21, p11, 0.25);
	  t.setTransitionProb(p21, p20, 0.25);
	  t.setTransitionProb(p21, p31, 0.25);
	  t.setTransitionProb(p21, p22, 0.25);
	  
	  t.setTransitionProb(p22, p21, 0.25);
	  t.setTransitionProb(p22, p12, 0.25);
	  t.setTransitionProb(p22, p32, 0.25);
	  t.setTransitionProb(p22, p23, 0.25);
	 
	  t.setTransitionProb(p23, p13, 0.25);
	  t.setTransitionProb(p23, p23, 0.25);
	  t.setTransitionProb(p23, p33, 0.25);
	  t.setTransitionProb(p23, p22, 0.25);
	  
	  t.setTransitionProb(p30, p30, 0.5);
	  t.setTransitionProb(p30, p20, 0.25);
	  t.setTransitionProb(p30, p31, 0.25);
	  
	  t.setTransitionProb(p31, p31, 0.25);
	  t.setTransitionProb(p31, p30, 0.25);
	  t.setTransitionProb(p31, p21, 0.25);
	  t.setTransitionProb(p31, p32, 0.25);

	  t.setTransitionProb(p32, p32, 0.25);
	  t.setTransitionProb(p32, p22, 0.25);
	  t.setTransitionProb(p32, p31, 0.25);
	  t.setTransitionProb(p32, p33, 0.25);
	  
	  t.setTransitionProb(p33, p33, 0.5);
	  t.setTransitionProb(p33, p23, 0.25);
	  t.setTransitionProb(p33, p32, 0.25);*/
	  
	  /*ArrayList<Position> neighbors00 = new ArrayList<Position>();
	  neighbors00.add(p00);
	  neighbors00.add(p01);
	  neighbors00.add(p10);
	  t.setRelationship(p00, neighbors00);
	  ArrayList<Position> neighbors01 = new ArrayList<Position>();
	  neighbors01.add(p01);
	  neighbors01.add(p00);
	  neighbors01.add(p11);
	  neighbors01.add(p02);
	  t.setRelationship(p01, neighbors01);
	  ArrayList<Position> neighbors02 = new ArrayList<Position>();
	  neighbors02.add(p01);
	  neighbors02.add(p02);
	  neighbors02.add(p12);
	  neighbors02.add(p03);
	  t.setRelationship(p02, neighbors02);
	  ArrayList<Position> neighbors03 = new ArrayList<Position>();
	  neighbors03.add(p03);
	  neighbors03.add(p02);
	  neighbors03.add(p13);
	  t.setRelationship(p03, neighbors03);
	  ArrayList<Position> neighbors10 = new ArrayList<Position>();
	  neighbors10.add(p00);
	  neighbors10.add(p10);
	  neighbors10.add(p20);
	  neighbors10.add(p11);
	  t.setRelationship(p10, neighbors10);
	  ArrayList<Position> neighbors11 = new ArrayList<Position>();
	  neighbors11.add(p01);
	  neighbors11.add(p10);
	  neighbors11.add(p21);
	  neighbors11.add(p12);
	  t.setRelationship(p11, neighbors11);
	  ArrayList<Position> neighbors12 = new ArrayList<Position>();
	  neighbors12.add(p02);
	  neighbors12.add(p11);
	  neighbors12.add(p22);
	  neighbors12.add(p13);
	  t.setRelationship(p12, neighbors12);
	  ArrayList<Position> neighbors13 = new ArrayList<Position>();
	  neighbors13.add(p13);
	  neighbors13.add(p03);
	  neighbors13.add(p12);
	  neighbors13.add(p23);
	  t.setRelationship(p13, neighbors13);
	  ArrayList<Position> neighbors20 = new ArrayList<Position>();
	  neighbors20.add(p20);
	  neighbors20.add(p10);
	  neighbors20.add(p30);
	  neighbors20.add(p21);
	  t.setRelationship(p20, neighbors20);
	  ArrayList<Position> neighbors21 = new ArrayList<Position>();
	  neighbors21.add(p11);
	  neighbors21.add(p20);
	  neighbors21.add(p31);
	  neighbors21.add(p22);
	  t.setRelationship(p21, neighbors21);
	  ArrayList<Position> neighbors22 = new ArrayList<Position>();
	  neighbors22.add(p12);
	  neighbors22.add(p21);
	  neighbors22.add(p32);
	  neighbors22.add(p23);
	  t.setRelationship(p22, neighbors22);
	  ArrayList<Position> neighbors23 = new ArrayList<Position>();
	  neighbors23.add(p23);
	  neighbors23.add(p13);
	  neighbors23.add(p22);
	  neighbors23.add(p33);
	  t.setRelationship(p23, neighbors23);
	  ArrayList<Position> neighbors30 = new ArrayList<Position>();
	  neighbors30.add(p30);
	  neighbors30.add(p20);
	  neighbors30.add(p31);
	  t.setRelationship(p30, neighbors30);
	  ArrayList<Position> neighbors31 = new ArrayList<Position>();
	  neighbors31.add(p31);
	  neighbors31.add(p30);
	  neighbors31.add(p21);
	  neighbors31.add(p32);
	  t.setRelationship(p31, neighbors31);
	  ArrayList<Position> neighbors32 = new ArrayList<Position>();
	  neighbors32.add(p32);
	  neighbors32.add(p31);
	  neighbors32.add(p22);
	  neighbors32.add(p33);
	  t.setRelationship(p32, neighbors32);
	  ArrayList<Position> neighbors33 = new ArrayList<Position>();
	  neighbors33.add(p33);
	  neighbors33.add(p23);
	  neighbors33.add(p32);
	  t.setRelationship(p33, neighbors33);*/
	  
	  //把每个格子的neighbors set进去
	  t = t.setTransitionMatrix(m.p, t);
      t = t.CalculateTransitionMatrix(m, t);
	  
	  int [] sequence = {Y};
	  SensorModel s = new SensorModel(sequence,t);
	  s.updateProb(m);
	  
	  double max = 0.0;
	  int maxi = -1;
	  int maxj = -1;
	  for(int i =0;i<m.width;i++){
		  for (int j = 0; j <m.height;j++){
			 if( m.getProb(i, j)>max){
				 max = m.getProb(i, j);
				 maxi = i;
				 maxj = j;
			 }
		  }
	  }
	  
	  ArrayList<Position> possibleP = new ArrayList<Position>();
	  for(int i = 0;i<m.width;i++){
		  for(int j = 0;j<m.height;j++){
			  if(m.getProb(i, j)==max){
				  Position needToBeAdd = new Position(i,j);
				  possibleP.add(needToBeAdd);
			  }
		  }
	  }
/*	  System.out.println("********************************************************");
	  System.out.println("The new probablity maze is: ");
	  m.printMaze();
	  System.out.print("Most possible position: ");
	  m.getPosition(possibleP);*/
	}
}
