package util;

import java.util.ArrayList;

public class Path {
	final ArrayList<Node> path = new ArrayList<Node>();
	double pathLenght = 0;

	public void addNode(final Node node){
		path.add(node);
		pathLenght += node.getWeight();
	}

	public ArrayList<Node> getPath() { return path; }
	public double getLenght() { return pathLenght; }
}
