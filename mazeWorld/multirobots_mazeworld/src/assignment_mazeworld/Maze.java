package assignment_mazeworld;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Maze {
	final static Charset ENCODING = StandardCharsets.UTF_8;

	// A few useful constants to describe actions
	//a single robot can have five possible actions.
	public static int[] NORTH = {0, 1};
	public static int[] EAST = {1, 0};
	public static int[] SOUTH = {0, -1};
	public static int[] WEST = {-1, 0};
	public static int[] NOTMOVE = {0, 0};
	
	//A few settings of the maze map
	public int width;
	public int height;
	private char[][] grid;
    public static int wallSize = 1;
    public static int mazeSize = 7;
	//The number of robots on the robots
	public static int k = 2;
	
	public static Maze randomGenerator(int mazeSize){
		Maze m = new Maze();
		m.height = mazeSize;
		m.width = mazeSize;
		
		m.grid = new char[m.height][m.width];
		int[] draw = new int [mazeSize*mazeSize];
		
		for(int i = 0; i < Maze.wallSize;i++){
			
			Random rand = new Random(); 
			int temp = rand.nextInt(mazeSize*mazeSize);
			draw[temp] = 1;				
        }
		int count = -1;
		for(int i = 0 ; i < mazeSize; i++) {
			for (int j = 0 ; j < mazeSize; j++){
				count ++;				
				if (draw[count] == 1 )
					m.grid[j][i] = '#';
				else 
					m.grid[j][i] = '.';
			}
				
		}
		
		return m;
	}
	
	
	public static Maze readFromFile(String filename) {
		Maze m = new Maze();

		try {
			List<String> lines = readFile(filename);
			m.height = lines.size();

			int y = 0;
			m.grid = new char[m.height][];
			for (String line : lines) {
				m.width = line.length();
				m.grid[m.height - y - 1] = new char[m.width];
				for (int x = 0; x < line.length(); x++) {
					// (0, 0) should be bottom left, so flip y as 
					//  we read from file into array:
					m.grid[m.height - y - 1][x] = line.charAt(x);
				}
				y++;

				// System.out.println(line.length());
			}

			return m;
		} catch (IOException E) {
			return null;
		}
	}

	private static List<String> readFile(String fileName) throws IOException {
		Path path = Paths.get(fileName);
		return Files.readAllLines(path, ENCODING);
	}

	public char getChar(int x, int y) {
		return grid[y][x];
	}
	
	// is the location x, y on the map, and also a legal floor tile (not a wall)?
	public boolean isLegal(int[] xnew, int[] ynew) {
		// on the map
		//int flag = 0;
		for(int i=0; i<Maze.k;i++){
			if(xnew[i] < 0 || 
					xnew[i] >= width || 
					ynew[i] < 0 || 
							ynew[i] >= height) {
				return false;
			}else{
					for(int j= i+1;j<Maze.k;j++)
					{
						if(xnew[j]==xnew[i]&&ynew[j]==ynew[i])
							return false;
					}
					
					if(getChar(xnew[i],ynew[i])=='#')
					{
						return false;
					}
				
			}
				
		
	}
		return true;
	}
	
	
	public String toString() {
		String s = "";
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				s += grid[y][x];
			}
			s += "\n";
		}
		return s;
	}

	public static void main(String args[]) {
		Maze m = Maze.readFromFile("simple.maz");
		System.out.println(m);
	}

}
