package util;

import java.util.ArrayList;

public class Path {
	final ArrayList<Vertex> path = new ArrayList<Vertex>();
	double pathLenght = 0;

	public void addNode(final Vertex node){
		path.add(node);
		pathLenght += node.getWeight();
	}

	public ArrayList<Vertex> getPath() { return path; }
	public double getLenght() { return pathLenght; }
}
