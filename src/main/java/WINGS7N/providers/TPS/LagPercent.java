package WINGS7N.providers.TPS;

public class LagPercent {
    public String get(double TPS) {
        return String.format("%.2f", (100 - TPS * 5));
    }
}
