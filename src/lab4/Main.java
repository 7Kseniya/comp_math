package lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        double a = Double.parseDouble(bufferedReader.readLine().trim());

        double b = Double.parseDouble(bufferedReader.readLine().trim());

        int f = Integer.parseInt(bufferedReader.readLine().trim());

        double epsilon = Double.parseDouble(bufferedReader.readLine().trim());

        double result = TrapezoidalIntegration.calculate_integral(a, b, f, epsilon);
        if(!TrapezoidalIntegration.has_discontinuity){
            bufferedWriter.write(String.valueOf(result));   
        } else {
            bufferedWriter.write(String.valueOf(TrapezoidalIntegration.error_message));
        }
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
