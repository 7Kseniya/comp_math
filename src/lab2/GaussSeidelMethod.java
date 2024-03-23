package lab2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class GaussSeidelMethod {
    static boolean isMethodApplicable = true;
    static String errorMessage = "";

    static boolean isApplicable(int n, List<List<Double>> matrix, double epsilon) {
        for (int i = 0; i < n; i++) {
            double sumOfAbs = 0;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    sumOfAbs += Math.abs(matrix.get(i).get(j));
                }
            }
            if (Math.abs(matrix.get(i).get(i)) <= sumOfAbs) {
                isMethodApplicable = false;
                errorMessage = "The system has no diagonal dominance for this method. Method of the Gauss-Seidel is not applicable.";
                return false;
            }
        }
        return true;
    }

    static List<Double> solveByGaussSeidel(int n, List<List<Double>> matrix, double epsilon) {
        if (!isApplicable(n, matrix, epsilon)) {
            return new ArrayList<Double>();
        }

        List<Double> input = new ArrayList<Double>(Collections.nCopies(n, 0.0));
        List<Double> result;

        while (true) {
            result = new ArrayList<Double>();
            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        sum += matrix.get(i).get(j) * input.get(j);
                    }
                }
                result.add((matrix.get(i).get(n) - sum) / matrix.get(i).get(i));
            }
            boolean converged = true;
            for (int i = 0; i < n; i++) {
                if (Math.abs(result.get(i) - input.get(i)) >= epsilon) {
                    converged = false;
                    break;
                }
            }
            if (converged) {
                return result;
            }

            input = result;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        List<List<Double>> matrix = new ArrayList<List<Double>>();
        for (int i = 0; i < n; i++) {
            matrix.add(new ArrayList<Double>());
            for (int j = 0; j < n + 1; j++) {
                matrix.get(i).add(scanner.nextDouble());
            }
        }

        double epsilon = scanner.nextDouble();
        scanner.close();

        List<Double> result = solveByGaussSeidel(n, matrix, epsilon);
        if (isMethodApplicable) {
            for (double num : result) {
                System.out.println(num);
            }
        } else {
            System.out.println(errorMessage);
        }
    }
}
