package assignment_robots;


import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.shape.Polygon;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class CarDriver extends Application {
	// default window size
	protected int window_width = 600;
	protected int window_height = 400;
	
	// Draw a polygon;
	public void addPolygon(Group g, Double[] points) {
		Polygon p = new Polygon();
	    p.getPoints().addAll(points);
	    
	    g.getChildren().add(p);
	}
	
	// plot a car robot
	public void plotCarRobot(Group g, CarRobot car, CarState s) {
		//System.out.println(car);
		//System.out.println(s);
		car.set(s);
		double[][] current = car.get();
		Double[] to_add = new Double[2*current.length];
		for (int j = 0; j < current.length; j++) {
			System.out.println(current[j][0] + ", " + current[j][1]);
			to_add[2*j] = current[j][0];
			//to_add[2*j+1] = current[j][1];
			to_add[2*j+1] = window_height - current[j][1];
		}
		Polygon p = new Polygon();
		p.getPoints().addAll(to_add);
		
		p.setStroke(Color.RED);
		p.setFill(Color.PINK);
		g.getChildren().add(p);
	}
		
	// plot the World with all the obstacles;
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
		
		
	
		primaryStage.setTitle("CS 76 2D world");
		Group root = new Group();
		Scene scene = new Scene(root, window_width, window_height);

		primaryStage.setScene(scene);
		
		Group g = new Group();
		
		double a[][] = {{100, 400}, {600, 400}, {600, 370},{100,370}};
		Poly obstacle1 = new Poly(a);
		
		double b[][] = {{240, 230}, {270, 230}, {420, 30},{390, 30}};

		Poly obstacle2 = new Poly(b);
		
		double c[][] = {{100, 120},{280, 120},{250, 380}};
		Poly obstacle3 = new Poly(c);
		
		double d[][] = {{28, 90}, {200, 30}, {200, 0}, {28, 0}};
		Poly obstacle4 = new Poly(d);
		
		double e[][] = {{450, 90}, {500, 90}, {500, 0}, {450, 0}};
		Poly obstacle5 = new Poly(e);
		
		// Declaring a world; 
		World w = new World();
		// Add obstacles to the world;
		w.addObstacle(obstacle1);
		w.addObstacle(obstacle2);
		//w.addObstacle(obstacle3);
		w.addObstacle(obstacle4);
		w.addObstacle(obstacle5);	
		plotWorld(g, w);
		
		CarRobot car = new CarRobot();
		
		CarState state1 = new CarState(270, 15, 0);
	    // Set CarState;
		car.set(state1);
		
		boolean collided = w.carCollisionPath(car, state1, 0, 1.2);
	    System.out.println(collided);
		//plotCarRobot(g, car, state1);
		
		RRTPlanner r = new RRTPlanner(400,100,100,10);
		Tree np = r.CreateTree(w);
		List<CarRobot> path = new LinkedList<CarRobot>();
		path = np.mVertices;
		
		for(CarRobot pathchild:path){
			plotCarRobot(g, car, pathchild.s);
		}
		System.out.println(path);

		 System.out.println(np.mEdges.size());
		 System.out.println(np.mVertices.size());
	    scene.setRoot(g);
	    primaryStage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
