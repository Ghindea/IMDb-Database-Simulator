package org.example;

import org.example.exceptions.InvalidCommandException;
import org.example.exceptions.InvalidOptionException;
import org.example.exceptions.SelfAccountDeletedException;

import java.util.*;

public class IMDB {
    private static IMDB instance = null;
    public static Scanner in;
    private List<Regular> regulars;
    private List<Contributor> contributors;
    private List<Admin> admins;
    private List<Actor> actors;
    private List<Request> requests;
    private List<Production> productions;
    private IMDB() {
        regulars        = new ArrayList<Regular>();
        contributors    = new ArrayList<Contributor>();
        admins          = new ArrayList<Admin>();
        actors          = new ArrayList<Actor>();
        requests        = new ArrayList<Request>();
        productions     = new ArrayList<Production>();
        in = new Scanner(System.in);
    }
    public void run() {
        Parser.parseDatabaseToMemory();
        Parser.solveDiscrepancies();
        while (true) {
            Actions.clearScreen();
            System.out.println(Actions.ANSI_ORANGE + Actions.imdbLogo2 + Actions.ANSI_RESET);

            User loggedUser = Login.start(in);

            switch (loggedUser.getUserType()) {
                case Admin -> {
                    while (true) {
                      try {
                          runAdminMode((Admin) loggedUser);
                          break;
                      } catch (InvalidCommandException e) {
                          IMDB.in.nextLine();
                          System.out.println(e.getMessage());
                      }
                    }
                }
                case Contributor -> {
                    while (true) {
                        try {
                            runContributorMode((Contributor) loggedUser);
                            break;
                        } catch (InvalidCommandException e) {
                            IMDB.in.nextLine();
                            System.out.println(e.getMessage());
                        }
                    }
                }
                case Regular -> {
                    while (true) {
                        try {
                            runRegularMode((Regular) loggedUser);
                            break;
                        } catch (InvalidCommandException e) {
                            IMDB.in.nextLine();
                            System.out.println(e.getMessage());
                        }
                    }
                }

            }

            System.out.println(Actions.ANSI_LIGHT_GREEN + "\nLogin? [Y]/[N]" +Actions.ANSI_RESET);
            System.out.print("Type option ");
            String option = IMDB.in.nextLine();
            if (!option.equalsIgnoreCase("y"))
                break;
            System.out.println();
        }
        Parser.parseDatabaseToJSONs();

        in.close();
    }

    private void runAdminMode(Admin loggedAdmin) throws InvalidCommandException {
        int option = 0;
        while (option != 10) {
            String notifications;
            if (loggedAdmin.getNotifications().isEmpty())
                notifications = Actions.ANSI_BOLD + "No new notifications" + Actions.ANSI_RESET;
            else
                notifications = Actions.ANSI_BOLD + loggedAdmin.getNotifications().size() +" new notifications" + Actions.ANSI_RESET;
            String menuText = Actions.ANSI_LIGHT_GREEN +
                    "--------------------------------------------------------------------------------------------------------------------------------------------" +
                    "\nNotifications : " + notifications +
                    "\nChoose action:\n" + Actions.ANSI_RESET +
                    "    1)  View production details                             6)  Add/Delete user\n" +
                    "    2)  View actors details                                 7)  Add/Delete actor/production from system\n" +
                    "    3)  View notifications                                  8)  Update actor/production details\n" +
                    "    4)  Search database                                     9)  Solve a request\n" +
                    "    5)  View/Modify favorites list                          10) Logout\n";
            System.out.print(menuText + "\nType action number: ");
            try {
                option = IMDB.in.nextInt();
                IMDB.in.nextLine();
            } catch (InputMismatchException e) {
                throw new InvalidCommandException("Please introduce a number!");
            }
            System.out.println();
            if (option > 10) {
                System.out.println(InvalidOptionException.message);
            } else {
                switch (option) {
                    case 1: {
                        Actions.displayProductions(); break;
                    }
                    case 2: {
                        Actions.displayActors(); break;
                    }
                    case 3: {
                        Actions.viewNotifications(loggedAdmin); break;
                    }
                    case 4: {
                        Actions.search(); break;
                    }
                    case 5: {
                        Actions.modifyFavoritesList(loggedAdmin); break;
                    }
                    case 6: {
                        try {
                            Actions.modifyUsersList(loggedAdmin); break;
                        } catch (SelfAccountDeletedException e) {
                            option = 10;
                            System.out.println(e.getMessage());
                        }
                    }
                    case  7: {
                        Actions.modifyPagesList(loggedAdmin); break;
                    }
                    case 8: {
                        Actions.modifyPage(loggedAdmin); break;
                    }
                    case 9: {
                        Actions.solveRequests(loggedAdmin); break;
                    }
                }
            }
        }
        System.out.println(Actions.ANSI_LIGHT_GREEN + "\nLogging out..." + Actions.ANSI_RESET);

    }
    private void runContributorMode(Contributor loggedContributor) throws InvalidCommandException {
        int option = 0;
        while (option != 10) {
            String notifications;
            if (loggedContributor.getNotifications().isEmpty())
                notifications = Actions.ANSI_BOLD + "No new notifications" + Actions.ANSI_RESET;
            else
                notifications = Actions.ANSI_BOLD + loggedContributor.getNotifications().size() +" new notifications" + Actions.ANSI_RESET;
            String menuText = Actions.ANSI_LIGHT_GREEN +
                    "--------------------------------------------------------------------------------------------------------------------------------------------" +
                    "\nNotifications : " + notifications +
                    "\nChoose action:\n" + Actions.ANSI_RESET +
                    "    1)  View production details                             6)  Push/Pull request\n" +
                    "    2)  View actors details                                 7)  Add/Delete actor/production from system\n" +
                    "    3)  View notifications                                  8)  Update actor/production details\n" +
                    "    4)  Search database                                     9)  Solve a request\n" +
                    "    5)  View/Modify favorites list                          10) Logout\n";
            System.out.print(menuText + "\nType action number: ");
            try {
                option = IMDB.in.nextInt();
                IMDB.in.nextLine();
            } catch (InputMismatchException e) {
                throw new InvalidCommandException("Please introduce a number!");
            }
            System.out.println();
            if (option > 10) {
                System.out.println(InvalidOptionException.message);
            } else {
                switch (option) {
                    case 1: {
                        Actions.displayProductions(); break;
                    }
                    case 2: {
                        Actions.displayActors(); break;
                    }
                    case 3: {
                        Actions.viewNotifications(loggedContributor); break;
                    }
                    case 4: {
                        Actions.search(); break;
                    }
                    case 5: {
                        Actions.modifyFavoritesList(loggedContributor); break;
                    }
                    case 6: {
                        Actions.modifyRequestsList(loggedContributor); break;
                    }
                    case  7: {
                        Actions.modifyPagesList(loggedContributor); break;
                    }
                    case 8: {
                        Actions.modifyPage(loggedContributor); break;
                    }
                    case 9: {
                        Actions.solveRequests(loggedContributor); break;
                    }
                }
            }
        }
        System.out.println(Actions.ANSI_LIGHT_GREEN + "\nLogging out..." + Actions.ANSI_RESET);
    }
    private void runRegularMode(Regular loggedRegular) throws InvalidCommandException {
        int option = 0;
        while (option != 8) {
            String notifications;
            if (loggedRegular.getNotifications().isEmpty())
                notifications = Actions.ANSI_BOLD + "No new notifications" + Actions.ANSI_RESET;
            else
                notifications = Actions.ANSI_BOLD + loggedRegular.getNotifications().size() +" new notifications" + Actions.ANSI_RESET;
            String menuText = Actions.ANSI_LIGHT_GREEN +
                    "--------------------------------------------------------------------------------------------------------------------------------------------" +
                    "\nNotifications : " + notifications +
                    "\nChoose action:\n" + Actions.ANSI_RESET +
                    "    1)  View production details                             6)  Push/Pull request\n" +
                    "    2)  View actors details                                 7)  Add/Delete rating\n" +
                    "    3)  View notifications                                  8)  Logout\n" +
                    "    4)  Search database\n" +
                    "    5)  Add/Delete actor/production to/from favorites\n";
            System.out.print(menuText + "\nType action number: ");
            try {
                option = IMDB.in.nextInt();
                IMDB.in.nextLine();
            } catch (InputMismatchException e) {
                throw new InvalidCommandException("Please introduce a number!");
            }
            System.out.println();
            if (option > 8) {
                System.out.println(InvalidOptionException.message);
            } else {
                switch (option) {
                    case 1: {
                        Actions.displayProductions(); break;
                    }
                    case 2: {
                        Actions.displayActors(); break;
                    }
                    case 3: {
                        Actions.viewNotifications(loggedRegular); break;
                    }
                    case 4: {
                        Actions.search(); break;
                    }
                    case 5: {
                        Actions.modifyFavoritesList(loggedRegular); break;
                    }
                    case 6: {
                        Actions.modifyRequestsList(loggedRegular); break;
                    }
                    case  7: {
                        Actions.modifyRatingsList(loggedRegular); break;
                    }
                }
            }
        }
        System.out.println(Actions.ANSI_LIGHT_GREEN + "\nLogging out..." + Actions.ANSI_RESET);
    }
    public static IMDB getInstance() {
        if (instance == null)
            instance = new IMDB();
        return instance;
    }

    public List<Admin> getAdmins() {
        return this.admins;
    }

    public List<Regular> getRegulars() {
        return regulars;
    }

    public List<Contributor> getContributors() {
        return contributors;
    }
    public List<User> getUsers() {
        List<User> userList = new ArrayList<User>();
        userList.addAll(admins);
        userList.addAll(regulars);
        userList.addAll(contributors);

        return userList;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public String containsActorName(String actor) {
        for (Actor a : actors)
            if (a.containsDataAbout(actor))
                return a.getName();
        return null;
    }
    public Actor getActor(String actor) {
        for (Actor a : actors)
            if (a.containsDataAbout(actor))
                return a;
        return null;
    }
    public User getUser(String username) {
        for (User u : getUsers()) {
            if (u.getUserName().equalsIgnoreCase(username))
                return u;
        }
        return null;
    }
    public Production getProduction(String prod) {
        for (Production p : productions) {
            if (p.getTitle().equalsIgnoreCase(prod))
                return productions.get(productions.indexOf(p));
        }
        return null;
    }
    public String containsProductionTitle(String prod) {
        for (Production p : productions) {
            if (p.containsTitle(prod))
                return p.getTitle();
        }
        return null;
    }
    public User containsUser(String username) {
        for (User u : getUsers())
            if (u.getUserName().equalsIgnoreCase(username))
                return u;
        return null;
    }

    /*
    * TODO the following method will require updates when notifications will be implemented!!
    * */
    public void addActor(String name) {
        actors.add(new Actor().setName(name));
    }
    public void addActor(Actor actor) {
        actors.add(actor);
    }
    public void addProduction(String name, String type) {
        productions.add(ProductionFactory.buildProduction(type).setTitle(name));
    }
    public void addProduction(Production prod) {
        productions.add(prod);
    }
    // TODO: do the rest =)
}
