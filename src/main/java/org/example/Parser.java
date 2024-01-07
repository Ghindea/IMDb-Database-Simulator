package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
/*
* I'm sorry to whoever will have to read the JSONs after they've been parsed
* by this program, cuz IDK how to pretty print them... I did my best.
* */
public class Parser {
    private enum parseType {
        accounts,
        actors,
        productions,
        requests
    }
    public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private String pathAccounts     = "src/main/resources/input/accounts.json";
    private String pathActors       = "src/main/resources/input/actors.json";
    private String pathProductions  = "src/main/resources/input/production.json";
    private String pathRequests     = "src/main/resources/input/requests.json";
    private String pathTest         = "src/main/resources/input/test.json";

    public void parseDatabaseToJSONs() {
        parseMemory(pathTest, parseType.productions);
    }
    private void parseMemory(String path, parseType type) {
        JSONArray jsonArray = new JSONArray();

        switch (type) {
            case actors -> {jsonArray = parseIMDBActorsList();}
            case accounts -> {jsonArray = parseIMDBAccountsList();}
            case productions -> {jsonArray = parseIMDBProductionsList();}
            case requests -> {jsonArray = parseIMDBRequestsList();}
        }
        // Write the array to a JSON file
        try (FileWriter fileWriter = new FileWriter(path)) {
            JSONValue.writeJSONString(jsonArray, fileWriter);
        } catch (IOException e) {
            System.out.println("Unable to export " + type);
        }
    }
    private JSONArray parseIMDBActorsList() {
        JSONArray jsonArray = new JSONArray();

        for (Actor a : IMDB.getInstance().getActors()) {
            JSONObject jActor = new JSONObject();
            jActor.put("name", a.getName());

            JSONArray jPerformances = new JSONArray();
            for (Actor.Performance p : a.getPerformances()) {
                JSONObject jPerf = new JSONObject();
                jPerf.put("title", p.getTitle());
                jPerf.put("type", p.getType());

                jPerformances.add(jPerf);
            }
            jActor.put("performances", jPerformances);
            jActor.put("biography", a.getBiography());
            jsonArray.add(jActor);
        }

        return jsonArray;
    }
    private JSONArray parseIMDBAccountsList() {
        JSONArray jsonArray = new JSONArray();
        for (User a : IMDB.getInstance().getUsers()) {
            JSONObject jAccount = new JSONObject();
            jAccount.put("username", a.getUserName());
            if (a instanceof Admin)
                jAccount.put("experience", null);
            else
                jAccount.put("experience", Integer.toString(a.getUserXP()));

            jAccount.put("userType", a.getUserType().toString());

            /* de ce oare am pus information privat class!?*/
            JSONObject jInfo = new JSONObject();
            ArrayList<String> info = a.getUserInfo();
            JSONObject jCredentials = new JSONObject();
            jCredentials.put("password", info.get(1));
            jCredentials.put("email", info.get(0));
            jInfo.put("credentials", jCredentials);
            jInfo.put("name", info.get(2));
            jInfo.put("country", info.get(3));
            jInfo.put("age", Integer.valueOf(info.get(4)));
            jInfo.put("gender", info.get(5));
            jInfo.put("birthDate", info.get(6));

            jAccount.put("information", jInfo);

            SortedSet<String> fav = a.getFavorites();
            ArrayList<String> favAct = new ArrayList<>(), favProd = new ArrayList<>();
            for (String s : fav) {
                if (IMDB.getInstance().containsActorName(s) != null)
                    favAct.add(s);
                if (IMDB.getInstance().containsProductionTitle(s) != null)
                    favProd.add(s);
            }
            if (!favAct.isEmpty()) {
                JSONArray jFavAct = new JSONArray();
                jFavAct.addAll(favAct);
                jAccount.put("favoriteActors", jFavAct);
            }
            if (!favProd.isEmpty()) {
                JSONArray jFavProd = new JSONArray();
                jFavProd.addAll(favProd);
                jAccount.put("favoriteProductions", jFavProd);
            }

            if (!a.getNotifications().isEmpty()) {
                JSONArray jNotif = new JSONArray();
                jNotif.addAll(a.getNotifications());
                jAccount.put("notifications", jNotif);
            }

            if (a instanceof Staff) {
                SortedSet<String> cont = ((Staff) a).getContributions();
                favAct.clear(); favProd.clear();
                for (String s : cont) {
                    if (IMDB.getInstance().containsActorName(s) != null)
                        favAct.add(s);
                    if (IMDB.getInstance().containsProductionTitle(s) != null)
                        favProd.add(s);
                }
                if (!favAct.isEmpty()) {
                    JSONArray jFavAct = new JSONArray();
                    jFavAct.addAll(favAct);
                    jAccount.put("actorsContribution", jFavAct);
                }
                if (!favProd.isEmpty()) {
                    JSONArray jFavProd = new JSONArray();
                    jFavProd.addAll(favProd);
                    jAccount.put("productionContribution", jFavProd);
                }
            }
            jsonArray.add(jAccount);
        }
        return jsonArray;
    }
    private JSONArray parseIMDBRequestsList() {
        JSONArray jsonArray = new JSONArray();
        for (Request r : IMDB.getInstance().getRequests()) {
            JSONObject jRequest = new JSONObject();
            jRequest.put("type", r.getType().toString());
            jRequest.put("createdDate", r.getRequestDate().toString());
            jRequest.put("username", r.getFromUserName());
            jRequest.put("to", r.getToUser());
            jRequest.put("description", r.getDescription());

            if (r.getTitle() != null) {
                if (IMDB.getInstance().containsActorName(r.getTitle()) != null)
                    jRequest.put("actorName", r.getTitle());
                if (IMDB.getInstance().containsProductionTitle(r.getTitle()) != null)
                    jRequest.put("movieTitle", r.getTitle());
            }
            /* maybe i should've saved this field in 2 parameters... */

            jsonArray.add(jRequest);
        }
        return jsonArray;
    }
    private JSONArray parseIMDBProductionsList() {
        JSONArray jsonArray = new JSONArray();
        for (Production p : IMDB.getInstance().getProductions()) {
            JSONObject jProduction = new JSONObject();
            jProduction.put("title", p.getTitle());
            jProduction.put("type", p.getType());
            jProduction.put("plot", p.getDescription());
            jProduction.put("averageRating", p.getGrade());
            jProduction.put("releaseYear", p.getReleaseYear());

            JSONArray jDirectors = new JSONArray();
            jDirectors.addAll(p.getDirectorsNames());
            jProduction.put("directors", jDirectors);

            JSONArray jActors = new JSONArray();
            jActors.addAll(p.getActorsNames());
            jProduction.put("actors", jActors);

            JSONArray jGenres = new JSONArray();
            for (Genre g : p.getGenres())
                jGenres.add(g.toString());
            jProduction.put("genres", jGenres);

            JSONArray jRatings = new JSONArray();
            for (Rating r : p.getRatings()) {
                JSONObject jRat = new JSONObject();
                jRat.put("username", r.getUserName());
                jRat.put("rating", r.getRating());
                jRat.put("comment", r.getComment());

                jRatings.add(jRat);
            }
            jProduction.put("ratings", jRatings);

            if (p instanceof Movie)
                jProduction.put("duration", ((Movie) p).getDuration());

            if (p instanceof Series) {
                jProduction.put("numSeasons", ((Series) p).getSeries().size());
                JSONObject jSeasonsList = new JSONObject();
                for (int i = 1; i <= ((Series) p).getSeries().size(); i++) {
                    JSONArray jSeason = new JSONArray();
                    String key = "Season " + i;
                    for (Episode e : ((Series) p).getSeries().get(key)) {
                        JSONObject jEpisode = new JSONObject();
                        jEpisode.put("episodeName", e.getTitle());
                        jEpisode.put("duration", e.getDuration());

                        jSeason.add(jEpisode);
                    }
                    jSeasonsList.put(key, jSeason);
                }
                jProduction.put("seasons", jSeasonsList);
            }
            jsonArray.add(jProduction);
        }
        return jsonArray;
    }
    public void parseDatabaseToMemory() {
        parseJSON(pathActors, parseType.actors);
        parseJSON(pathAccounts, parseType.accounts);
        parseJSON(pathRequests, parseType.requests);
        parseJSON(pathProductions, parseType.productions);

        /*
        FIXME when notifications will be implemented i need to add remaining actors and productions from performances and casts lists
        */
    }
    private void parseJSON(String path, parseType type) {
        try (FileReader in = new FileReader(path)) {
            JSONParser jp = new JSONParser();
            JSONArray ja = (JSONArray) jp.parse(in);
            switch (type) {
                case actors -> {
                    for (Object o : ja) { parseActor((JSONObject) o);}
                }
                case accounts -> {
                    for (Object o : ja) { parseAccount((JSONObject) o);}
                }
                case requests -> {
                    for (Object o : ja) { parseRequest((JSONObject) o);}
                }
                case productions -> {
                    for (Object o : ja) { parseProduction((JSONObject) o);}
                }
            }
        }
        catch (IOException | ParseException e) {
            System.out.println("Unable to import " + type);
        }
    }
    private void parseProduction(JSONObject jProd) {
        Iterator<String> iterator;
        ArrayList<String> list;
        Production prod  = ProductionFactory.buildProduction((String) jProd.get("type"));

        prod.setTitle((String) jProd.get("title"))                  // title
                .setGrade((double) jProd.get("averageRating"))      // rating
                .setDescription((String) jProd.get("plot"));        // desc

        JSONArray jDirectors = (JSONArray) jProd.get("directors");  // directors list
        list = new ArrayList<String>();
        iterator = jDirectors.iterator();
        while (iterator.hasNext()) list.add(iterator.next());
        prod.setDirectorsNames(list);

        JSONArray jActors   = (JSONArray) jProd.get("actors");      // actors list
        list = new ArrayList<String>();
        iterator = jActors.iterator();
        while (iterator.hasNext()) list.add(iterator.next());
        prod.setActorsNames(list);

        JSONArray jGenres = (JSONArray) jProd.get("genres");        // genres list
        ArrayList<Genre> genresList = new ArrayList<Genre>();
        Iterator<String> i = jGenres.iterator();
        while (i.hasNext()) genresList.add(Genre.valueOf(i.next()));
        prod.setGenres(genresList);

        JSONArray jRating = (JSONArray) jProd.get("ratings");       // ratings list
        ArrayList<Rating> ratingsList = new ArrayList<Rating>();
        for (Object r : jRating) {
            Rating rating = new Rating();
            rating.setUserName((String) ((JSONObject) r).get("username"))
                    .setComment((String) ((JSONObject) r).get("comment"))
                    .setRating(((Long) ((JSONObject) r).get("rating")).intValue());
            ratingsList.add(rating);
        }
        prod.setRatings(ratingsList);

        if (jProd.get("releaseYear") != null)
            prod.setReleaseYear(((Long) jProd.get("releaseYear")).intValue());    // release year

        if (prod instanceof Movie) {
            ((Movie) prod).setDuration((String) jProd.get("duration"));  // duration
        } else {
            /*
            IDK what I have written here, it's just unholy, it may, or it may not work
            */
            ((Series) prod).setNumberOfSeasons(((Long) jProd.get("numSeasons")).intValue());
            HashMap<String, List<Episode>> series = new HashMap<>();

            JSONObject jSeasonsList = (JSONObject) jProd.get("seasons");  // object that contains a list of "season" objects


            for (int seasonNo = 1; seasonNo <= ((Series) prod).getNumberOfSeasons(); seasonNo++) {     // for each season
                String seasonName = "Season " + seasonNo;
                JSONArray jEpisodes = (JSONArray) jSeasonsList.get(seasonName); // array of episode objects contained in their season
                ArrayList<Episode> episodesList = new ArrayList<Episode>();
                for (Object e: jEpisodes) {     // for each episode add that episode to the list
                    episodesList.add(new Episode((String) ((JSONObject) e).get("episodeName"),
                            (String) ((JSONObject) e).get("duration")));
                }
                series.put(seasonName, episodesList); // add the season and its list of episodes in hashmap
            }
            ((Series) prod).setSeries(series);  // set production hashmap
        }

        IMDB.getInstance().getProductions().add(prod);
    }
    private void parseRequest(JSONObject jReq) {
        Request req = new Request();
        req.setType(RequestType.valueOf((String) jReq.get("type")))
                .setDescription((String) jReq.get("description"))
                .setFromUserName((String) jReq.get("username"))
                .setToUser((String) jReq.get("to"))
                .setRequestDate(LocalDateTime.parse((String) jReq.get("createdDate"), dateTimeFormat));

        if (req.getType().equals(RequestType.ACTOR_ISSUE))
            req.setTitle((String) jReq.get("actorName"));
        if (req.getType().equals(RequestType.MOVIE_ISSUE))
            req.setTitle((String) jReq.get("movieTitle"));

        IMDB.getInstance().getRequests().add(req);
    }
    private void parseAccount(JSONObject jUser) {
        User user = UserFactory.buildUser(AccountType.valueOf((String) jUser.get("userType")));
        user.setUserName((String) jUser.get("username"))            // username
                .setUserType(AccountType.valueOf((String) jUser.get("userType")));  // type

        if ((String) jUser.get("experience") != null)
            user.setUserXP(Integer.parseInt((String) jUser.get("experience")));     // xp
        else if (user.getUserType() == AccountType.Admin)
            user.setUserXP(Integer.MAX_VALUE);
        else
            user.setUserXP(0);

        JSONObject jInfo = (JSONObject) jUser.get("information");
        JSONObject jCred = (JSONObject) jInfo.get("credentials");
        Credentials credentials = new Credentials((String) jCred.get("email"), (String) jCred.get("password")); // credentials
        user.setUserInfo(credentials,
                (String) jInfo.get("name"),                 // name
                (String) jInfo.get("country"),              // country
                (String) jInfo.get("gender"),               // gender
                ((Long) jInfo.get("age")).intValue(),       // age
                LocalDate.parse((String) jInfo.get("birthDate"), dateFormat).atStartOfDay());    // date

        SortedSet<String> set = new TreeSet<String>();
        JSONArray jFavProd   = (JSONArray) jUser.get("favoriteProductions");
        JSONArray jFavActor  = (JSONArray) jUser.get("favoriteActors");

        Iterator<String> iterator;
        if (jFavProd != null) {
            iterator = jFavProd.iterator();
            while (iterator.hasNext())
                set.add(iterator.next());
        }
        if (jFavActor != null) {
            iterator = jFavActor.iterator();
            while (iterator.hasNext())
                set.add(iterator.next());
        }
        user.setFavorites(set);                             // favorites

        if (user.getUserType().equals(AccountType.Admin) ||
                user.getUserType().equals(AccountType.Contributor)) {

            JSONArray jProdCont = (JSONArray) jUser.get("productionsContribution");
            JSONArray jActCont  = (JSONArray) jUser.get("actorsContribution");
            set = new TreeSet<>();

            if (jProdCont != null) {
                iterator = jProdCont.iterator();
                while (iterator.hasNext()) set.add(iterator.next());
            }
            if (jActCont != null) {
                iterator = jActCont.iterator();
                while (iterator.hasNext()) set.add(iterator.next());
            }

            ((Staff) user).setContributions(set);
        }

        JSONArray jNotifications = (JSONArray) jUser.get("notifications");
        if (jNotifications != null) {
            ArrayList<String> notifications = new ArrayList<>();
            iterator = jNotifications.iterator();
            while (iterator.hasNext()) {
                notifications.add(iterator.next());
            }
            user.setNotifications(notifications);
        }
        if (user instanceof Admin)
            IMDB.getInstance().getAdmins().add((Admin) user);
        else if (user instanceof Contributor)
            IMDB.getInstance().getContributors().add((Contributor) user);
        else if (user instanceof Regular)
            IMDB.getInstance().getRegulars().add((Regular) user);

        try (FileWriter out =  new FileWriter("src/main/java/org/database/used_user_names.txt", true)) {
            out.write((String) jUser.get("username") + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void parseActor(JSONObject jActor) {
        Actor actor = new Actor();
        actor.setName((String) jActor.get("name"));
        actor.setBiography((String) jActor.get("biography"));

        JSONArray jarrayPerforms = (JSONArray) jActor.get("performances");
        for (Object perfo : jarrayPerforms) {
            JSONObject jPerf = (JSONObject) perfo;
            actor.addPerformance((String) jPerf.get("title"),
                    (String) jPerf.get("type"));
        }
        IMDB.getInstance().getActors().add(actor);
    }
}
