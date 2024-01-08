package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class Production extends Observable implements Comparable<Production> {
    private String title;
    private List<String> directorsNames,actorsNames;
    private List<Genre> genres;
    private List<Rating> ratings;
    private String description, type;
    private double grade;
    private int releaseYear;
    public abstract void displayInfo();

    public Production(String title, List<String> directorsNames, List<String> actorsNames,
                      List<Genre> genres, List<Rating> ratings, String description) {

        this.title          = title;
        this.directorsNames = directorsNames;
        this.actorsNames    = actorsNames;
        this.genres         = genres;
        this.ratings        = ratings;
        this.description    = description;
        this.grade          = getAverage();
    }
    public Production() {
        directorsNames  = new ArrayList<String>();
        actorsNames     = new ArrayList<String>();
        genres          = new ArrayList<Genre>();
        ratings         = new ArrayList<Rating>();
    }
    private double getAverage() {
        return 0;
    }
    @Override
    public int compareTo(@NotNull Production o) {
        return Integer.compare(this.ratings.size(), o.ratings.size());
    }
    public Production setTitle(String title) {
        this.title = title;
        return this;
    }
    public Production setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }
    public Production setDirectorsNames(List<String> directorsNames) {
        this.directorsNames = directorsNames;
        return this;
    }
    public Production setActorsNames(List<String> actorsNames) {
        this.actorsNames = actorsNames;
        return this;
    }
    public Production setGenres(List<Genre> genres) {
        this.genres = genres;
        return this;
    }
    public Production setRatings(List<Rating> ratings) {
        this.ratings = ratings;
        return this;
    }
    public Production setDescription(String description) {
        this.description = description;
        return this;
    }
    public Production setGrade(double grade) {
        this.grade = grade;
        return this;
    }
    public Production setType(String type) {
        this.type = type;
        return this;
    }

    public String getTitle() {
        return title;
    }
    public List<String> getDirectorsNames() {
        return directorsNames;
    }
    public List<String> getActorsNames() {
        return actorsNames;
    }
    public List<Genre> getGenres() {
        return genres;
    }
    public List<Rating> getRatings() {
        return ratings;
    }
    public String getDescription() {
        return description;
    }
    public double getGrade() {
        return grade;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public int getRatingsNumber() {
        return ratings.size();
    }
    public String getType() {
        return type;
    }

    public Boolean containsDataAbout(String content) {
        if (getTitle().equalsIgnoreCase(content))
            return true;
        for (Genre g : genres)
            if (g.toString().equalsIgnoreCase(content))
                return true;
        for (String d : directorsNames)
            if (d.equalsIgnoreCase(content))
                return true;
        for (String a : actorsNames)
            if (a.equalsIgnoreCase(content))
                return true;
        return false;
    }
    public Boolean containsTitle(String content) {
        return getTitle().equalsIgnoreCase(content);
    }
    public void addDirector(String name) {
        directorsNames.add(name);
    }
    public void addActor(String name) {
        actorsNames.add(name);
    }
    public void addGenre(Genre g) {
        genres.add(g);
    }
    public void addRating(Rating r) {
        ratings.add(r);
    }
    public Production clone(Production p) {
        Production clone = ProductionFactory.buildProduction(p.getType())
                .setActorsNames(p.getActorsNames())
                .setDescription(p.getDescription())
                .setDirectorsNames(p.getDirectorsNames())
                .setGenres(p.getGenres())
                .setGrade(p.getGrade())
                .setReleaseYear(p.getReleaseYear())
                .setRatings(p.getRatings())
                .setTitle(p.getTitle());
        if (p instanceof Movie)
            ((Movie) clone).setDuration(((Movie) p).getDuration());
        else
            ((Series) clone).setNumberOfSeasons(((Series) p).getNumberOfSeasons())
                    .setSeries(((Series) p).getSeries());

        return clone;
    }

    // TODO: getAverage() to determine the grade
}
