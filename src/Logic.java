import java.util.ArrayList;
import java.util.Arrays;

public class Logic {
    private int[] distance;
    private int[][] distancesCollection;
    private int[][][] pathCollection;

    void dijkstraProcess(int[][] processGivenGraph) {
        //Initialize variables
        int numberOfVertex = processGivenGraph.length;
        int startingIndex = 0;

        distance = new int[numberOfVertex];
        Boolean[] includedInPath = new Boolean[numberOfVertex];
        ArrayList<Integer> pathX = new ArrayList<Integer>();
        ArrayList<Integer> pathY = new ArrayList<Integer>();

        distancesCollection = new int[numberOfVertex][numberOfVertex];
        pathCollection = new int[numberOfVertex][2][numberOfVertex+1];

        //Initialize distance as inf and includedInPath as false
        //Max integer value will represent as INF
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(includedInPath, false);

        for (int[][] array2D : pathCollection)
            for(int[] array1D : array2D)
                Arrays.fill(array1D, -1);

        //Assign the first distance to 0
        distance[0] = 0;
        pathCollection[0][0][1] = 1;
        pathCollection[0][1][1] = 1;
        pathX.add(-1);
        pathY.add(-1);
        pathX.add(1);
        pathY.add(1);

        //Calculate the shortestPath
        for (int counter = 0; counter < numberOfVertex; ++counter) {
            int minimumDistanceIndex = calculateMinimumDistance(distance, includedInPath);

            //Add the current path to the collection
            if (counter != 0) {
                pathY.add(minimumDistanceIndex + 1);
                pathX.add(pathY.get((counter + 1) - 1));

                for (int pathCounter = 0; pathCounter < pathX.size(); ++pathCounter) {
                    pathCollection[counter][0][pathCounter] = pathX.get(pathCounter);
                    pathCollection[counter][1][pathCounter] = pathY.get(pathCounter);
                }
            }

            //Add the distance to the collection
            for (int distanceCounter = 0; distanceCounter < distance.length; ++distanceCounter)
                distancesCollection[counter][distanceCounter] = distance[distanceCounter];

            includedInPath[minimumDistanceIndex] = true;

            //Calculate the shortestPath
            for (int vertexCounter = 0; vertexCounter < numberOfVertex; ++vertexCounter) {
                if (!includedInPath[vertexCounter] &&
                        processGivenGraph[minimumDistanceIndex][vertexCounter] != 0 &&
                        distance[minimumDistanceIndex] != Integer.MAX_VALUE &&
                        distance[minimumDistanceIndex] + processGivenGraph[minimumDistanceIndex][vertexCounter] < distance[vertexCounter])
                    distance[vertexCounter] = distance[minimumDistanceIndex]  + processGivenGraph[minimumDistanceIndex][vertexCounter];
            }
        }

        for (int[] ints : distancesCollection) {
            for (int b = 0; b < ints.length; ++b) {
                System.out.print(ints[b] + " ");
            }
            System.out.println();
        }

        System.out.println("Test:");
        for (int[][] array2D: pathCollection) {
            for (int[] array1D: array2D) {
                for(int item: array1D) {
                    System.out.print(item + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    int calculateMinimumDistance(int[] processGivenDistance, Boolean[] processGivenPathSet) {
        int minimumValue = Integer.MAX_VALUE;
        int minimumIndex = -1;

        for (int counter = 0; counter < processGivenDistance.length; ++counter) {
            if (!processGivenPathSet[counter] && processGivenDistance[counter] <= minimumValue) {
                minimumValue = processGivenDistance[counter];
                minimumIndex = counter;
            }
        }

        return minimumIndex;
    }

    public int [][] getDistancesCollection() { return distancesCollection; }

    public int [][][] getPathCollection() { return pathCollection; }
}
