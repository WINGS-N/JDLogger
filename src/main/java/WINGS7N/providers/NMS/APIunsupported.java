package WINGS7N.providers.NMS;

public class APIunsupported {
    public static String run(String nms) {
        switch (nms) {
            case "v1_13_R1":
                return "1.13";
            case "v1_13_R2":
                return "1.13.2";
            case "v1_14_R1":
                return  "1.14.4";
            case "v1_15_R1":
                return "1.15.2";
            case "v1_16_R1":
                return "1.16.1";
            case "v1_16_R2":
                return "1.16.3";
            case "v1_16_R3":
                return "1.16.5";
            default:
                return "UNKNOWN";
        }
    }
}
