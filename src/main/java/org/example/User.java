package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public abstract class User implements Observer, Comparable<Object>  {
    private static ArrayList<String> usedUserNames = loadUsers();
    private Information userInfo;
    private AccountType userType;
    private String userName;
    private int userXP;
    private List<String> notifications;
    private SortedSet<String> favorites;

    private static class Information {
        private Credentials credentials;
        private String name, country, gender;
        private int age;
        private LocalDateTime birthDate;
        public Information(Builder builder) {
            this.name           = builder.name;
            this.credentials    = builder.credentials;
            this.country        = builder.country;
            this.gender         = builder.gender;
            this.age            = builder.age;
            this.birthDate      = builder.birthDate;
        }
        public static class Builder {
            private Credentials credentials;
            private String name, country, gender;
            private int age;
            private LocalDateTime birthDate;
            private Builder() {}
            public static Builder newInstance() {
                return new Builder();
            }
            public Builder setName(String name) {
                this.name = name;
                return this;
            }
            public Builder setCredentials(Credentials credentials) {
                this.credentials = credentials;
                return this;
            }
            public Builder setCountry(String country) {
                this.country = country;
                return this;
            }
            public Builder setGender(String gender) {
                this.gender = gender;
                return this;
            }
            public Builder setAge(int age) {
                this.age = age;
                return this;
            }
            public Builder setBirthDate(LocalDateTime birthDate) {
                this.birthDate = birthDate;
                return this;
            }
            public Information build() {
                return new Information(this);
            }
        }
        /*
        Self note:

        When Builder class is used in User's constructor only the credentials & name fields are set
        To update user's info make a new Information instance like this:
            ... userInfo = Information.Builder.newInstance()
                                .setCredentials(new Credentials(userInfo.getName(), userInfo.getCredentials))
                                .setCountry("country")
                                .setGender("gender")
                                .setAge(20)
                                .setBirthDate(30.12.2023)   // or whatever format it works
                                .build()
        */
        public Credentials getCredentials() {
            return credentials;
        }
        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public String getGender() {
            return gender;
        }

        public int getAge() {
            return age;
        }

        public LocalDateTime getBirthDate() {
            return birthDate;
        }

        @Override
        public String toString() {
            return "email : " + credentials.getEmail() + "\n" +
                    ", password : " + credentials.getPassword() + "\n" +
                    ", name : '" + name + '\'' + "\n" +
                    ", country : '" + country + '\'' + "\n" +
                    ", gender : '" + gender + '\'' + "\n" +
                    ", age : " + age + "\n" +
                    ", birthDate : " + birthDate + "\n" +
                    '}';
        }

    }

    User(String name, String email, AccountType type) {
        this.userName   = generateUserName(name);
        this.userInfo   = Information.Builder.newInstance()
                            .setCredentials(new Credentials(email))
                            .setName(name)
                            .build();
        this.userXP     = 0;
        this.userType   = type;
        this.favorites  = new TreeSet<String>();
        this.notifications  = new ArrayList<String>();
    }
    User() {
        this.userXP     = 0;
        this.favorites  = new TreeSet<String>();
        this.notifications  = new ArrayList<String>();
    }
    private static ArrayList<String> loadUsers(){
        ArrayList<String> used = new ArrayList<String>();
        for (Admin a : IMDB.getInstance().getAdmins())
            used.add(a.getUserName());
        for (Contributor c : IMDB.getInstance().getContributors())
            used.add(c.getUserName());
        for (Regular r : IMDB.getInstance().getRegulars())
            used.add(r.getUserName());

        return used;
    }
    public static String generateUserName(String name) {
        String[] words = name.split("\\s+");
        String baseUserName = new String();
        for (int i = 0; i < words.length - 1; i++) {
            baseUserName += words[i].toLowerCase() + "_";
        }
        baseUserName += words[words.length-1].toLowerCase();
        String userName = baseUserName; int counter = 2;
        while (usedUserNames.contains(userName)) {
            userName = baseUserName + "_" + counter;
            counter++;
        }

        usedUserNames.add(userName);
        try {
            BufferedWriter out = new BufferedWriter(
                                 new FileWriter("src/main/java/org/database/used_user_names.txt", true));
            out.write(userName + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userName;
    }

    public AccountType getUserType() {
        return this.userType;
    }

    public User setUserInfo(Credentials credentials, String name, String country,
                            String gender, int age, LocalDateTime birthDate) {
        this.userInfo = Information.Builder.newInstance()
                .setCredentials(credentials)
                .setName(name)
                .setCountry(country)
                .setGender(gender)
                .setAge(age)
                .setBirthDate(birthDate)
                .build();
        return this;
    }

    public User setUserType(AccountType userType) {
        this.userType = userType;
        return this;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public User setUserXP(int userXP) {
        this.userXP = userXP;
        return this;
    }

    public User setNotifications(List<String> notifications) {
        this.notifications = notifications;
        return this;
    }

    public User setFavorites(SortedSet<String> favorites) {
        this.favorites = favorites;
        return this;
    }

    public Boolean credentialsMatch(Credentials credentials) {
        return this.userInfo.credentials.equals(credentials);
    }

    public String getUserName() {
        return userName;
    }

    public int getUserXP() {
        return userXP;
    }

    public ArrayList<String> getUserInfo() {
        ArrayList<String> info = new ArrayList<>();
        /*The order of atributes is as it follows:
        * 0. email
        * 1. password
        * 2. name 3. country 4. age 5. gender 6.birthDate*/
        info.add(userInfo.credentials.getEmail());
        info.add(userInfo.credentials.getPassword());
        info.add(userInfo.name);
        info.add(userInfo.country);
        info.add(Integer.toString(userInfo.age));
        info.add(userInfo.gender);
        info.add(userInfo.birthDate.format(Parser.dateFormat));

        return info;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public SortedSet<String> getFavorites() {
        return favorites;
    }

    public String getName() {
        return userInfo.getName();
    }
    @Override
    public String toString() {
        return "User{" +
                "userInfo=" + userInfo.toString() +
                ", userType=" + userType.toString() +
                ", userName='" + userName + '\'' +
                ", userXP=" + userXP +
                '}';
    }

    @Override
    public void update(String msg) {
        notifications.add(msg);
    }
}
