package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Series extends Production{
    private int numberOfSeasons;
    private Map<String, List<Episode>> series;

    public Series(String title, List<String> directorsNames, List<String> actorsNames,
                  List<Genre> genres, List<Rating> ratings, String description, int releaseYear, int numberOfSeasons) {

        super(title, directorsNames, actorsNames, genres, ratings, description);
        this.numberOfSeasons    = numberOfSeasons;
    }
    public Series() {
        this.series = new HashMap<>();
        super.setType("Series");
    }

    @Override
    public void displayInfo() {
        System.out.println();
        System.out.println(Actions.ANSI_UNDERLINE + Actions.ANSI_BOLD+ Actions.ANSI_ORANGE +
                super.getTitle().toUpperCase() +
                Actions.ANSI_RESET);
        System.out.println(numberOfSeasons + " seasons" + Actions.ANSI_YELLOW +"    ★" + super.getGrade() +
                Actions.ANSI_RESET + "   " + super.getReleaseYear());
        for (Genre i : super.getGenres())
            System.out.print("|" + i + "|");
        System.out.println("\n");
        System.out.println(super.getDescription());

        if (super.getDirectorsNames().size() == 1)
            System.out.print("Director: ");
        else
            System.out.print("Directors: ");
        if (!super.getDirectorsNames().isEmpty()) {
            for (int i = 0; i < super.getDirectorsNames().size()-1; i++)
                System.out.print(Actions.ANSI_LIGHT_BLUE +
                        super.getDirectorsNames().get(i) + Actions.ANSI_RESET + ", ");
            System.out.print(Actions.ANSI_LIGHT_BLUE +
                    super.getDirectorsNames().get(super.getDirectorsNames().size()-1)
                    + Actions.ANSI_RESET);
            System.out.println();
        }

        System.out.print("Cast: ");
        if (!super.getActorsNames().isEmpty()) {
            for (int i = 0; i < super.getActorsNames().size()-1; i++)
                System.out.print(Actions.ANSI_LIGHT_BLUE + super.getActorsNames().get(i) + Actions.ANSI_RESET + ", ");
            System.out.println(Actions.ANSI_LIGHT_BLUE +
                    super.getActorsNames().get(super.getActorsNames().size()-1)
                    + Actions.ANSI_RESET);
        }
        System.out.println();
        if (!series.isEmpty()) {
            for (int i = 1; i <= series.size(); i++) {
                String key = "Season " + i;
                System.out.print(key+ " :\n\t");
                for (Episode e : series.get(key))
                    System.out.print(e.getTitle() + " (" + e.getDuration() + ")" + " | ");
                System.out.println();
            }
        }

        System.out.println();
        System.out.println("User reviews:");
        super.getRatings().sort(Rating::compareTo);
        for (Rating r : super.getRatings()) {
            System.out.println(Actions.ANSI_LIGHT_GREEN + r.getUserName() +
                    Actions.ANSI_YELLOW + "    ★" + r.getRating() + Actions.ANSI_RESET);
            System.out.println("\t" + r.getComment());
        }
        System.out.println();

        System.out.println(Actions.ANSI_ORANGE +
                "----------------------------------------------------------------------" +
                Actions.ANSI_RESET);
    }
    public Series setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
        return this;
    }
    public Series setSeries(Map<String, List<Episode>> dictionary) {
        this.series = dictionary;
        return this;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public Map<String, List<Episode>> getSeries() {
        return series;
    }
}
