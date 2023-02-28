package main.part1;

public class ArcCosCalculator implements FunctionCalculator{

    @Override
    public double calculate(double argument) {
        if (Math.abs(argument) > 1) {
            return Double.NaN;
        }
        double ans = argument;
        double a = 1;
        double b = 2;
        double tmp = 1;
        double i = 2;
        while (tmp > 0.001) {
            tmp = Math.pow(argument, 2 * i - 1) * a / (b * (2 * i - 1));
            ans += tmp;
            a *= 2L * i - 1;
            b *= 2L * i;
            i++;
        }
        return Math.PI / 2 - ans;
    }
}
