/*
 * To get more information about how F-PageRank works
 * you can refer to our paper http://ieeexplore.ieee.org/document/8029787/
 */

package fpagerank;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Imam Mustafa Kamal
 * @email imamkamal@pusan.ac.kr
 */

public class Main {

    public static void main(String[] args) {

        String fileName = "src/data/eventlogs.csv";
        ReadData rd = new ReadData(fileName);

        List<List<String>> lines = new ArrayList<>();
        lines = rd.readCsv();

        rd.parseDatalist(lines);

        List<String> resourceList = new ArrayList<String>();
        resourceList = rd.getUniqueList(rd.resourcelist);

        int n = resourceList.size();

        double[][] routeMatrix = new double[n][n];

        routeMatrix = rd.initialRoutingMatrix(resourceList, routeMatrix);
        routeMatrix = rd.routingMatrix(lines, resourceList, routeMatrix);

        rd.displayMatrix(routeMatrix, "Routing Matrix");

        double[][] normRouteMatrix = new double[n][n];
        double dumpingFact = 0.8;

        PageRank pr = new PageRank();
        normRouteMatrix = pr.normalizeMatrix(routeMatrix, dumpingFact);

        double[][] onePerN = new double[n][n];
        onePerN = pr.getOnePerNMatrix(n, dumpingFact);

        double[][] matrixA = new double[n][n];
        matrixA = pr.getMatrixA(normRouteMatrix, onePerN);

        rd.displayMatrix(matrixA, "Matrix A");

        double[] initialRank = new double[n];
        initialRank = pr.getInitialRank(n);

        double[] finalRank = new double[n];
        int maxIteration = 10;

        finalRank = pr.calcRanking(matrixA, initialRank, maxIteration);
        rd.displayRanking(finalRank, resourceList);
        
        DisplayGraph dg = new DisplayGraph();
        dg.displayGraph(resourceList, routeMatrix, finalRank);        

    }

}
