package csp_solver;

import java.util.ArrayList;
import java.util.HashMap;

public class CSPCircuitClient {
	public static int A = 0;
	public static int B = 1;
	public static int C = 2;
	public static int E = 3;

	
	
	public static void main(String args[]) {
		
		double startTime0 = System.nanoTime(); 
		ArrayList<Integer> variables = new ArrayList<Integer>();
		variables.add(A);
		variables.add(B);
		variables.add(C);
		variables.add(E);
		
		HashMap<Integer,ArrayList<Integer>> domains = new HashMap<Integer,ArrayList<Integer>>();
		ArrayList<Integer> domainlist = new ArrayList<Integer>();
		
		for(int j=0;j<20;j=j+10){
			for(int i=j;i<8;i++){
				domainlist.add(i);
			}
		}
		domains.put(A, domainlist);
		domainlist.clear();
		
		for(int j=0;j<20;j=j+10){
			for(int i=j;i<6;i++){
				domainlist.add(i);
			}
		}
		domains.put(B, domainlist);
		domainlist.clear();
		

		for(int i=0;i<9;i++){
			domainlist.add(i);
		}
		domains.put(C, domainlist);
		domainlist.clear();
		
		for(int j=0;j<30;j=j+10){
			for(int i=j;i<4;i++){
				domainlist.add(i);
			}
		}
		domains.put(E, domainlist);
		domainlist.clear();
		
		
    
		ArrayList<Constraint> cons = new ArrayList<Constraint>();
		cons.add(new Constraint(A,B));
		cons.add(new Constraint(A,C));
		cons.add(new Constraint(A,E));
		cons.add(new Constraint(B,C));
		cons.add(new Constraint(B,E));
		cons.add(new Constraint(C,E));
		//构造大node
		ConstraintSatisfactionProblem mapProblem = new ConstraintSatisfactionProblem(variables,domainlist,cons);
	   
		for (int var : mapProblem.getVariables()){
			ArrayList<Integer> d = new ArrayList<Integer>();
			for(int dd: domainlist){
				d.add(dd);
			}
			mapProblem.setVarDomain(var, d);
		}
		double endTime0 = System.nanoTime();  
        double time0 = (endTime0 - startTime0)/10000;
	}
}
