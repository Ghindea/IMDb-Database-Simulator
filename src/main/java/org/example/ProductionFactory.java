package org.example;

public class ProductionFactory {
    public static Production buildProduction(String type) {
        switch (type.toLowerCase()) {
            case "movie" -> {
                return new Movie();
            }
            case "series" -> {
                return new Series();
            }
            default -> {
                return null;
            }
        }
    }

}
