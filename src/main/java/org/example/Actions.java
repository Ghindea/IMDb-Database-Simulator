package org.example;

import org.example.exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

public class Actions {
    // ANSI escape code to reset formatting
    public static String ANSI_RESET = "\u001B[0m";
    // ANSI escape code for bold text
    public static String ANSI_BOLD = "\u001B[1m";
    // ANSI escape code for underlined text
    public static String ANSI_UNDERLINE = "\u001B[4m";
    // ANSI escape code for light green text
    public static String ANSI_LIGHT_GREEN = "\u001B[92m";
    // ANSI escape code for orange text (this may vary based on the terminal)
    public static String ANSI_ORANGE = "\u001B[38;5;208m";
    // ANSI escape code for yellow text
    public static String ANSI_YELLOW = "\u001B[33m";
    // ANSI escape code for light blue text
    public static String ANSI_LIGHT_BLUE = "\u001B[94m";
    // ANSI escape code for light pink text
    public static String ANSI_LIGHT_PINK = "\u001B[95m";
    // ANSI escape code for light red text
    public static String ANSI_LIGHT_RED = "\u001B[91m";
    // ANSI escape code for bright cyan text
    public static String ANSI_BRIGHT_CYAN = "\033[1;36m";
    public static void displayProductions() {

        System.out.println(ANSI_BOLD + ANSI_LIGHT_GREEN +
                           "----------------------------------------------------------------------" +
                           "\nMOVIES & SERIES ON IMDB\n" +
                           "----------------------------------------------------------------------" +
                           ANSI_RESET);
        while (true) {
            System.out.println("Sort by: " + ANSI_LIGHT_GREEN + "None" + ANSI_RESET + " | " +
                                ANSI_LIGHT_PINK + "Genre" + ANSI_RESET + " | " +
                                ANSI_YELLOW + "Number of ratings" + ANSI_RESET + " | " +
                                ANSI_LIGHT_RED + "Exit" + ANSI_RESET);

            System.out.print("Type option : ");
            String option = IMDB.in.nextLine();
            if (option.equalsIgnoreCase("exit"))
                break;
            switch (option.toLowerCase()) {
                case "none" -> {
                    for (Production p : IMDB.getInstance().getProductions())
                        p.displayInfo();
                }
                case "genre" -> {
                    System.out.println();
                    for (Genre i : Genre.values())
                        System.out.print("|" + i + "| ");
                    System.out.print("\nChoose genre : ");
                    String genreOption = IMDB.in.nextLine();
                    for (Production p : IMDB.getInstance().getProductions())
                        for (Genre g : p.getGenres())
                            if (g.toString().equalsIgnoreCase(genreOption)) {
                                p.displayInfo(); break;
                            }
                }
                case "rating", "number of ratings", "number" -> {
                    System.out.println("| Ascending | | Descending |");
                    System.out.print("Choose order : ");
                    ArrayList<Production> productionsClone = new ArrayList<>(IMDB.getInstance().getProductions());
                    String orderOption = IMDB.in.nextLine();
                    if (orderOption.equalsIgnoreCase("ascending"))
                        productionsClone.sort(Comparator.comparingInt(Production::getRatingsNumber));
                    else if (orderOption.equalsIgnoreCase("descending"))
                        productionsClone.sort(Comparator.comparingInt(Production::getRatingsNumber).reversed());

                    for (Production p : productionsClone)
                        p.displayInfo();
                }
                default -> {
                    System.out.println(ANSI_LIGHT_RED + "Invalid option! Try again." + ANSI_RESET);
                }
            }
        }

    }
    public static void displayActors() {
        System.out.println(ANSI_BOLD + ANSI_LIGHT_GREEN +
                "----------------------------------------------------------------------" +
                "\nACTORS ON IMDB\n" +
                "----------------------------------------------------------------------" +
                ANSI_RESET);
        while (true) {
            System.out.println("Sort by: " + ANSI_LIGHT_GREEN + "None" + ANSI_RESET + " | " +
                    ANSI_LIGHT_PINK + "Name ascending" + ANSI_RESET + " | " +
                    ANSI_YELLOW + "Name descending" + ANSI_RESET + " | " +
                    ANSI_LIGHT_RED + "Exit" + ANSI_RESET);

            System.out.print("Type option : ");
            String option = IMDB.in.nextLine();
            if (option.equalsIgnoreCase("exit"))
                break;
            ArrayList<Actor> actorsClone = new ArrayList<>(IMDB.getInstance().getActors());
            Collections.sort(actorsClone);
            switch (option.toLowerCase()) {
                case "none" -> {
                    for (Actor a : IMDB.getInstance().getActors())
                        a.displayInfo();
                }
                case "ascending", "name ascending", "a" -> {
                    for (Actor a : actorsClone)
                        a.displayInfo();
                }
                case "descending", "name descending", "d" -> {
                    for (int i = actorsClone.size()-1; i >= 0; i--)
                        actorsClone.get(i).displayInfo();
                }
            }
        }
    }
    public static void search() {
        System.out.println(ANSI_BOLD + ANSI_LIGHT_GREEN +
                "--------------------------------------------------------------------------------------------------------------------------------------------" +
                "\nProvide a text about an actor/production/genre/director to look for. Type exit to return to main menu.\n" +
                "--------------------------------------------------------------------------------------------------------------------------------------------" +
                ANSI_RESET);
        boolean ok = false;
        while (true) {
            System.out.print(ANSI_BOLD + ANSI_LIGHT_GREEN +
                               "Search IMDB : " + ANSI_RESET);
            String content = IMDB.in.nextLine();
            if (content.equalsIgnoreCase("exit"))
                break;
            for(Actor a : IMDB.getInstance().getActors())
                if (a.containsDataAbout(content))
                    {a.displayInfo(); ok = true;}
            for (Production p : IMDB.getInstance().getProductions())
                if (p.containsDataAbout(content))
                    {p.displayInfo(); ok = true;}

            if (!ok) System.out.println("Oops... can't find anything about " + content + ". Try something else.");
            ok = false;
        }
    }
    public static void modifyUsersList(User loggedUser) throws SelfAccountDeletedException {
        System.out.println(ANSI_BOLD + ANSI_LIGHT_GREEN +
                "----------------------------------------------------------------------" +
                "\nDO YOU WANT TO ADD [A] OR TO DELETE [D] AN USER?\n" +
                "----------------------------------------------------------------------" +
                ANSI_RESET);
        System.out.print("Type option: ");
        String option = IMDB.in.nextLine();
        switch (option.toLowerCase()) {
            case "a", "add" -> addUser();
            case "d", "delete" -> deleteUser(loggedUser);
        }
    }
    private static void addUser() {
        String email = null, password = null, name = null, type = null, gender = null, birthDate = null, country = null, confirm = "";
        int age;
        System.out.println("\n" + ANSI_UNDERLINE + ANSI_BOLD + ANSI_ORANGE +
                            "CREATE USER" + ANSI_RESET);

        while (!confirm.equalsIgnoreCase("confirm")) {
            System.out.print("Account type : ");
            type = IMDB.in.nextLine();
            System.out.print("Email : ");
            email = IMDB.in.nextLine();
            System.out.print("Name : ");
            name = IMDB.in.nextLine();
            System.out.print("Do you want an autogenerated password? [Y]/[N] ");
            String passOption = IMDB.in.nextLine();
            if (passOption.equalsIgnoreCase("n")) {
                System.out.print("Password: ");
                password = IMDB.in.nextLine();
            } else {
                password = Credentials.generateRandomPassword();
                System.out.println("Your password is : " + ANSI_LIGHT_RED + password + ANSI_RESET +  "; Don't share it with anyone!");
            }
            System.out.print("Country : ");
            country = IMDB.in.nextLine();
            System.out.print("Gender : ");
            gender = IMDB.in.nextLine();
            System.out.print("Birth date (YYYY-MM-DD) : ");
            birthDate = IMDB.in.nextLine();

            System.out.print("Type CONFIRM to save user : ");
            confirm = IMDB.in.nextLine();
        }
        AccountType accountType = null;
        switch (type.charAt(0)) {
            case 'A', 'a' -> {accountType = AccountType.Admin;}
            case 'C', 'c' -> {accountType = AccountType.Contributor;}
            case 'R', 'r' -> {accountType = AccountType.Regular;}
        }
        LocalDateTime dateOfBirth = LocalDate.parse(birthDate, Parser.dateFormat).atStartOfDay();
        age = Period.between(dateOfBirth.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();

        User newUser = UserFactory.buildUser(accountType)
                .setUserName(User.generateUserName(name))
                .setUserInfo(new Credentials(email, password), name, country, gender, age, dateOfBirth)
                .setUserXP(accountType.equals(AccountType.Admin) ? Integer.MAX_VALUE:0);

        if (newUser instanceof Admin)
            IMDB.getInstance().getAdmins().add((Admin) newUser);
        else if (newUser instanceof Contributor)
            IMDB.getInstance().getContributors().add((Contributor) newUser);
        else if (newUser instanceof Regular)
            IMDB.getInstance().getRegulars().add((Regular) newUser);

        System.out.println(ANSI_ORANGE + "User added successfully!\n" + ANSI_RESET);
    }
    private static void deleteUser(User loggedUser) throws SelfAccountDeletedException {
        System.out.println("\n" + ANSI_UNDERLINE + ANSI_BOLD + ANSI_LIGHT_RED +
                "DELETE USER" + ANSI_RESET);
        System.out.print("Type username : ");
        String username = IMDB.in.nextLine();
        User user = IMDB.getInstance().containsUser(username);
        if (user != null) {
            if (!loggedUser.getUserName().equalsIgnoreCase(username)) {
                System.out.println(ANSI_UNDERLINE + ANSI_BOLD + ANSI_LIGHT_RED +
                        "ARE YOU SURE YOU WANT TO DELETE USER "+ user.getUserName() +
                        "? [Y]/[N]"+ ANSI_RESET);
                System.out.print("Type option : ");
                String option = IMDB.in.nextLine();
                if (option.equalsIgnoreCase("y")) {
                    switch (user.getUserType()) {
                        case Admin -> IMDB.getInstance().getAdmins().remove(user);
                        case Regular -> IMDB.getInstance().getRegulars().remove(user);
                        case Contributor -> IMDB.getInstance().getContributors().remove(user);
                    }
                    System.out.println("User removed");
                }
            } else {
                System.out.println(ANSI_UNDERLINE + ANSI_BOLD + ANSI_LIGHT_RED +
                        "YOU WILL BE LOGGED OUT AND ALL YOUR DATA WILL BE DELETED. ARE YOU SURE YOU WANT TO DELETE YOUR OWN ACCOUNT?  [Y]/[N]"+ ANSI_RESET);
                System.out.print("Type option : ");
                String option = IMDB.in.nextLine();
                if (option.equalsIgnoreCase("y")) {
                    IMDB.getInstance().getAdmins().remove(loggedUser);
                    System.out.println("Your account has been deleted.");
                    throw new SelfAccountDeletedException();
                }
            }
        } else {
            System.out.println(UserDoesNotExistsException.message);
        }

    }
    public static void viewNotifications(User loggedUser) {
        if (loggedUser.getNotifications().isEmpty())
            System.out.println(ANSI_BOLD + "There are no new notifications.\n" + ANSI_RESET);
        else {
            for (String n : loggedUser.getNotifications())
                System.out.println("\t ↪ " + n);
            System.out.println();
            System.out.println(ANSI_LIGHT_GREEN + "Delete notifications? [Y]/[N]" + ANSI_RESET);
            System.out.print("Type option : ");
            String option = IMDB.in.nextLine();
            if (option.equalsIgnoreCase("y"))
                loggedUser.getNotifications().clear();
            System.out.println();
        }
    }
    public static void modifyFavoritesList(User loggedUser) {
        while (true) {
            System.out.println(ANSI_LIGHT_GREEN + "Your favorites : " + ANSI_RESET);
            if (loggedUser.getFavorites().isEmpty()) {
                System.out.println(ANSI_BOLD + "There are no pages in your favorites list.\n" + ANSI_RESET);
            } else {
                for (String f : loggedUser.getFavorites()) {
                    if (IMDB.getInstance().containsProductionTitle(f) != null)
                        System.out.println(ANSI_ORANGE + "\t↪ " + f + ANSI_RESET);
                    else
                        System.out.println(ANSI_LIGHT_BLUE + "\t↪ " + f + ANSI_RESET);
                }
                System.out.println();
            }
            System.out.println(ANSI_YELLOW + "|Add favorite| " + ANSI_RESET +
                               ANSI_LIGHT_RED + "|Remove favorite| " + ANSI_RESET +
                               ANSI_LIGHT_PINK + "|Exit|" + ANSI_RESET);
            System.out.print("Type option : ");
            String option = IMDB.in.nextLine();
            if (option.equalsIgnoreCase("exit"))
                break;
            switch (option.toLowerCase()) {
                case "a", "add", "add favorite" -> {
                    System.out.print("Type actor name/production title to add : ");
                    boolean ok = false;
                    String inputFav = IMDB.in.nextLine();
                    String actor = IMDB.getInstance().containsActorName(inputFav);
                    if (actor != null) {
                        loggedUser.getFavorites().add(actor);
                        System.out.println("Actor " + ANSI_LIGHT_BLUE + actor + ANSI_RESET + " was added to favorites list.");
                        ok = true;
                    }
                    String prodTitle = IMDB.getInstance().containsProductionTitle(inputFav);
                    if (prodTitle != null) {
                        Production prod = IMDB.getInstance().getProduction(prodTitle);
                        loggedUser.getFavorites().add(prod.getTitle());
                        System.out.println(prod.getType() + " " + ANSI_ORANGE + prod.getTitle() + ANSI_RESET + " was added to favorites list.");
                        ok = true;
                    }
                    if (!ok) {
                        System.out.println(PageDoesntExistException.message);
                    }
                }
                case "r", "d", "delete" , "remove", "remove favorite" -> {
                    System.out.print("Type actor name/production title to remove : ");
                    boolean ok = false;
                    String inputFav = IMDB.in.nextLine();
                    String actor = IMDB.getInstance().containsActorName(inputFav);
                    if (actor != null) {
                        if (loggedUser.getFavorites().contains(actor)) {
                            loggedUser.getFavorites().remove(actor);
                            System.out.println("Actor " + ANSI_LIGHT_BLUE + actor + ANSI_RESET + " was removed from favorites list.");
                            ok = true;
                        } else {
                            System.out.println(FavoritesListDoesNotContainElementException.message);
                        }
                    } else {
                        String prodTitle = IMDB.getInstance().containsProductionTitle(inputFav);
                        if (prodTitle != null) {
                            Production prod = IMDB.getInstance().getProduction(prodTitle);
                            if (loggedUser.getFavorites().contains(prod.getTitle())) {
                                loggedUser.getFavorites().remove(prod.getTitle());
                                System.out.println(prod.getType() + " " + ANSI_ORANGE + prod.getTitle() + ANSI_RESET + " was removed to favorites list.");
                                ok = true;
                            } else {
                                System.out.println(FavoritesListDoesNotContainElementException.message);
                            }
                        } else {
                            System.out.println(PageDoesntExistException.message);
                        }
                    }

                }
                default -> {
                    System.out.println(InvalidOptionException.message);
                }
            }
        }
        System.out.println();
    }
    public static void modifyPagesList(Staff loggedStaff) {
        System.out.println(ANSI_BOLD + ANSI_LIGHT_GREEN +
                "----------------------------------------------------------------------" +
                "\nDO YOU WANT TO ADD [A] OR TO DELETE [D] A PAGE?\n" +
                "----------------------------------------------------------------------" +
                ANSI_RESET);
        System.out.print("Type option: ");
        String option = IMDB.in.nextLine();
        switch (option.toLowerCase()) {
            case "a", "add" -> addPage(loggedStaff);
            case "d", "delete" -> deletePage(loggedStaff);
        }
    }
    private static void addPage(Staff loggedStaff) {
        System.out.println(ANSI_BOLD + ANSI_YELLOW +
                "\nCREATE A NEW PAGE\n" +
                "----------------------------------------------------------------------" +
                ANSI_RESET);
        System.out.println("Choose a new page type: " +
                ANSI_ORANGE + "|Production| " + ANSI_RESET +
                ANSI_LIGHT_BLUE + "|Actor|" + ANSI_RESET);
        System.out.print("Type option : ");
        String option = IMDB.in.nextLine();
        switch (option.toLowerCase()) {
            case "p", "production", "productions" -> {
                System.out.println(ANSI_BOLD + ANSI_ORANGE +
                        "\nCREATE A NEW PRODUCTION PAGE\n" +
                        "----------------------------------------------------------------------" +
                        ANSI_RESET);
                boolean ok = false; Production prod = null; String title = null, type = null;
                while (!ok) {
                    System.out.print("Production type (mandatory) : ");
                    type = IMDB.in.nextLine();
                    System.out.print(type + "'s title : ");
                    title = IMDB.in.nextLine();

                    prod = ProductionFactory.buildProduction(type);
                    if (prod != null) {
                        ok = true;
                    } else {
                        System.out.println(ANSI_LIGHT_RED + "Wrong data" + ANSI_RESET);
                    }
                }
                prod.setTitle(title);
                while (true) {
                    prod.displayInfo();
                    System.out.print("|Add description| |Add release year| |Add cast| |Add directors| |Add genres| ");
                    if (prod.getType().equals("Movie"))
                        System.out.print("|Duration| ");
                    else
                        System.out.print("|Seasons| ");
                    System.out.println("|Save details|");
                    System.out.print("Type option : ");
                    String detailsOption = IMDB.in.nextLine();
                    if (detailsOption.equalsIgnoreCase("save")) {
                        prod.addObserver(loggedStaff);
                        loggedStaff.addProductionSystem(prod);
                        System.out.println(prod.getType() + " page was created succesfully!");
                        break;
                    }
                    System.out.println();
                    switch (detailsOption.toLowerCase()) {
                        case "add description", "description" -> {
                            System.out.print("Type " + prod.getTitle() + "'s description : ");
                            String about = IMDB.in.nextLine();
                            prod.setDescription(about);
                        }
                        case "add release year", "release year", "release", "year" -> {
                            System.out.print("Type " + prod.getTitle() + "'s release year : ");
                            int year = Integer.parseInt(IMDB.in.nextLine());
                            prod.setReleaseYear(year);
                        }
                        case "duration" -> {
                            if (prod instanceof Movie) {
                                System.out.print("Type " + prod.getTitle() + "'s duration : ");
                                String duration = IMDB.in.nextLine();
                                ((Movie) prod).setDuration(duration);
                            } else {
                                System.out.println(ANSI_LIGHT_RED + "That's not a movie man..." + ANSI_RESET);
                            }
                        }
                        case "number of seasons", "seasons", "number" -> {
                            if (prod instanceof Series) {
                                System.out.print("Type " + prod.getTitle() + "'s number of seasons : ");
                                int no = 0;
                                try {
                                    no = Integer.parseInt(IMDB.in.nextLine());
                                    ((Series) prod).setNumberOfSeasons(no);
                                    Map<String, List<Episode>> series = new HashMap<>();
                                    for (int i = 1; i <= no; i++) {
                                        String seasonName = "Season " + i;
                                        System.out.print("Type " + seasonName + "'s number of episodes : ");
                                        int ne = 0;
                                        ne = Integer.parseInt(IMDB.in.nextLine());
                                        List<Episode> episodes = new ArrayList<>();
                                        for (int j = 1; j <= ne; j++) {
                                            System.out.print("Type episode "+ j  + " title: ");
                                            String eTitle = IMDB.in.nextLine();
                                            System.out.print("Type episode "+ j  + " duration: ");
                                            String eDuration = IMDB.in.nextLine();

                                            episodes.add(new Episode(eTitle, eDuration));
                                        }
                                        series.put(seasonName, episodes);
                                    }
                                    ((Series) prod).setSeries(series);
                                } catch (Exception e) {
                                    System.out.println(InvalidOptionException.message);
                                }
                            } else {
                                System.out.println(ANSI_LIGHT_RED + "That's not a series man..." + ANSI_RESET);
                            }
                        }
                        case "directors", "director", "dir" -> {
                            System.out.println("After typing all directors, type DONE");
                            while (true) {
                                System.out.print("Director's name: ");
                                String name = IMDB.in.nextLine();
                                if (name.equalsIgnoreCase("done"))
                                    break;
                                prod.addDirector(name);
                            }
                        }
                        case "cast", "actors" -> {
                            System.out.println("After typing all actors, type DONE");
                            while (true) {
                                System.out.print("Actor's name: ");
                                String name = IMDB.in.nextLine();
                                if (name.equalsIgnoreCase("done"))
                                    break;
                                prod.addActor(name);
                                if (IMDB.getInstance().containsActorName(name) == null)
                                    IMDB.getInstance().addActor(name);
                                IMDB.getInstance().getActor(name).addPerformance(title, type);
                            }
                        }
                        case "genre", "genres" -> {
                            System.out.println("After typing all genres, type DONE");
                            while (true) {
                                System.out.print("Genre: ");
                                String g = IMDB.in.nextLine();
                                if (g.equalsIgnoreCase("done"))
                                    break;
                                try {
                                    prod.addGenre(Genre.valueOf(g));
                                } catch (Exception e) {
                                    System.out.println(ANSI_LIGHT_RED + "Invalid genre" + ANSI_RESET);
                                }
                            }
                        }
                    }
                }
            }
            case "a", "actor", "actors" -> {
                System.out.println(ANSI_BOLD + ANSI_LIGHT_BLUE +
                        "\nCREATE A NEW ACTOR PAGE\n" +
                        "----------------------------------------------------------------------" +
                        ANSI_RESET);
                Actor newActor = new Actor();
                System.out.print("Actor name (mandatory): ");
                String name = IMDB.in.nextLine();
                newActor.setName(name);
                while (true) {
                    newActor.displayInfo();
                    System.out.print("|Add biography| |Add performances| |Save details|" +
                                       "\nType option: ");
                    String detailsOption = IMDB.in.nextLine();
                    if (detailsOption.equalsIgnoreCase("save")) {
                        newActor.addObserver(loggedStaff);
                        loggedStaff.addActorSystem(newActor);
                        System.out.println("Actor page was created succesfully!");
                        break;
                    }
                    System.out.println();
                    switch (detailsOption.toLowerCase()) {
                        case "add biography", "biography", "bio" -> {
                            System.out.print("Type " + name +"'s biography : ");
                            newActor.setBiography(IMDB.in.nextLine());
                        }
                        case "add performances", "performances", "performance", "p" -> {
                            System.out.println("After typing all performances, type DONE");
                            while (true) {
                                System.out.println("Performance title: ");
                                String title = IMDB.in.nextLine();
                                if (title.equalsIgnoreCase("done"))
                                    break;
                                System.out.println("Performance type: ");
                                String type = IMDB.in.nextLine();
                                newActor.addPerformance(title, type);
                                if (IMDB.getInstance().containsProductionTitle(title) == null)
                                    IMDB.getInstance().addProduction(title, type);
                                IMDB.getInstance().getProduction(title).getActorsNames().add(name);
                            }
                        }
                    }
                }
            }
        }
    }
    private static void deletePage(Staff loggedStaff) {
        System.out.println(ANSI_BOLD + ANSI_LIGHT_RED +
                "\nDELETE PAGE\n" +
                "----------------------------------------------------------------------" +
                ANSI_RESET);
        System.out.print("Type actor name/production title : ");
        String name = IMDB.in.nextLine();
        boolean ok = false;
        for (Actor a : IMDB.getInstance().getActors()) {
            if (a.getName().equalsIgnoreCase(name)) {
                loggedStaff.removeActorSystem(a.getName());
                System.out.println("Actor removed");
                ok = true;
                break;
            }
        }
        for (Production p : IMDB.getInstance().getProductions()) {
            if (p.getTitle().equalsIgnoreCase(name)) {
                loggedStaff.removeProductionSystem(p.getTitle());
                System.out.println("Production removed");
                ok = true;
                break;
            }
        }
        if (!ok) {
            System.out.println("Invalid text! Try again");
        }
    }
    public static void modifyPage(Staff loggedStaff) {
        System.out.println(ANSI_BOLD + ANSI_LIGHT_GREEN +
                "----------------------------------------------------------------------" +
                "\nDO YOU WANT TO UPDATE A PRODUCTION PAGE [P] OR AN ACTOR PAGE [A]?\n" +
                "----------------------------------------------------------------------" +
                ANSI_RESET);
        System.out.print("Type option: ");
        String option = IMDB.in.nextLine();
        switch (option.toLowerCase()) {
            case "p", "production" -> updateProduction(loggedStaff);
            case "a", "actor" -> updateActor(loggedStaff);
        }
    }
    private static void updateProduction(Staff loggedStaff) {
        System.out.println(ANSI_BOLD + ANSI_ORANGE +
                "\nUPDATE PRODUCTION PAGE\n" +
                "----------------------------------------------------------------------" +
                ANSI_RESET);
        boolean ok = false; Production prod = null; String title = null, type = null;
        while (!ok) {
            System.out.print("Type production title : ");
            title = IMDB.in.nextLine();

            prod = IMDB.getInstance().getProduction(title);
            if (prod != null) {
                ok = true;
                prod = prod.clone(prod);
            } else {
                System.out.println(ANSI_LIGHT_RED + "Wrong title" + ANSI_RESET);
            }
        }
        while (true) {
            prod.displayInfo();
            System.out.print("|Edit description| |Edit release year| |Edit cast| |Edit directors| |Edit genres| ");
            if (prod.getType().equals("Movie"))
                System.out.print("|Edit Duration| ");
            else
                System.out.print("|Edit Seasons| ");
            System.out.println("|Save details|");
            System.out.print("Type option : ");
            String detailsOption = IMDB.in.nextLine();
            if (detailsOption.equalsIgnoreCase("save")) {
                loggedStaff.updateProduction(prod);
                System.out.println(prod.getType() + " page was edited succesfully!");
                break;
            }
            System.out.println();
            switch (detailsOption.toLowerCase()) {
                case "edit description", "description" -> {
                    System.out.print("Type " + prod.getTitle() + "'s new description : ");
                    String about = IMDB.in.nextLine();
                    prod.setDescription(about);
                }
                case "edit release year", "release year", "release", "year" -> {
                    System.out.print("Type " + prod.getTitle() + "'s new release year : ");
                    int year = Integer.parseInt(IMDB.in.nextLine());
                    prod.setReleaseYear(year);
                }
                case "edit duration", "duration" -> {
                    if (prod instanceof Movie) {
                        System.out.print("Type " + prod.getTitle() + "'s new duration : ");
                        String duration = IMDB.in.nextLine();
                        ((Movie) prod).setDuration(duration);
                    } else {
                        System.out.println(ANSI_LIGHT_RED + "That's not a movie man..." + ANSI_RESET);
                    }
                }
                case "number of seasons", "seasons", "number" -> {
                    if (prod instanceof Series) {
                        System.out.print("Type " + prod.getTitle() + "'s new number of seasons : ");
                        int no = 0;
                        try {
                            no = Integer.parseInt(IMDB.in.nextLine());
                            ((Series) prod).setNumberOfSeasons(no);
                            Map<String, List<Episode>> series = new HashMap<>();
                            for (int i = 1; i <= no; i++) {
                                String seasonName = "Season " + i;
                                System.out.print("Type " + seasonName + "'s number of episodes : ");
                                int ne = 0;
                                ne = Integer.parseInt(IMDB.in.nextLine());
                                List<Episode> episodes = new ArrayList<>();
                                for (int j = 1; j <= ne; j++) {
                                    System.out.print("Type episode "+ j  + " title: ");
                                    String eTitle = IMDB.in.nextLine();
                                    System.out.print("Type episode "+ j  + " duration: ");
                                    String eDuration = IMDB.in.nextLine();

                                    episodes.add(new Episode(eTitle, eDuration));
                                }
                                series.put(seasonName, episodes);
                            }
                            ((Series) prod).setSeries(series);
                        } catch (Exception e) {
                            System.out.println(InvalidOptionException.message);
                        }
                    } else {
                        System.out.println(ANSI_LIGHT_RED + "That's not a series man..." + ANSI_RESET);
                    }
                }
                case "edit directors", "directors", "director", "dir" -> {
                    prod.getDirectorsNames().clear();
                    System.out.println("After typing all directors, type DONE");
                    while (true) {
                        System.out.print("Director's name: ");
                        String name = IMDB.in.nextLine();
                        if (name.equalsIgnoreCase("done"))
                            break;
                        prod.addDirector(name);
                    }
                }
                case "edit cast", "cast", "actors" -> {
                    prod.getActorsNames().clear();
                    System.out.println("After typing all actors, type DONE");
                    while (true) {
                        System.out.print("Actor's name: ");
                        String name = IMDB.in.nextLine();
                        if (name.equalsIgnoreCase("done"))
                            break;
                        prod.addActor(name);
                        if (IMDB.getInstance().containsActorName(name) == null)
                            IMDB.getInstance().addActor(name);
                        IMDB.getInstance().getActor(name).addPerformance(title, type);
                    }
                }
                case "edit genres", "genre", "genres" -> {
                    prod.getGenres().clear();
                    System.out.println("After typing all genres, type DONE");
                    while (true) {
                        System.out.print("Genre: ");
                        String g = IMDB.in.nextLine();
                        if (g.equalsIgnoreCase("done"))
                            break;
                        try {
                            prod.addGenre(Genre.valueOf(g));
                        } catch (Exception e) {
                            System.out.println(ANSI_LIGHT_RED + "Invalid genre! (Try to capitalize the word)" + ANSI_RESET);
                        }
                    }
                }
            }
        }
    }
    private static void updateActor(Staff loggedStaff) {
        System.out.println(ANSI_BOLD + ANSI_LIGHT_BLUE +
                "\nEDIT ACTOR PAGE\n" +
                "----------------------------------------------------------------------" +
                ANSI_RESET);
        System.out.print("Actor name : ");
        String name = IMDB.in.nextLine();
        if (IMDB.getInstance().getActor(name) != null) {
            Actor newActor = new Actor().clone(IMDB.getInstance().getActor(name));
            while (true) {
                newActor.displayInfo();
                System.out.print("|Edit biography| |Edit performances| |Save details|" +
                        "\nType option: ");
                String detailsOption = IMDB.in.nextLine();
                if (detailsOption.equalsIgnoreCase("save")) {
                    loggedStaff.updateActor(newActor);
                    System.out.println("Actor page was created succesfully!");
                    break;
                }
                System.out.println();
                switch (detailsOption.toLowerCase()) {
                    case "edit biography", "biography", "bio" -> {
                        System.out.print("Type " + name +"'s new biography : ");
                        newActor.setBiography(IMDB.in.nextLine());
                    }
                    case "edit performances", "performances", "performance", "p" -> {
                        System.out.println("After typing all performances, type DONE");
                        while (true) {
                            System.out.println("Performance title: ");
                            String title = IMDB.in.nextLine();
                            if (title.equalsIgnoreCase("done"))
                                break;
                            System.out.println("Performance type: ");
                            String type = IMDB.in.nextLine();
                            newActor.addPerformance(title, type);
                            if (IMDB.getInstance().containsProductionTitle(title) == null)
                                IMDB.getInstance().addProduction(title, type);
                            IMDB.getInstance().getProduction(title).getActorsNames().add(name);
                        }
                    }
                }
            }
        } else {
            System.out.println(PageDoesntExistException.message);
        }
    }
    public static void modifyRatingsList(Regular loggedRegular) {
        System.out.println(ANSI_BOLD + ANSI_LIGHT_GREEN +
                "----------------------------------------------------------------------" +
                "\nDO YOU WANT TO ADD [A] OR TO DELETE [D] A RATING?\n" +
                "----------------------------------------------------------------------" +
                ANSI_RESET);
        System.out.print("Type option: ");
        String option = IMDB.in.nextLine();
        switch (option.toLowerCase()) {
            case "a", "add" -> {
                while (true) {
                    try {
                        addRating(loggedRegular);
                        break;
                    } catch (InvalidOptionException e) {
                        IMDB.in.nextLine();
                        System.out.println(e.getMessage());
                    }
                }
            }
            case "d", "delete" -> deleteRating(loggedRegular);
        }
    }
    private static void addRating(Regular loggedRegular) throws InvalidOptionException {
        System.out.println(ANSI_BOLD + ANSI_LIGHT_GREEN +
                "\nSELECT A PRODUCTION TO RATE\n" +
                "----------------------------------------------------------------------" +
                ANSI_RESET);
        while (true) {
            System.out.print("Type production's title : ");
            String title = IMDB.in.nextLine();
            Production p = IMDB.getInstance().getProduction(title);
            if (p != null) {
                System.out.print("Type comment : ");
                String comment = IMDB.in.nextLine();
                int rating = 0;
                while (true) {
                    System.out.print("Type rating value [" +
                            ANSI_YELLOW + "1★" + ANSI_RESET + " -> " + ANSI_YELLOW + "10★" + ANSI_RESET +"] : ");
                    try {
                        rating = IMDB.in.nextInt();
                        IMDB.in.nextLine();
                        if (rating > 10 || rating < 1)
                            throw new InvalidOptionException();
                        break;
                    } catch (InputMismatchException | InvalidOptionException e) {
                        throw new InvalidOptionException();
                    }
                }
                Rating r = new Rating().setUserName(loggedRegular.getUserName())
                        .setRating(rating)
                        .setComment(comment);
                loggedRegular.addRating(p,r);
                System.out.println("Rating added successfully!");
                break;
            } else {
                System.out.println(PageDoesntExistException.message);
            }
        }
    }
    private static void deleteRating(Regular loggedRegular) {
        System.out.println(ANSI_BOLD + ANSI_LIGHT_GREEN +
                "\nSELECT A PRODUCTION TO DELETE ITS RATING\n" +
                "----------------------------------------------------------------------" +
                ANSI_RESET);
        while (true) {
            System.out.print("Type production's title : ");
            String title = IMDB.in.nextLine();
            Production p = IMDB.getInstance().getProduction(title);
            boolean ok = false;
            if (p != null) {
                if (loggedRegular.deleteRating(p))
                    System.out.println("Rating deleted successfully!");
                else
                    System.out.println("You haven't rated " + p.getTitle());
                break;
            } else {
                System.out.println(PageDoesntExistException.message);
            }
        }

    }
    public static void modifyRequestsList(User loggedUser) {
        System.out.println(ANSI_BOLD + ANSI_LIGHT_GREEN +
                "----------------------------------------------------------------------" +
                "\nDO YOU WANT TO ADD [A] OR TO DELETE [D] A REQUEST?\n" +
                "----------------------------------------------------------------------" +
                ANSI_RESET);
        System.out.print("Type option: ");
        String option = IMDB.in.nextLine();
        switch (option.toLowerCase()) {
            case "a", "add" -> addRequest(loggedUser);
            case "d", "delete" -> deleteRequest(loggedUser);
        }
    }
    private static void addRequest(User loggedUser) {
        System.out.println("\n" + ANSI_UNDERLINE + ANSI_BOLD + ANSI_LIGHT_GREEN +
                "CREATE REQUEST" + ANSI_RESET);
        String confirm = "";
        while (!confirm.equalsIgnoreCase("confirm")) {
            System.out.println("Choose a request type: " + Arrays.toString(RequestType.values()));
            String option = IMDB.in.nextLine();
            switch (option.toLowerCase()) {
                case "delete_account", "delete", "del" -> {
                    System.out.print("Request description: ");
                    String description = IMDB.in.nextLine();
                    System.out.print(ANSI_LIGHT_RED + "Are you sure you want to delete your own account? [Y]/[N] " + ANSI_RESET);
                    String idk = IMDB.in.nextLine();
                    if (idk.equalsIgnoreCase("y")) {
                        Request r = new Request().setType(RequestType.DELETE_ACCOUNT)
                                                 .setRequestDate(LocalDateTime.now())
                                                 .setToUser("ADMIN")
                                                 .setFromUserName(loggedUser.getUserName())
                                                 .setDescription(description);
                        System.out.print("Type CONFIRM to delete your account :");
                        confirm = IMDB.in.nextLine();
                        if (confirm.equalsIgnoreCase("confirm")) {
                            ((RequestManager) loggedUser).createRequest(r);
                            System.out.println("Request created succesfully!");
                        }
                    }
                }
                case "other", "others" -> {
                    System.out.print("Request description: ");
                    String description = IMDB.in.nextLine();
                    Request r = new Request().setType(RequestType.OTHERS)
                                            .setRequestDate(LocalDateTime.now())
                                            .setToUser("ADMIN")
                                            .setFromUserName(loggedUser.getUserName())
                                            .setDescription(description);
                    System.out.print("Type CONFIRM to create request :");
                    confirm = IMDB.in.nextLine();
                    if (confirm.equalsIgnoreCase("confirm")) {
                        ((RequestManager) loggedUser).createRequest(r);
                        System.out.println("Request created succesfully!");
                    }
                }
                case "movie", "movie_issue", "series", "series_issue" -> {
                    System.out.print("Productions's title : ");
                    String title = IMDB.in.nextLine();
                    Production production = IMDB.getInstance().getProduction(title);
                    if (production != null) {
                        System.out.print("Request description: ");
                        String description = IMDB.in.nextLine();
                        Request r = new Request().setRequestDate(LocalDateTime.now())
                                .setFromUserName(loggedUser.getUserName())
                                .setDescription(description)
                                .setTitle(production.getTitle())
                                .setToUser("staff");
                        if (production.getType().equals("Movie"))
                            r.setType(RequestType.MOVIE_ISSUE);
                        else
                            r.setType(RequestType.SERIES_ISSUE);

                        System.out.print("Type CONFIRM to create request :");
                        confirm = IMDB.in.nextLine();
                        if (confirm.equalsIgnoreCase("confirm")) {
                            ((RequestManager) loggedUser).createRequest(r);
                            System.out.println("Request created succesfully!");
                        }
                    } else {
                        System.out.println(PageDoesntExistException.message);
                    }
                }
                case "actor", "actor_issue" -> {
                    System.out.print("Actor's name : ");
                    String name = IMDB.in.nextLine();
                    Actor actor = IMDB.getInstance().getActor(name);
                    if (actor != null) {
                        System.out.print("Request description: ");
                        String description = IMDB.in.nextLine();
                        Request r = new Request().setRequestDate(LocalDateTime.now())
                                .setType(RequestType.ACTOR_ISSUE)
                                .setFromUserName(loggedUser.getUserName())
                                .setDescription(description)
                                .setTitle(actor.getName())
                                .setToUser("staff");

                        System.out.print("Type CONFIRM to create request :");
                        confirm = IMDB.in.nextLine();
                        if (confirm.equalsIgnoreCase("confirm")) {
                            ((RequestManager) loggedUser).createRequest(r);
                            System.out.println("Request created succesfully!");
                        }
                    } else {
                        System.out.println(PageDoesntExistException.message);
                    }
                }
                default -> System.out.println(InvalidOptionException.message);
            }
        }
    }
    private static void deleteRequest(User loggedUser) {
        System.out.println("\n" + ANSI_UNDERLINE + ANSI_BOLD + ANSI_LIGHT_GREEN +
                "DELETE REQUEST" + ANSI_RESET);
        List<Request> ownedRequests = loggedUser.ownedRequests();
        if (!ownedRequests.isEmpty()) {
            System.out.println("Type done when you are done =)");
            while (true) {
                ownedRequests = loggedUser.ownedRequests();
                System.out.println("Your requests: ");
                for (Request r : ownedRequests) {
                    System.out.println("\t" + ownedRequests.indexOf(r) + ") " + r.getType() + " : " + r.getDescription() + " at " + r.getRequestDate().format(Parser.dateTimeFormat));
                }
                System.out.println("Choose request number to delete : ");
                String done = IMDB.in.nextLine();
                if (done.equalsIgnoreCase("done"))
                    break;
                if (ownedRequests.isEmpty()) {
                    System.out.println("You don't have any requests!");
                    break;
                }
                if (RequestHolder.requestsForAdmins.contains(ownedRequests.get(Integer.parseInt(done))))
                    ((RequestManager) loggedUser).removeRequest(ownedRequests.get(Integer.parseInt(done)));
                if (RequestHolder.globalRequests.contains(ownedRequests.get(Integer.parseInt(done))))
                    ((RequestManager) loggedUser).removeRequest(ownedRequests.get(Integer.parseInt(done)));
                System.out.println("Request removed");
            }

        } else {
            System.out.println("You don't have any requests!");
        }

    }
    public static void solveRequests(User loggedUser) {
        System.out.println(ANSI_BOLD + ANSI_LIGHT_GREEN +
                "----------------------------------------------------------------------" +
                "\nREQUESTS AVAILABLE FOR YOU:\n" +
                "----------------------------------------------------------------------" +
                ANSI_RESET);
        List<Request> ownedRequests = new ArrayList<>();
        while (true) {
            ownedRequests.clear();
            for (Request r : RequestHolder.globalRequests) {
                if (r.getToUser().equals(loggedUser.getUserName()) ||
                        r.getToUser().equalsIgnoreCase("Staff") && loggedUser instanceof Staff)
                    ownedRequests.add(r);
            }
            if (loggedUser instanceof Admin) {
                ownedRequests.addAll(RequestHolder.requestsForAdmins);
            }
            System.out.println("Your requests : ");
            for (Request r : ownedRequests) {
                r.displayInfo(ownedRequests.indexOf(r));
                System.out.println();
            }
            if (!ownedRequests.isEmpty()) {
                System.out.print("Choose request index to solve :");
                String rIndex = IMDB.in.nextLine();
                if (rIndex.equalsIgnoreCase("exit") || rIndex.equalsIgnoreCase("done"))
                    break;
                int index = Integer.parseInt(rIndex);
                if (index < ownedRequests.size()) {
                    Request request = ownedRequests.get(index);
                    if (request != null) {
                        request.displayInfo(index);
                        System.out.println();
                        System.out.println("|Mark as accepted| |Mark as rejected|");
                        System.out.print("Type option : ");
                        String option = IMDB.in.nextLine();

                        String message;
                        if (request.getTitle() != null) {
                            message = "\t↪ " + index + ") " + Actions.ANSI_BRIGHT_CYAN + request.getType() + Actions.ANSI_RESET +
                                    " from " + request.getFromUserName() +
                                    " : " + request.getTitle() + " ⇒ \"" + Actions.ANSI_LIGHT_GREEN + request.getDescription() + Actions.ANSI_RESET +"\" at " + request.getRequestDate();
                        }
                        else {
                            message = "\t↪ " + index + ") " + Actions.ANSI_BRIGHT_CYAN + request.getType() +Actions.ANSI_RESET
                                    + " from " + request.getFromUserName() +
                                    " ⇒ \"" + Actions.ANSI_LIGHT_GREEN + request.getDescription() + Actions.ANSI_RESET + "\" at " + request.getRequestDate();
                        }
                        switch (option.toLowerCase()) {
                            case "accept", "accepted", "accept request", "mark as accepted" -> {
                                RequestHolder.globalRequests.remove(request);
                                RequestHolder.requestsForAdmins.remove(request);
                                request.notify("Request" + message + " was accepted");
                                User u = IMDB.getInstance().getUser(request.getFromUserName());
                                if (u != null) {
                                    u.setUserXP(u.getUserXP() + 5);
                                }
                                loggedUser.setUserXP(loggedUser.getUserXP() + 10);
                                System.out.println("\nRequest accepted!");
                            }
                            case "reject", "rejected", "reject reques", "mark as rejected" -> {
                                RequestHolder.globalRequests.remove(request);
                                RequestHolder.requestsForAdmins.remove(request);
                                request.notify("Request" + message + " was rejected");
                                System.out.println("\nRequest rejected!");
                            }
                        }
                    }
                } else {
                    System.out.println(InvalidOptionException.message);
                }
            } else {
                System.out.println("You don't have requests!");
                break;
            }
        }

    }
    public static void clearScreen() {
//        try {
//            String os = System.getProperty("os.name").toLowerCase();
//
//            if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
//                // For Unix-like operating systems (Linux and macOS)
//                new ProcessBuilder("clear").inheritIO().start();
//            } else if (os.contains("win")) {
//                // For Windows
//                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//            }
//        } catch (Exception e) {
//            System.out.println();
//        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static String imdbLogo="░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n" +
                                  "░   ░   ░░░░░░░   ░      ░░░░░     ░░░░\n" +
                                  "▒   ▒  ▒   ▒▒▒    ▒   ▒▒▒   ▒▒  ▒▒   ▒▒\n" +
                                  "▒   ▒   ▒   ▒ ▒   ▒   ▒▒▒▒   ▒  ▒▒▒   ▒\n" +
                                  "▓   ▓   ▓▓   ▓▓   ▓   ▓▓▓▓   ▓      ▓▓▓\n" +
                                  "▓   ▓   ▓▓▓  ▓▓   ▓   ▓▓▓▓   ▓  ▓▓▓▓   \n" +
                                  "▓   ▓   ▓▓▓▓▓▓▓   ▓   ▓▓▓   ▓▓  ▓▓▓▓▓  \n" +
                                  "█   █   ███████   █      █████    █   █\n" +
                                  "███████████████████████████████████████";
    public static String imdbLogo2="                                         \n" +
                                   "▀████▀████▄     ▄███▀███▀▀▀██▄ ▀███▀▀▀██▄\n" +
                                   "  ██   ████    ████   ██    ▀██▄ ██    ██\n" +
                                   "  ██   █ ██   ▄█ ██   ██     ▀██ ██    ██\n" +
                                   "  ██   █  ██  █▀ ██   ██      ██ ██▀▀▀█▄▄\n" +
                                   "  ██   █  ██▄█▀  ██   ██     ▄██ ██    ▀█\n" +
                                   "  ██   █  ▀██▀   ██   ██    ▄██▀ ██    ▄█\n" +
                                   "▄████▄███▄ ▀▀  ▄████▄████████▀ ▄████████ \n" +
                                   "                                         \n" +
                                   "                                         ";
}
