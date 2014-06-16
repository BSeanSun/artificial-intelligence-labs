package assignment_robots;

import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.shape.Polygon;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class ArmDriver extends Application {
	// default window size
	protected int window_width = 600;
	protected int window_height = 400;
	
	public void addPolygon(Group g, Double[] points) {
		Polygon p = new Polygon();
	    p.getPoints().addAll(points);
	    
	    g.getChildren().add(p);
	}
	
	// plot a ArmRobot;
	public void plotArmRobot(Group g, ArmRobot arm, double[] config) {
		arm.set(config);
		double[][] current;
		Double[] to_add;
		Polygon p;
		for (int i = 1; i <= arm.getLinks(); i++) {
			current = arm.getLinkBox(i);
			
			
			to_add = new Double[2*current.length];
			for (int j = 0; j < current.length; j++) {
				System.out.println(current[j][0] + ", " + current[j][1]);
				to_add[2*j] = current[j][0];
				//to_add[2*j+1] = current[j][1];
				to_add[2*j+1] = window_height - current[j][1];
			}
			p = new Polygon();
			p.getPoints().addAll(to_add);
			p.setStroke(Color.BLUE);
			p.setFill(Color.LIGHTBLUE);
			g.getChildren().add(p);
		}
		
	}
	
	public void plotWorld(Group g, World w) {
		int len = w.getNumOfObstacles();
		double[][] current;
		Double[] to_add;
		Polygon p;
		for (int i = 0; i < len; i++) {
			current = w.getObstacle(i);
			to_add = new Double[2*current.length];
			for (int j = 0; j < current.length; j++) {
				to_add[2*j] = current[j][0];
				//to_add[2*j+1] = current[j][1];
				to_add[2*j+1] = window_height - current[j][1];
			}
			p = new Polygon();
			p.getPoints().addAll(to_add);
			g.getChildren().add(p);
		}
	}
	
	// The start function; will call the drawing;
	// You can run your PRM or RRT to find the path; 
	// call them in start; then plot the entire path using
	// interfaces provided;
	@Override
	public void start(Stage primaryStage) {
		
		
		// setting up javafx graphics environments;
		primaryStage.setTitle("CS 76 2D world");

		Group root = new Group();
		Scene scene = new Scene(root, window_width, window_height);

		primaryStage.setScene(scene);
		
		Group g = new Group();

		// setting up the world;
		
		// creating polygon as obstacles;
		

		double a[][] = {{10, 400}, {150, 300}, {100, 210}};
		Poly obstacle1 = new Poly(a);
		
		double b[][] = {{350, 30}, {300, 200}, {430, 125}};

		Poly obstacle2 = new Poly(b);
		
		
		double c[][] = {{110, 220}, {250, 380}, {320, 220}};
		Poly obstacle3 = new Poly(c);
		
		// Declaring a world; 
		World w = new World();
		// Add obstacles to the world;
		//w.addObstacle(obstacle1);
	    w.addObstacle(obstacle2);
		//w.addObstacle(obstacle3);
		
		plotWorld(g, w);
		
		ArmRobot arm = new ArmRobot(2);
		   
		
		PRMPlanner p = new PRMPlanner(15, 100, 70, 80,80);
		//TRIPRMPlanner tp = new TRIPRMPlanner(15,80,50,80,100);

		Graph np = p.CreateGraph(w);
		//Graph tnp = tp.CreateGraph(w);



		List<ArmRobot> path = new LinkedList<ArmRobot>();
		//List<ArmRobot> tpath = new LinkedList<ArmRobot>();

		path =  p.QPhase(np);
		//tpath = tp.QPhase(tnp);
		if(path == null){
			System.out.println("no path");
			return;
		}

	   for(ArmRobot pathchild:path){
			arm.set(pathchild.config);
		plotArmRobot(g, arm,pathchild.config);

		}
		
		//for(ArmRobot pathchild:tpath){
        //    arm.set(pathchild.config);
		//	plotArmRobot(g, arm,pathchild.config);

		//	}

		System.out.println(path);
	    scene.setRoot(g);
	    primaryStage.show();
		

	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
