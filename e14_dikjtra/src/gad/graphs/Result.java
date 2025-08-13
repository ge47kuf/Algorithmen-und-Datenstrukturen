package gad.graphs;

import java.util.Collection;

public interface Result {

	void addNode(int id, int pathLength);

	void addNeighbour(int id);

	void addNeighbours(Collection<Integer> ids);
}
