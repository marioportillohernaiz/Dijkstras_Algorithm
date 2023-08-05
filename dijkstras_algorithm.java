import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class dijkstras_algorithm {

    public static void main(String[] args) {
        Grid grid = new Grid();
        Node gridNode = new Node();
        ArrayList<Node> objectList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter grid size (in 5x5 format): ");
        grid.setAxis(sc.nextLine());

        System.out.print("Enter starting position (in x,y format): ");
        String startPos = sc.nextLine();

        int maxNumbOfCoor = grid.getXAxis() * grid.getYAxis();
        ArrayList<Node> pathList = new ArrayList<>(maxNumbOfCoor);
        gridNode.setAxis(startPos);
        pathList.add(gridNode);
        objectList.add(gridNode);

        System.out.print("Enter number of objects: ");
        int objects = sc.nextInt();

        for (int i=0; i < objects; i++) {
            Scanner objectsc = new Scanner(System.in);
            System.out.print("Enter object (in x,y format): ");
            String object = objectsc.nextLine();

            if (object.equals(startPos)) {
                System.out.println("Cannot add obstacle on starting position.");
                i--;
            } else {
                gridNode = new Node();
                gridNode.setAxis(object);
                objectList.add(gridNode);
            }
        }

        Scanner goalsc = new Scanner(System.in);
        System.out.print("Enter goal coordinates (in x,y format): ");
        gridNode = new Node();
        gridNode.setAxis(goalsc.nextLine());
        objectList.add(gridNode);

        gridCreation(grid, objectList, pathList);
        dijkstraPath(grid, objectList, pathList);
    }

    public static void gridCreation(Grid grid, ArrayList<Node> objectList, ArrayList<Node> pathList) {
        for (int y=1; y <= grid.getYAxis(); y++) {
            System.out.print("+");
            for (int x=1; x <= grid.getXAxis(); x++) {
                System.out.print("---+");
            }

            System.out.println();
            System.out.print("|");
            for (int x=1; x <= grid.getXAxis(); x++) {
                String mapmark = obstacleFinder(x, y, objectList, pathList);
                if (mapmark != "") {
                    System.out.print(" "+ mapmark +" |");
                } else {
                    System.out.print("   |");
                }
            }
            System.out.println();
        }
        System.out.print("+");
        for (int x=1; x <= grid.getXAxis(); x++) {
            System.out.print("---+");
        }
        System.out.println();
    }

    public static String obstacleFinder(int xAxis, int yAxis, ArrayList<Node> objectList, ArrayList<Node> pathList) {
        String output = "";
        int count = 0;
        if (objectList.size() != 0) {
            for (Node objNode : objectList) {
                int x = objNode.getXAxis();
                int y = objNode.getYAxis();

                if (count == 0 && x == xAxis && y == yAxis) {
                    return "S";
                } else if (count == objectList.size()-1 && x == xAxis && y == yAxis) {
                    return "G";
                } else if (x == xAxis && y == yAxis) {
                    return "X";
                } else {
                    output = "";
                }
                count++;
            }

            for(int i=0; i < pathList.size(); i++) {
                Node node = new Node();
                node.setAxis(pathList.get(i).toString());
                int x = node.getXAxis();
                int y = node.getYAxis();

                if (x == xAxis && y == yAxis) {
                    return "-";
                } else {
                    output = "";
                }
            }
        }
        return output;
    }

    private static boolean checkValid(Node node, Grid grid, ArrayList<Node> objectList, boolean[][] visited) {
        int xGrid = grid.getXAxis();
        int yGrid = grid.getYAxis();

        if (node.getXAxis() >= 1 && node.getYAxis() >= 1 && node.getXAxis() <= xGrid && node.getYAxis() <= yGrid) {
            if ((!objectList.toString().contains(node.toString()) && visited[node.getYAxis()][node.getXAxis()] == false) || objectList.get(0).toString().equals(node.toString()) || objectList.get(objectList.size()-1).toString().equals(node.toString())) {
                return true;
            }
        }
        return false;
    }

    public static void dijkstraPath(Grid grid, ArrayList<Node> objectList, ArrayList<Node> pathList) {
        /* Max number of path cost <= (X*Y) - numbOfObstacles - 1
         *  +---+---+---+
            | S |   | X |
            +---+---+---+
            |   | X |   |
            +---+---+---+
            |   |   | G |
            +---+---+---+
            Example: S -> 2,1 -> x
                     S -> 1,2 -> 1,3 -> 2,3 -> 3,3 = 4
            Max path = 4
        */

        Queue<Node> queue = new LinkedList<>();
        Node startingNode = objectList.get(0);
        startingNode.setDistance(0);
        queue.add(startingNode);
        boolean pathFound = false;

        boolean[][] visited = new boolean[grid.getYAxis()+1][grid.getXAxis()+1];
        visited[startingNode.getYAxis()][startingNode.getXAxis()] = true;
        
        while (queue.isEmpty() == false) {
            Node currentNode = queue.remove();
            Node followUpNode = new Node();

            // Check if Node reached goal
            if (currentNode.toString().equals(objectList.get(objectList.size()-1).toString())) {
                System.out.println("Shortest path: "+currentNode.getDistance());
                pathFound = true;
                break;
            }

            // Check up
            followUpNode.addNewAxis(currentNode.getXAxis(), currentNode.getYAxis()-1);
            if (checkValid(followUpNode, grid, objectList, visited)) {
                followUpNode.setDistance(currentNode.getDistance() + 1);
                queue.add(followUpNode);
                pathList.add(currentNode);
                visited[followUpNode.getYAxis()][followUpNode.getXAxis()] = true;
            }

            // Check down
            followUpNode = new Node();
            followUpNode.addNewAxis(currentNode.getXAxis(), currentNode.getYAxis()+1);
            if (checkValid(followUpNode, grid, objectList, visited)) {
                followUpNode.setDistance(currentNode.getDistance() + 1);
                queue.add(followUpNode);
                pathList.add(currentNode);
                visited[followUpNode.getYAxis()][followUpNode.getXAxis()] = true;
            }

            // Check right
            followUpNode = new Node();
            followUpNode.addNewAxis(currentNode.getXAxis()+1, currentNode.getYAxis());
            if (checkValid(followUpNode, grid, objectList, visited)) {
                followUpNode.setDistance(currentNode.getDistance() + 1);
                queue.add(followUpNode);
                pathList.add(currentNode);
                visited[followUpNode.getYAxis()][followUpNode.getXAxis()] = true;
            }

            // Check left
            followUpNode = new Node();
            followUpNode.addNewAxis(currentNode.getXAxis()-1, currentNode.getYAxis());
            if (checkValid(followUpNode, grid, objectList, visited)) {
                followUpNode.setDistance(currentNode.getDistance() + 1);
                queue.add(followUpNode);
                pathList.add(currentNode);
                visited[followUpNode.getYAxis()][followUpNode.getXAxis()] = true;
            }
        }
        if (!pathFound) {
            System.out.println("No Possible Route");
        }
        
        gridCreation(grid, objectList, pathList);
    }
}