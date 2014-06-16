package assignment_mazeworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import assignment_mazeworld.SearchProblem.SearchNode;
import assignment_mazeworld.MultiMazeProblem.MultiMazeNode;

public class MultiMazeDriver extends Application {

	Maze maze;
	
	// instance variables used for graphical display
	private static final int PIXELS_PER_SQUARE = 12;
	MazeView mazeView;
	List<AnimationPath> animationPathList;
	
	
	// some basic initialization of the graphics; needs to be done before 
	//  runSearches, so that the mazeView is available
	private void initMazeView() {
		maze = Maze.readFromFile("simple.maz");
		//maze = Maze.randomGenerator(Maze.mazeSize);
		animationPathList = new ArrayList<AnimationPath>();
		// build the board
		mazeView = new MazeView(maze, PIXELS_PER_SQUARE);
		
	}
	
	// assumes maze and mazeView instance variables are already available
	private void runSearches() {
		
		int[] msx = new int[Maze.k];
		int[] msy = new int[Maze.k];
		int[] mgx = new int[Maze.k];
		int[] mgy = new int[Maze.k];
		
		//setting starting and goal postions for each robots
	/*	for(int i = 0; i<Maze.k;i++){
			Random r = new Random();
			msx[i] = r.nextInt(Maze.mazeSize);
			msy[i] = r.nextInt(Maze.mazeSize);
			mgx[i] = r.nextInt(Maze.mazeSize);
			mgy[i] = r.nextInt(Maze.mazeSize);
			
		}
	*/	
		
		
		for(int i = 0; i<Maze.k;i++){
			msx[i] = 0;
			msy[i] = i;
			mgx[i] = 6;
			mgy[i] = i;
			
		}
	/*	
		msx[0] = 0;
		msy[0] = 0;
		msx[1] = 0;
		msy[1] = 1;
		//msx[2] = 0;
		//msy[2] = 2;
		
		mgx[0] = 39;
		mgy[0] = 39;
		mgx[1] = 39;
		mgy[1] = 38;
		//mgx[2] = 39;
	//	mgy[2] = 2;
		*/
		MultiMazeProblem mazeProblem = new MultiMazeProblem(maze, msx, msy, mgx,
				mgy);

		List<SearchNode> astarPath = mazeProblem.astarSearch();
	    if(astarPath==null)
	    {
	    	System.out.println("路线没找到");
	    	return;
	    }
	    
	  /*  for(int i = 0 ; i<Maze.k; i++){
	    	List<SearchNode> subpath = new ArrayList<SearchNode>();
	    	for(int j = 0; j<astarPath.size();j++){
	    		int [] node = new int[Maze.k];
	    		node = astarPath.get(j);
	    	}
	    }
	    */
	    
		animationPathList.add(new AnimationPath(mazeView, astarPath));
		System.out.println("A*:  ");	
		System.out.println("A* path length:  " + astarPath.size() + " " + astarPath);
		mazeProblem.printStats();
	/*	
		List<SearchNode> bfsPath = mazeProblem.breadthFirstSearch();
	    if(bfsPath==null)
	    {
	    	System.out.println("路线没找到");
	    	return;
	    }
		//animationPathList.add(new AnimationPath(mazeView, astarPath));
		System.out.println("bfs:  ");	
		System.out.println("bfs path length:  " + bfsPath.size() + " " + bfsPath);
		//mazeProblem.printStats();
*/
		
		
	}


	public static void main(String[] args) {
		launch(args);
	}

	// javafx setup of main view window for mazeworld
	@Override
	public void start(Stage primaryStage) {
		
		initMazeView();
	
		primaryStage.setTitle("CS 76 Mazeworld");

		// add everything to a root stackpane, and then to the main window
		StackPane root = new StackPane();
		root.getChildren().add(mazeView);
		primaryStage.setScene(new Scene(root));

		primaryStage.show();

		// do the real work of the driver; run search tests
		runSearches();


		// sets mazeworld's game loop (a javafx Timeline)
		Timeline timeline = new Timeline(1.0);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(.05), new GameHandler()));
		timeline.playFromStart();

	}

	// every frame, this method gets called and tries to do the next move
	//  for each animationPath.
	private class GameHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			// System.out.println("timer fired");

			
			for (AnimationPath animationPath : animationPathList) {
				// note:  animationPath.doNextMove() does nothing if the
				//  previous animation is not complete.  If previous is complete,
				//  then a new animation of a piece is started.
				
				animationPath.doNextMove();
			}
			
		}
	}

	// each animation path needs to keep track of some information:
	// the underlying search path, the "piece" object used for animation,
	// etc.
	private class AnimationPath {
		private Node[] piece = new Node[Maze.k];
		private List<SearchNode> searchPath;
		private int currentMove = 0;

		private int[] lastX = new int[Maze.k];
		private int[] lastY = new int[Maze.k];

		boolean animationDone = true;

		public AnimationPath(MazeView mazeView, List<SearchNode> path) {
			searchPath = path;
			MultiMazeNode firstNode = (MultiMazeNode) searchPath.get(0);
			for(int i = 0; i<Maze.k; i++){
				piece[i] = mazeView.addPiece(firstNode.getX(i), firstNode.getY(i));
				lastX[i] = firstNode.getX(i);
				lastY[i] = firstNode.getY(i);
			}
				
			
		}

		// try to do the next step of the animation. Do nothing if
		// the mazeView is not ready for another step.
		public void doNextMove() {

			// animationDone is an instance variable that is updated
			//  using a callback triggered when the current animation
			//  is complete
			int dx;
			int dy;
			
			//if(currentMove >= searchPath.size()){
				//return;
			//}
			
			
			
			
				if (currentMove < searchPath.size() && animationDone) {
					MultiMazeNode mazeNode = (MultiMazeNode) searchPath
							.get(currentMove);
					for(int i= 0; i<Maze.k;i++){
						dx = mazeNode.getX(i) - lastX[i];
						dy = mazeNode.getY(i) - lastY[i];
						// System.out.println("animating " + dx + " " + dy);
						animateMove(piece[i], dx, dy);
						lastX[i] = mazeNode.getX(i);
						lastY[i] = mazeNode.getY(i);
					}
					currentMove++;
					//System.out.println(currentMove);
					
					}			
			
		}

		// move the piece n by dx, dy cells
		public void animateMove(Node n, int dx, int dy) {
			animationDone = false;
			TranslateTransition tt = new TranslateTransition(
					Duration.millis(300), n);
				tt.setByX(PIXELS_PER_SQUARE * dx);
				tt.setByY(-PIXELS_PER_SQUARE * dy);
			
			// set a callback to trigger when animation is finished
			tt.setOnFinished(new AnimationFinished());

			tt.play();

		}

		// when the animation is finished, set an instance variable flag
		//  that is used to see if the path is ready for the next step in the
		//  animation
		private class AnimationFinished implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) {
				animationDone = true;
			}
		}
	}
}