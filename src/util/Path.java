package util;

import java.io.Serializable;
import java.util.ArrayList;

public class Path implements Serializable{
	private static final long serialVersionUID = 625664590923029028L;

	final ArrayList<Vertex> path = new ArrayList<Vertex>();
	double pathLenght = 0;

	public void addNode(final Vertex node){
		path.add(node);
		pathLenght += node.getWeight();
	}

	public ArrayList<Vertex> getPath() { return path; }
	public double getLenght() { return pathLenght; }
}
