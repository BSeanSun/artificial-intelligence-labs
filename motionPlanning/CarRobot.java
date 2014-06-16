package assignment_robots;

import java.util.Arrays;

// This class declares the class of a steered car;
// Each car has a state, representing its current configuration; 
// Each car has a initial configuration, set to be at origin with angle 0;

public class CarRobot implements Comparable<CarRobot>{


	protected CarState s;
	protected double initial[][] = {{10, 10}, {20, 0}, {10, -10}, {-10, -10}, {-10, 10}};
    private double distance;
	
	public CarRobot () {
		
		s = new CarState(0, 0, 0);
	}

	// set the current configuration of the robot;
	public void set(CarState c) {
		double[] result = c.get();
		s.set(result[0], result[1], result[2]);
	}

	// get the configuration of the current car;
	public CarState getCarState() {
		return s;
	}
	
	// get the current car location and shape;
	// returns a polygon as the car, at current configuration;
	public double[][] get() {
		double[][] rn = new double[5][2];
		double[] current = new double[2];
		
		
		for (int i = 0; i < rn.length; i++) {
			current[0] = initial[i][0] * Math.cos(s.getTheta()) - initial[i][1] * Math.sin(s.getTheta()) + s.getX();
			current[1] = initial[i][0] * Math.sin(s.getTheta()) + initial[i][1] * Math.cos(s.getTheta()) + s.getY();
			rn[i][0] = current[0];
			rn[i][1] = current[1];
		}
		return rn;
	}
	
	public void setDistance(double d){
		this.distance = d;
	}
	
	public double getDistance(){
		return this.distance;
	}
	// Returns the path of a certain control, from 0 to time, with time step;
	// returned path has length ceil(time/step) + 1 (including start);
	// path[i] contains the configuration of the car at time step i;
	public double[][] getPath(CarState start, int ctrl, double time, double step) {
		
		int total = (int) Math.ceil(time/step);
		double[][] path = new double[total+1][3];
		
		SteeredCar planner = new SteeredCar();
		int i = 0;
		CarState c = new CarState();
		c.set(start.getX(), start.getY(), start.getTheta());
		CarState s;
		path[0][0] = start.getX();
		path[0][1] = start.getY();
		path[0][2] = start.getTheta();
		i = 1;
		while (i < total) {
			s = planner.move(c, ctrl, step);
			path[i][0] = s.getX();
			path[i][1] = s.getY();
			path[i][2] = s.getTheta();
			c.set(s.getX(), s.getY(), s.getTheta());
			i += 1;
		}
		s = planner.move(c, ctrl, time-step*(total-1));
		path[i][0] = s.getX();
		path[i][1] = s.getY();
		path[i][2] = s.getTheta();
		
		return path;
	}
	
	@Override
	public boolean equals(Object other) {
		return Arrays.equals(s.get(), ((CarRobot)other).s.get());
	}

	@Override
	public int hashCode() {
		return (int)(s.get()[0] * 100 + s.get()[1]*10 + s.get()[2]); 
	}

	@Override
	public String toString() {
		
		return Arrays.toString(this.s.get());
	}


	@Override
	public int compareTo(CarRobot o) {
		return (int) Math.signum(priority() - o.priority());
	}


//		@Override
		public double priority() {
			return getDistance();
		}

}