package csp_solver;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;



public class CSPClient {
	/*public static final int NSW = 0;
	public static final int NT = 1;
	public static final int Q = 2;
	public static final int SA = 3;
	public static final int T = 4;
	public static final int V = 5;
	public static final int WA = 6;
	public static final int RED = 0;
	public static final int GREEN = 1;
	public static final int BLUE = 2;*/
	public static int WA = 0;
	public static int SA = 1;
	public static int NT = 2;
	public static int Q = 3;
	public static int NSW = 4;
	public static int V = 5;
	public static int T = 6;
	
	public static int RED = 0;
	public static int GREEN = 1;
	public static int BLUE = 2;
	
	public static void main(String args[]) {
		
		double startTime0 = System.nanoTime(); 
		ArrayList<Integer> variables = new ArrayList<Integer>();
		variables.add(NSW);
		variables.add(NT);
		variables.add(Q);
		variables.add(SA);
		variables.add(T);
		variables.add(V);
		variables.add(WA);
		
		ArrayList<Integer> domains = new ArrayList<Integer>();
		domains.add(RED);
		domains.add(GREEN);
		domains.add(BLUE);
    
		ArrayList<Constraint> cons = new ArrayList<Constraint>();
		cons.add(new Constraint(WA,NT));
		cons.add(new Constraint(WA,SA));
		cons.add(new Constraint(NT,SA));
		cons.add(new Constraint(NT,Q));
		cons.add(new Constraint(SA,Q));
		cons.add(new Constraint(SA,NSW));
		cons.add(new Constraint(SA,V));
		cons.add(new Constraint(Q,NSW));
		cons.add(new Constraint(NSW,V));
		//构造大node
		ConstraintSatisfactionProblem mapProblem = new ConstraintSatisfactionProblem(variables,domains,cons);
	   
/*		for (int var : mapProblem.getVariables()){
			ArrayList<Integer> d = new ArrayList<Integer>();
			for(int dd: domains){
				d.add(dd);
			}
			mapProblem.setVarDomain(var, d);
		}*/
		double endTime0 = System.nanoTime();  
        double time0 = (endTime0 - startTime0)/10000;
		
		
		
		
		double startTime = System.nanoTime(); 
		//构造原始搜索
		Assignment assignment = new Assignment(variables);  
	    BackTracking search = new BackTracking();
	    Assignment result = search.recursiveBackTrackingSearch(mapProblem, assignment);
	    double endTime = System.nanoTime(); 
        double time = (endTime - startTime)/1000000;
        DecimalFormat df = new DecimalFormat();
		System.out.println("Original BTS: "+result);
		System.out.println("Time only run search: "+time);
		System.out.println();
		
		
		//构造MRV搜索
		double startTime1 = System.nanoTime();  
		Assignment assignment1 = new Assignment(variables);  
	    MRVBackTracking search1 = new MRVBackTracking();
	    Assignment result1 = search1.recursiveBackTrackingSearch(mapProblem, assignment1);
	    double endTime1 = System.nanoTime(); 
        double time1 = (endTime1 - startTime1)/1000000;
        DecimalFormat df1 = new DecimalFormat();
		System.out.println("MRV BTS: "+result1);
		System.out.println("Time only run search: "+time1);
		System.out.println();
		
		//构造DegreeHeuristic搜索
		double startTime2 = System.nanoTime(); ; 
		Assignment assignment2 = new Assignment(variables);  
	    DegreeBackTracking search2 = new DegreeBackTracking();
	    Assignment result2 = search2.recursiveBackTrackingSearch(mapProblem, assignment2);
		double endTime2 = System.nanoTime(); ; 
		double time2 = (endTime2 - startTime2)/1000000;
		DecimalFormat df2 = new DecimalFormat();
		System.out.println("Dgree BTS: "+result2);
		System.out.println("Time only run search: "+time2);
		System.out.println();
		
		//MAC3搜索
		double startTime3 = System.nanoTime(); ; 
		Assignment assignment3 = new Assignment(variables);  
	    MAC3BackTracking search3 = new MAC3BackTracking();
	    Assignment result3 = search3.recursiveBackTrackingSearch(mapProblem, assignment3);
		double endTime3 = System.nanoTime(); ; 
		double time3 = (endTime3 - startTime3)/1000000;
		System.out.println("MAC3 BTS: "+result3);
		System.out.println("Time only run search: "+time3);
	}
}
