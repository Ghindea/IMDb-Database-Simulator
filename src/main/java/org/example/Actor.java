package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Actor implements Comparable<Object>{
    private String name, biography;
    private List<Performance> performances;

    @Override
    public int compareTo(@NotNull Object o) {
        return name.compareTo(((Actor) o).name);
    }

    public class Performance {
        private String title, type;
        public Performance(String title, String type) {
            this.title = title;
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public String getType() {
            return type;
        }

        public Performance setTitle(String title) {
            this.title = title;
            return this;
        }

        public Performance setType(String type) {
            this.type = type;
            return this;
        }
    }
    Actor() {performances = new ArrayList<>();}
    public List<Performance> getPerformances() {
        return performances;
    }
    public Actor setName(String name) {
        this.name = name;
        return this;
    }
    public Actor setBiography(String biography) {
        this.biography = biography;
        return this;
    }
    public Actor setPerformances(List<Performance> performances) {
        this.performances = performances;
        return this;
    }
    public void addPerformance(String title, String type) {
        performances.add(new Performance(title, type));
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public void displayInfo() {
        System.out.println();
        if (name != null) {
            System.out.println(Actions.ANSI_UNDERLINE + Actions.ANSI_BOLD + Actions.ANSI_LIGHT_BLUE +
                        name.toUpperCase() +
                        Actions.ANSI_RESET);
            System.out.println();
            if (biography == null)
                System.out.println(name + " doesn't have a biography.");
            else
                System.out.println(biography);

            System.out.println();
            if (performances.isEmpty()) {
                System.out.println(name + " doesn't have listed performances.");
            } else {
                System.out.println("Performances: ");
                for (Performance p : performances)
                    System.out.println("\t" + p.title + " (" + p.type + ")");
            }
        } else {
            System.out.println(Actions.ANSI_UNDERLINE + Actions.ANSI_BOLD + Actions.ANSI_LIGHT_BLUE +
                    "UNKNOWN ACTOR WITH NO DATA" +
                    Actions.ANSI_RESET);
        }

        System.out.println();
        System.out.println(Actions.ANSI_LIGHT_BLUE +
                "----------------------------------------------------------------------" +
                Actions.ANSI_RESET);
    }

    public Boolean containsDataAbout(String content) {
        return this.name.equalsIgnoreCase(content);
    }
    public Actor clone(Actor a) {
        Actor clone = new Actor();
        clone.setBiography(a.getBiography())
                .setName(a.getName())
                .setPerformances(a.getPerformances());

        return clone;
    }
}
