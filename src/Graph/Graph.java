
package graph;

import java.util.*;

public class Graph {
    private Map<String, List<String>> adjVertices;

    public Graph() {
        adjVertices = new HashMap<>();
    }

    public void addVertex(String label) {
        adjVertices.putIfAbsent(label, new ArrayList<>());
    }

    public void addEdge(String label1, String label2) {
        adjVertices.get(label1).add(label2);
        adjVertices.get(label2).add(label1);
    }

    public void removeVertex(String label) {
        adjVertices.values().forEach(e -> e.remove(label));
        adjVertices.remove(label);
    }

    public void removeEdge(String label1, String label2) {
        List<String> eV1 = adjVertices.get(label1);
        List<String> eV2 = adjVertices.get(label2);
        if (eV1 != null) eV1.remove(label2);
        if (eV2 != null) eV2.remove(label1);
    }

    public void updateVertex(String oldLabel, String newLabel) {
        List<String> edges = adjVertices.remove(oldLabel);
        if (edges != null) {
            adjVertices.put(newLabel, edges);
            for (String vertex : adjVertices.keySet()) {
                List<String> adjEdges = adjVertices.get(vertex);
                if (adjEdges.remove(oldLabel)) {
                    adjEdges.add(newLabel);
                }
            }
        }
    }

    public boolean searchVertex(String label) {
        return adjVertices.containsKey(label);
    }

    public Set<String> getVertices() {
        return adjVertices.keySet();
    }

    public List<String> getAdjVertices(String label) {
        return adjVertices.get(label);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String vertex : adjVertices.keySet()) {
            sb.append(vertex).append(": ").append(adjVertices.get(vertex)).append("\n");
        }
        return sb.toString();
    }
}

