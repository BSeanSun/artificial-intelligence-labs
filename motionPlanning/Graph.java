package assignment_robots;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Graph {

	
	// Vertices in the graph
	public ArrayList<ArmRobot> mVertices = new ArrayList<ArmRobot>();
	
	// Edges in the graph. A vertex can be adjacent to several other nodes. 
	public HashMap<ArmRobot, ArrayList<ArmRobot>> mEdges = new HashMap<ArmRobot, ArrayList<ArmRobot>>();
	
	public Graph(){
		
	}
	
	// Add a vertex to the graph, but it doesn't have edge information.
	public void addVertex( ArmRobot current_arm )
	{
		this.mVertices.add(current_arm);
	}
	
	// Add Edge vert1 -> vert2
	// vert1 and vert2 have to be vertices in the graph, or it will throw exception.
	public void addEdge( ArmRobot vert1, ArmRobot vert2 ) throws Exception
	{
		if( this.mVertices.contains(vert1) && this.mVertices.contains(vert2) )
		{
			if( this.mEdges.containsKey(vert1) )
			{
				ArrayList<ArmRobot> adjVerts = this.mEdges.get(vert1);
				adjVerts.add( vert2 );
			}
			else
			{
				ArrayList<ArmRobot> newSuccs = new ArrayList<ArmRobot>();
				newSuccs.add(vert2);
				this.mEdges.put(vert1, newSuccs);
			}
		}
	//	else
	//		throw new Exception( "One vertex doesn't exist in the graph" );
	}
	
	// Get successors of a node in the graph.
	public ArrayList<ArmRobot> getSuccessor( ArmRobot vert )
	{
		ArrayList<ArmRobot> succs = null;
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
