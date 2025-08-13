package gad.graphs;

import java.util.Collection;
import java.util.stream.Collectors;

public class StudentResult implements Result {

	@Override
	public void addNode(int id, int pathLength) {
		System.out.println("Added node " + id + " with length " + pathLength);
	}

	@Override
	public void addNeighbour(int id) {
		System.out.println("Added neighbour with id: " + id);
	}

	@Override
	public void addNeighbours(Collection<Integer> ids) {
		System.out.println("Added neighbours with ids: "
				+ ids.stream().map(id -> Integer.toString(id)).collect(Collectors.joining(", ")));
	}
}
