package mgr.math.utils;

public class MatrixUtils {

    public static double[] multiplyVecPozMatrix(double[] vec, double[][] matrix){
        double[] result = new double[vec.length];
        for (int k = 0; k < vec.length; k++) {
            for (int j = 0; j < matrix.length; j++) {
                result[k] += vec[j] * matrix[j][k];
            }
        }
        return result;
    }

    public static double[][] addTwoMatricies(double[][] m1, double[][] m2){
        double[][] result = new double[m1.length][m1.length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                result[i][j] = m1[i][j] + m2[i][j];
            }
        }
        return result;
    }

    public static double[][] MultiplyTwoMatricies(double[][] m1, double[][] m2){
        double[][] result = new double[m1.length][m1.length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                for (int k = 0; k < result.length; k++) {
                    result[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return result;
    }

    public static double[][] substractTwoMatricies(double[][] m1, double[][] m2){
        double[][] result = new double[m1.length][m1.length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                result[i][j] = m1[i][j] - m2[i][j];
            }
        }
        return result;
    }

    public static double multiplyTwoVectors(double[] poziomy, double[] pionowy){
        double result = 0;
        for (int i = 0; i < poziomy.length; i++) {
            result += poziomy[i] * pionowy[i];
        }
        return result;
    }

    public static double[][] divideMatrixByScalar(double[][] matrix, double scalar){
        double[][] result = new double[matrix.length][matrix.length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                result[i][j] = matrix[i][j] / scalar;
            }
        }
        return result;
    }

    public static double[][] MultiplyMatrixByScalar(double[][] matrix, double scalar){
        double[][] result = new double[matrix.length][matrix.length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                result[i][j] = matrix[i][j] * scalar;
            }
        }
        return result;
    }

    public static double[][] addScalarToMatrix(double[][] matrix, double scalar){
        double[][] result = new double[matrix.length][matrix.length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                result[i][j] = scalar + matrix[i][j];
            }
        }
        return result;
    }

    public static String matrixToString(double[][] matrix){
        String out = "";
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                out += matrix[i][j];
                out += " ";
            }
            out += "\n";
        }
        return out;
    }

    public static double[][] multiplyVectorsToMatrix(double[] pionowy, double[] poziomy){
        double[][] result = new double[pionowy.length][poziomy.length];
        for (int i = 0; i < pionowy.length; i++) {
            for (int j = 0; j < poziomy.length; j++) {
                result[i][j] = pionowy[i] * poziomy[j];
            }
        }
        return result;
    }

    public static double[] multiplyMatrixByPionowyVec(double[][] matrix, Double[] vec){
        double[] result = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result[i] += matrix[i][j] * vec[j];
            }
        }
        return result;
    }
}