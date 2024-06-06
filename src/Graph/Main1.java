
package Graph;

import graph.Graph;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.*;

public class Main1 extends Application {
    private Graph graph = new Graph();
    private Pane graphPane = new Pane();
    private Map<String, Circle> vertexMap = new HashMap<>();
    private int vertexCount = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Graph Visualization");

        GridPane controlPane = new GridPane();
        TextField vertexField = new TextField();
        TextField edgeField1 = new TextField();
        TextField edgeField2 = new TextField();
        TextField oldVertexField = new TextField();
        TextField newVertexField = new TextField();
        TextField searchVertexField = new TextField();
        Button addVertexButton = new Button("Add Vertex");
        Button addEdgeButton = new Button("Add Edge");
        Button removeVertexButton = new Button("Remove Vertex");
        Button removeEdgeButton = new Button("Remove Edge");
        Button updateVertexButton = new Button("Update Vertex");
        Button searchVertexButton = new Button("Search Vertex");
        Label vertexLabel = new Label("Vertex:");
        Label edgeLabel1 = new Label("Edge from:");
        Label edgeLabel2 = new Label("to:");
        Label oldVertexLabel = new Label("Old Vertex:");
        Label newVertexLabel = new Label("New Vertex:");
        Label searchVertexLabel = new Label("Search Vertex:");

        // Set CSS IDs and classes
        controlPane.setId("control-pane");
        vertexField.getStyleClass().add("text-field");
        edgeField1.getStyleClass().add("text-field");
        edgeField2.getStyleClass().add("text-field");
        oldVertexField.getStyleClass().add("text-field");
        newVertexField.getStyleClass().add("text-field");
        searchVertexField.getStyleClass().add("text-field");
        addVertexButton.getStyleClass().add("button");
        addEdgeButton.getStyleClass().add("button");
        removeVertexButton.getStyleClass().add("button");
        removeEdgeButton.getStyleClass().add("button");
        updateVertexButton.getStyleClass().add("button");
        searchVertexButton.getStyleClass().add("button");
        removeVertexButton.getStyleClass().add("button-delete");
        removeEdgeButton.getStyleClass().add("button-delete");
        searchVertexButton.getStyleClass().add("button-search");
        updateVertexButton.getStyleClass().add("button-update");
        vertexLabel.getStyleClass().add("label");
        edgeLabel1.getStyleClass().add("label");
        edgeLabel2.getStyleClass().add("label");
        oldVertexLabel.getStyleClass().add("label");
        newVertexLabel.getStyleClass().add("label");
        searchVertexLabel.getStyleClass().add("label");

        controlPane.add(vertexLabel, 0, 0);
        controlPane.add(vertexField, 1, 0);
        controlPane.add(addVertexButton, 2, 0);
        controlPane.add(edgeLabel1, 0, 1);
        controlPane.add(edgeField1, 1, 1);
        controlPane.add(edgeLabel2, 2, 1);
        controlPane.add(edgeField2, 3, 1);
        controlPane.add(addEdgeButton, 4, 1);
        controlPane.add(oldVertexLabel, 0, 2);
        controlPane.add(oldVertexField, 1, 2);
        controlPane.add(newVertexLabel, 2, 2);
        controlPane.add(newVertexField, 3, 2);
        controlPane.add(updateVertexButton, 4, 2);
        controlPane.add(searchVertexLabel, 0, 3);
        controlPane.add(searchVertexField, 1, 3);
        controlPane.add(searchVertexButton, 2, 3);
        controlPane.add(removeVertexButton, 0, 4);
        controlPane.add(removeEdgeButton, 1, 4);

        addVertexButton.setOnAction(e -> {
            String vertex = vertexField.getText();
            if (!vertex.isEmpty()) {
                graph.addVertex(vertex);
                drawGraph();
                vertexField.clear();
            }
        });

        addEdgeButton.setOnAction(e -> {
            String vertex1 = edgeField1.getText();
            String vertex2 = edgeField2.getText();
            if (!vertex1.isEmpty() && !vertex2.isEmpty()) {
                graph.addEdge(vertex1, vertex2);
                drawGraph();
                edgeField1.clear();
                edgeField2.clear();
            }
        });

        removeVertexButton.setOnAction(e -> {
            String vertex = vertexField.getText();
            if (!vertex.isEmpty()) {
                graph.removeVertex(vertex);
                drawGraph();
                vertexField.clear();
            }
        });

        removeEdgeButton.setOnAction(e -> {
            String vertex1 = edgeField1.getText();
            String vertex2 = edgeField2.getText();
            if (!vertex1.isEmpty() && !vertex2.isEmpty()) {
                graph.removeEdge(vertex1, vertex2);
                drawGraph();
                edgeField1.clear();
                edgeField2.clear();
            }
        });

        updateVertexButton.setOnAction(e -> {
            String oldVertex = oldVertexField.getText();
            String newVertex = newVertexField.getText();
            if (!oldVertex.isEmpty() && !newVertex.isEmpty()) {
                graph.updateVertex(oldVertex, newVertex);
                drawGraph();
                oldVertexField.clear();
                newVertexField.clear();
            }
        });

        searchVertexButton.setOnAction(e -> {
            String vertex = searchVertexField.getText();
            if (!vertex.isEmpty()) {
                boolean found = graph.searchVertex(vertex);
                String message;
                if (found) {
                    message = "Vertex " + vertex + " found.";
                } else {
                    message = "Vertex " + vertex + " not found.";
                }
                showAlert(message);
                searchVertexField.clear();
            }
        });
        
        // Load CSS file
        String css = this.getClass().getResource("styles.css").toExternalForm();
        controlPane.getStylesheets().add(css);

        graphPane.setId("graph-pane");
        graphPane.setPrefSize(900, 700);
        GridPane mainPane = new GridPane();
        mainPane.add(controlPane, 0, 0);
        mainPane.add(graphPane, 0, 1);

        Scene scene = new Scene(mainPane, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
       }
    
 private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Result");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

private void drawGraph() {
    graphPane.getChildren().clear();
    vertexMap.clear();

    Set<String> vertices = graph.getVertices();
    int size = vertices.size();
    double centerX = graphPane.getWidth() / 2;
    double centerY = graphPane.getHeight() / 2;
    double radius = Math.min(centerX, centerY) - 50;

    int i = 0;
    for (String vertex : vertices) {
        double angle = 2 * Math.PI * i / size;
        double x = Math.max(20, Math.min(centerX + radius * Math.cos(angle), graphPane.getWidth() - 20));
        double y = Math.max(20, Math.min(centerY + radius * Math.sin(angle), graphPane.getHeight() - 20));
        Circle circle = new Circle(x, y, 20, Color.BLUE);
        Text text = new Text(x - 5, y + 5, vertex); // Adjust the position of the text
        text.setFill(Color.WHITE);
        vertexMap.put(vertex, circle);
        graphPane.getChildren().addAll(circle, text);
        i++;
    }

    for (String vertex1 : vertices) {
        List<String> adjVertices = graph.getAdjVertices(vertex1);
        for (String vertex2 : adjVertices) {
            Circle circle1 = vertexMap.get(vertex1);
            Circle circle2 = vertexMap.get(vertex2);
            if (circle1 != null && circle2 != null) {
                double dx = circle2.getCenterX() - circle1.getCenterX();
                double dy = circle2.getCenterY() - circle1.getCenterY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                double ratio = (radius - 180) / distance;

                double startX = circle1.getCenterX() + dx * ratio;
                double startY = circle1.getCenterY() + dy * ratio;

                double endX = circle2.getCenterX() - dx * ratio;
                double endY = circle2.getCenterY() - dy * ratio;

                Line line = new Line(startX, startY, endX, endY);
                line.setStrokeWidth(2);
                line.setStroke(Color.BLACK);
                graphPane.getChildren().add(line);
            }
        }
    }
}

}