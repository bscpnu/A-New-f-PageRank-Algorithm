/*
 * To get more information about how F-PageRank works
 * you can refer to our paper http://ieeexplore.ieee.org/document/8029787/
 */

package fpagerank;

import java.util.List;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

/**
 *
 * @author Imam Mustafa Kamal
 * @email imamkamal@pusan.ac.kr
 */
public class DisplayGraph {
    
    public static void displayGraph(List<String> resourceList, double[][] routeMatrix, double[] ranking) {
        Graph graph = new MultiGraph("F-PageRank");
        graph.addAttribute("ui.title", "Identifying key-resource in social network using F-PageRank");
        for (int i = 0; i < resourceList.size(); i++) {
            graph.addNode(resourceList.get(i));
        }
        for (int i = 0; i < routeMatrix.length; i++) {
            for (int j = 0; j < routeMatrix.length; j++) {
                if (routeMatrix[i][j] != 0.0) {
                    graph.addEdge(resourceList.get(j) + "" + resourceList.get(i), resourceList.get(j), resourceList.get(i), true);
                }
            }
        }

        int colr = 255, colg = 0, colb = 255;
        
        for (int i = 0; i < resourceList.size(); i++) {
            int size = (int) (ranking[i] * 120);
            Node a = graph.getNode(resourceList.get(i));
            a.addAttribute("ui.label", resourceList.get(i));
            a.addAttribute("ui.style", "fill-color: rgb(" + colr + "," + colg + "," + colb + "); size:" + size + "px;");

            colr = colr - 20;
            colg = colg + 50;
            colb = colb - 80;
        }

        graph.display();
    }
}
