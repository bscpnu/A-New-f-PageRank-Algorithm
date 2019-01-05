/*
 * To get more information about how F-PageRank works
 * you can refer to our paper http://ieeexplore.ieee.org/document/8029787/
 */

package fpagerank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Imam Mustafa Kamal
 * @email imamkamal@pusan.ac.kr
 */
public class ReadData {

    public String fileName;

    double[][] routeMatrix;

    List<String> resourcelist = new ArrayList<String>();
    List<String> caselist = new ArrayList<String>();
    List<String> activitylist = new ArrayList<String>();

    public ReadData(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public List<List<String>> readCsv() {
        File file = new File(this.fileName);
        List<List<String>> lines = new ArrayList<>();
        Scanner inputStream;
        try {
            inputStream = new Scanner(file);
            while (inputStream.hasNext()) {
                String line = inputStream.next();
                String[] values = line.split(",");
                lines.add(Arrays.asList(values));
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public void parseDatalist(List<List<String>> lines) {
        for (List<String> line : lines) {
            int columnNo = 1;
            for (String value : line) {
                if (columnNo == 3) {
                    this.resourcelist.add(value);
                } else if (columnNo == 1) {
                    this.caselist.add(value);
                } else {
                    this.activitylist.add(value);
                }
                columnNo++;
            }
        }
    }

    public List<String> getUniqueList(List<String> datalist) {
        Set<String> unique = new HashSet<String>(datalist);

        List<String> uniqueList = new ArrayList<String>(unique);
        return uniqueList;
    }

    public double[][] initialRoutingMatrix(List<String> uniqueResource, double[][] routeMatrix) {
        for (int i = 0; i < routeMatrix.length; i++) {
            for (int j = 0; j < routeMatrix.length; j++) {
                routeMatrix[i][j] = 0;
            }
        }
        return routeMatrix;
    }

    public static int xyPosition(String name, List<String> resource_list) {
        int index = -1;
        for (int i = 0; i < resource_list.size(); i++) {
            if (resource_list.get(i).equals(name)) {
                index = i;
            }
        }
        return index;
    }

    public double[][] routingMatrix(List<List<String>> lines, List<String> resourcelist, double[][] routeMatrix) {
        int c = 1;
        for (int i = 0; i < lines.size() - 1; i++) {
            if (lines.get(i).get(0).equals(lines.get(c).get(0))) {
                int y = xyPosition(lines.get(i).get(2), resourcelist);
                int x = xyPosition(lines.get(c).get(2), resourcelist);

                routeMatrix[x][y] = routeMatrix[x][y] + 1;
            }
            if (c < lines.size() - 1) {
                c++;
            }
        }
        return routeMatrix;
    }
    
    public void displayMatrix(double[][] matrix, String label){
        System.out.println("\n" +label+ " is ");
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix.length; j++){
                System.out.print(String.format("%,.4f", matrix[i][j]) + "\t\t");
            }
            System.out.println("");
        }
    }
    
    public void displayRanking(double[] ranking, List<String> resourcelist){
        System.out.println("\nFinal rangking for each resource is ");
        for(int i=0; i<ranking.length; i++){
            System.out.println(resourcelist.get(i) + "\t=\t " +ranking[i]);
        }
    }

}
