package csp_solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;




/*
 * solves CSPs with variables that are just numbers.
 * 
 */
public class ConstraintSatisfactionProblem {


//variable list
private ArrayList<Integer> var;
//domain list, each domain is an array
private ArrayList<Integer> domain;

private ArrayList<Constraint> constraints;
private Hashtable<Integer, ArrayList<Constraint>> relation;
private Hashtable<Integer, ArrayList<Integer>> vartodom;
//通过构造器set variablelist 和 set domainlist
public ConstraintSatisfactionProblem(ArrayList<Integer> variableList, ArrayList<Integer> domainList,ArrayList<Constraint> c){
	this.var = variableList;
	this.domain = domainList;
	this.constraints = c;
    this.relation = new Hashtable<Integer, ArrayList<Constraint>>();
	this.vartodom = new Hashtable<Integer,ArrayList<Integer>>();
    for (int eachVariable : variableList) {
		this.relation.put(eachVariable, new ArrayList<Constraint>());
		this.vartodom.put(eachVariable, new ArrayList<Integer>());
	}
    
	for(Constraint cu: c){
	for (int v: cu.getPair()){
		relation.get(v).add(cu);
	}
	}
	
	for(int d: domainList){
		for (int v: variableList){
			vartodom.get(v).add(d);
		}
		}
}

//get current variable constraint list
public ArrayList<Constraint> getConstraints(int var) {
	return this.relation.get(var);
	}

public ArrayList<Integer> getVariables() {
	return this.var;
}

public ArrayList<Integer> getDomain(){
	return this.domain;
}

public void setVarDomain(int v,  ArrayList<Integer> d){
	this.vartodom.put(v, d);
}

public ArrayList<Integer> getVarDomain(int v){
	return this.vartodom.get(v);
}

public int getNeighbor(int var, Constraint constraint) {
	/*int[] pair = constraint.getPair();
		if (var == pair[0])
			return pair[1];
		else if (var == pair[1])
			return pair[0];
	return -100;*/
	List<Integer> scope = constraint.getPair();
	if (scope.size() == 2) {
		if (var == scope.get(0))
			return scope.get(1);
		else if (var == scope.get(1))
			return scope.get(0);
	}
	return -199;
}
}

