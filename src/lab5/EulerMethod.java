package lab5;

import static java.lang.StrictMath.*;
import java.util.function.BiFunction;

class EulerMethod {

    private static double first_function(double x, double y) {
        return sin(x);
    }

    private static double second_function(double x, double y) {
        return (x * y) / 2;
    }

    private static double third_function(double x, double y) {
        return y - (2 * x) / y;
    }

    private static double fourth_function(double x, double y) {
        return x + y;
    }

    private static double default_function(double x, double y) {
        return 0.0;
    }

    /*
     * How to use this function:
     * BiFunction<Double, Double, Double> func = get_function(4);
     * func.apply(0.0001);
     */
    private static BiFunction<Double, Double, Double> get_function(int n) {
        switch (n) {
            case (1):
                return EulerMethod::first_function;
            case (2):
                return EulerMethod::second_function;
            case (3):
                return EulerMethod::third_function;
            case (4):
                return EulerMethod::fourth_function;
            default:
                return EulerMethod::default_function;
        }
    }

    /*
     * Complete the 'solveByEulerImproved' function below.
     *
     * The function is expected to return a DOUBLE.
     * The function accepts following parameters:
     * 1. INTEGER f
     * 2. DOUBLE epsilon
     * 3. DOUBLE a
     * 4. DOUBLE y_a
     * 5. DOUBLE b
     */
    public static double solveByEulerImproved(int f, double epsilon, double a, double y_a, double b) {
        BiFunction<Double, Double, Double> function = get_function(f);
        double h = Math.sqrt(epsilon);
        double x = a; 
        double y = y_a;
        while (x < b) {
            if (x + h > b) {
                h = b - x;
            }
            double y_pred = y + h * function.apply(x, y);
            double f_avg = (function.apply(x, y) + function.apply(x + h, y_pred)) / 2;

            double y_new  = y + h * f_avg;
            
            y = y_new;
            x += h;
        }
        return y;
    }
}