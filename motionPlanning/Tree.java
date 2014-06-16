package assignment_robots;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Tree {

	
	// Vertices in the graph
	public LinkedList<CarRobot> mVertices = new LinkedList<CarRobot>();
	
	// Edges in the graph. A vertex can be adjacent to several other nodes. 
	public HashMap<CarRobot, LinkedList<CarRobot>> mEdges = new HashMap<CarRobot, LinkedList<CarRobot>>();
	
	public Tree(){
		
	}
	
	// Add a vertex to the graph, but it doesn't have edge information.
	public void addVertex( CarRobot vert )
	{
		this.mVertices.add(vert);
	}
	
	// Add Edge vert1 -> vert2
	// vert1 and vert2 have to be vertices in the graph, or it will throw exception.
	public void addEdge( CarRobot vert1, CarRobot vert2 ) throws Exception
	{
		if( this.mVertices.contains(vert1) && this.mVertices.contains(vert2) )
		{
			if( this.mEdges.containsKey(vert1) )
			{
				LinkedList<CarRobot> adjVerts = this.mEdges.get(vert1);
				adjVerts.add( vert2 );
			}
			else
			{
				LinkedList<CarRobot> newSuccs = new LinkedList<CarRobot>();
				newSuccs.add(vert2);
				this.mEdges.put(vert1, newSuccs);
			}
		}
		else
			throw new Exception( "One vertex doesn't exist in the graph" );
	}
	
	// Get successors of a node in the graph.
	public LinkedList<CarRobot> getSuccessor( CarRobot vert )
	{
		LinkedList<CarRobot> succs = null;
		try
		{
			succs = this.mEdges.get(vert);
		}
		catch(Exception e)
		{
			return null;
		}
		return succs;
	}
}
