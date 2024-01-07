package org.example;

import java.util.List;

public class Movie extends Production{
    private String duration;  // movie's length in minutes

    public Movie(String title, List<String> directorsNames, List<String> actorsNames,
                 List<Genre> genres, List<Rating> ratings, String description, String duration, int releaseYear) {

        super(title, directorsNames, actorsNames, genres, ratings, description);
        this.duration = duration;

    }
    public Movie() {super.setType("Movie");}
    @Override
    public void displayInfo() {
        System.out.println();
        if (super.getTitle() != null) {
            System.out.println(Actions.ANSI_UNDERLINE + Actions.ANSI_BOLD+ Actions.ANSI_ORANGE +
                                super.getTitle().toUpperCase() +
                                Actions.ANSI_RESET);
            System.out.println(duration + Actions.ANSI_YELLOW +"    ★" + super.getGrade() +
                    Actions.ANSI_RESET + "   " + super.getReleaseYear());
            for (Genre i : super.getGenres())
                System.out.print("|" + i + "|");
            System.out.println("\n");
            System.out.println(super.getDescription());

            if (super.getDirectorsNames().size() == 1)
                System.out.print("Director: ");
            else
                System.out.print("Directors: ");
            for (String d : super.getDirectorsNames())
                System.out.print(Actions.ANSI_LIGHT_BLUE + d + " " + Actions.ANSI_RESET);
            System.out.println();

            System.out.print("Cast: ");
            if (!super.getActorsNames().isEmpty()) {
                for (int i = 0; i < super.getActorsNames().size()-1; i++)
                    System.out.print(Actions.ANSI_LIGHT_BLUE + super.getActorsNames().get(i) + Actions.ANSI_RESET + ", ");
                System.out.println(Actions.ANSI_LIGHT_BLUE +
                        super.getActorsNames().get(super.getActorsNames().size()-1)
                        + Actions.ANSI_RESET);
                System.out.println();
            }

            System.out.println("User reviews:");
            for (Rating r : super.getRatings()) {
                System.out.println(Actions.ANSI_LIGHT_GREEN + r.getUserName() +
                        Actions.ANSI_YELLOW + "    ★" + r.getRating() + Actions.ANSI_RESET);
                System.out.println("\t" + r.getComment());
            }
            System.out.println();

            System.out.println(Actions.ANSI_ORANGE+
                    "----------------------------------------------------------------------" +
                    Actions.ANSI_RESET);
        } else {
            System.out.println(Actions.ANSI_UNDERLINE + Actions.ANSI_BOLD+ Actions.ANSI_ORANGE +
                    "UNKOWN PRODUCTION WITH NO DETAILS" +
                    Actions.ANSI_RESET);
        }
    }

    public Movie setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public String getDuration() {
        return duration;
    }
}
