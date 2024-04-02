package lab4;
import static java.lang.StrictMath.*;

import java.util.function.Function;

class TrapezoidalIntegration {
    public static String error_message = "";
    public static boolean has_discontinuity = false;
    
    private static double first_function(double x) {
        return 1 / x;
    }

    private static double second_function(double x) {
        return sin(x) / x;
    }

    private static double third_function(double x) {
        return x * x + 2;
    }

    private static double fourth_function(double x) {
        return 2 * x + 2;
    }

    private static double five_function(double x) {
        return log(x);
    }

    /*
    * How to use this function:
    *    Function<Double, Double> func = get_function(4);
    *    func.apply(0.0001);
    */
    private static Function<Double, Double> get_function(int n) {
        switch (n) {
            case (1):
                return TrapezoidalIntegration::first_function;
            case (2):
                return TrapezoidalIntegration::second_function;
            case (3):
                return TrapezoidalIntegration::third_function;
            case (4):
                return TrapezoidalIntegration::fourth_function;
            case (5):
                return TrapezoidalIntegration::five_function;
            default:
                throw new UnsupportedOperationException("Function " + n + " not defined.");
        }
    }
    
    
    /*
     * Complete the 'calculate_integral' function below.
     *
     * The function is expected to return a DOUBLE.
     * The function accepts following parameters:
     *  1. DOUBLE a
     *  2. DOUBLE b
     *  3. INTEGER f
     *  4. DOUBLE epsilon
     */

    public static double calculate_integral(double a, double b, int f, double epsilon) {
        if(a > b) {
            double temp = a;
            a = b;
            b = temp;
        }
        Function<Double, Double> function = get_function(f);

        double h = b - a;
        double integral = 0.5 * h * (function.apply(a) + function.apply(b));
        double prevIntegral = 0.0;


        while(abs(integral - prevIntegral) > epsilon) {
            prevIntegral = integral;
            h = h / 2;
            integral = 0.5 * prevIntegral + h * sum(function, a, b, h);
        }

        return integral;

    }
    private static double sum(Function<Double, Double> func, double a, double b, double h) {
        double sum = 0.0;
        for(double x = a + h; x < b; x += h) {
            if(Double.isNaN(func.apply(x)) || Double.isInfinite(func.apply(x))) {
                error_message = "integrated function has discontinuity or does not defined in current interval";
                has_discontinuity = true;
                return 0.0;
            }
            sum += func.apply(x);
        }
        return sum;
    }

}

