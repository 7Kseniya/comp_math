package lab3;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NewtonMethod {
    public static List<Double> solve_by_fixed_point_iterations(int system_id, int number_of_unknowns, List<Double> initial_approximations) {
        if (number_of_unknowns != initial_approximations.size()) {
            throw new IllegalArgumentException("the number of initial approximations must match the number of unknowns.");
        }
        
        List<Function<List<Double>, Double>> functions = SNAEFunctions.get_functions(system_id);
        double epsilon = 1e-5; // accuracy
        double[] x = new double[number_of_unknowns];
        for (int i = 0; i < number_of_unknowns; i++) {
            x[i] = initial_approximations.get(i);
        }
        while (true) {
            double[] fx = new double[number_of_unknowns];
            double[][] jacobian = new double[number_of_unknowns][number_of_unknowns];
            for (int i = 0; i < number_of_unknowns; i++) {
                List<Double> args = Arrays.stream(x).boxed().collect(Collectors.toList());
                fx[i] = functions.get(i).apply(args);
                for (int j = 0; j < number_of_unknowns; j++) {
                    double h = 1e-6;
                    List<Double> argsPlusH = Arrays.stream(x).boxed().collect(Collectors.toList());
                    argsPlusH.set(j, x[j] + h);
                    double fxPlusH = functions.get(i).apply(argsPlusH);
                    jacobian[i][j] = (fxPlusH - fx[i]) / h;
                }
            }
            double[] dx = solveLinearSystem(jacobian, fx);
            boolean converged = true;
            for (double d : dx) {
                if (Math.abs(d) > epsilon) {
                    converged = false;
                    break;
                }
            }
            if (converged) {
                break;
            }
            for (int i = 0; i < number_of_unknowns; i++) {
                x[i] -= dx[i];
            }
        }
        return Arrays.stream(x).boxed().collect(Collectors.toList());
    }

    private static double[] solveLinearSystem(double[][] a, double[] b) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(a[j][i]) > Math.abs(a[maxIndex][i])) {
                    maxIndex = j;
                }
            }
            double[] temp = a[i];
            a[i] = a[maxIndex];
            a[maxIndex] = temp;
            double tempB = b[i];
            b[i] = b[maxIndex];
            b[maxIndex] = tempB;
            for (int j = i + 1; j < n; j++) {
                double factor = a[j][i] / a[i][i];
                b[j] -= factor * b[i];
                for (int k = i; k < n; k++) {
                    a[j][k] -= factor * a[i][k];
                }
            }
        }
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += a[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / a[i][i];
        }
        return x;
    }
}
