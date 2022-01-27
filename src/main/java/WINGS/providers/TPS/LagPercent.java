package WINGS.providers.TPS;

public class LagPercent {
    public double get(double TPS) {
        return Double.parseDouble(String.format("%.2f", (100 - TPS * 5)));
    }
}
