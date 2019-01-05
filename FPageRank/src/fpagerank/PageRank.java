/*
 * To get more information about how F-PageRank works
 * you can refer to our paper http://ieeexplore.ieee.org/document/8029787/
 */

package fpagerank;

/**
 *
 * @author Imam Mustafa Kamal
 * @email imamkamal@pusan.ac.kr
 */
public class PageRank {

    public double[][] normalizeMatrix(double[][] routeMatrix, double dumpingFact) {

        double[] columnSum = new double[routeMatrix.length];
        
        for (int i = 0; i < columnSum.length; i++) {
            columnSum[i] = 0;
        }
        for (int i = 0; i < routeMatrix.length; i++) {
            for (int j = 0; j < routeMatrix.length; j++) {
                columnSum[i] = columnSum[i] + routeMatrix[j][i];
            }
        }
        for (int i = 0; i < routeMatrix.length; i++) {
            for (int j = 0; j < routeMatrix.length; j++) {
                if (columnSum[j] == 0) {
                    routeMatrix[i][j] = 0;
                } else {
                    routeMatrix[i][j] = dumpingFact * (routeMatrix[i][j] / columnSum[j]);
                }
            }
        }
        return routeMatrix;
    }

    public double[][] getOnePerNMatrix(int n, double dumpingFact) {
        double[][] onePerN = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                onePerN[i][j] = (1 - dumpingFact) * ((double) 1 / n);
            }
        }
        return onePerN;
    }

    public double[][] getMatrixA(double[][] normalizeMatrix, double[][] onePerNMatrix) {
        double[][] matrixA = new double[normalizeMatrix.length][normalizeMatrix.length];
        for (int i = 0; i < normalizeMatrix.length; i++) {
            for (int j = 0; j < normalizeMatrix.length; j++) {
                matrixA[i][j] = normalizeMatrix[i][j] + onePerNMatrix[i][j];
            }
        }
        return matrixA;
    }

    public double[] getInitialRank(int n) {
        double[] initialRank = new double[n];
        for (int i = 0; i < n; i++) {
            initialRank[i] = (double) 1 / n;
        }
        return initialRank;
    }

    public double[] powerMethod(double[][] matrixA, double[] currentRank) {
        double[] result = new double[currentRank.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = 0.0;
        }
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA.length; j++) {
                result[i] = result[i] + matrixA[i][j] * currentRank[j];
            }
        }
        return result;
    }

    public double[] getinitialRank(int n) {
        double[] initialRank = new double[n];
        for (int i = 0; i < n; i++) {
            initialRank[i] = (double) 1 / n;
        }
        return initialRank;
    }

    public double[] calcRanking(double[][] matrixA, double[] initialRank, int maxIteration) {
        double[] currentRank = new double[matrixA.length];
        double[] nextRank = new double[matrixA.length];

        for (int i = 0; i < maxIteration; i++) {
            if (!currentRank.equals(nextRank)) {
                currentRank = powerMethod(matrixA, initialRank);
            }
            nextRank = currentRank.clone();
        }
        return currentRank;
    }
}
