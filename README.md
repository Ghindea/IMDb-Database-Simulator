<div align="center">

    ▀████▀████▄     ▄███▀███▀▀▀██▄ ▀███▀▀▀██▄   
      ██   ████    ████   ██    ▀██▄ ██    ██    
      ██   █ ██   ▄█ ██   ██     ▀██ ██    ██    
      ██   █  ██  █▀ ██   ██      ██ ██▀▀▀█▄▄    
      ██   █  ██▄█▀  ██   ██     ▄██ ██    ▀█    
      ██   █  ▀██▀   ██   ██    ▄██▀ ██    ▄█    
    ▄████▄███▄ ▀▀  ▄████▄████████▀ ▄████████    

</div>

# **IMDB database simulator**
by: [Daniel Ghindea](https://github.com/Ghindea)
### Dependencies
This program uses JSON.simple toolkit to save database elements in JSON files. To compile code in IntelliJ add JSON.simple dependency as it follows:

    File > Project Structure... > Modules > Dependencies > + > JARs or Directories > json-simple-1.1.1.jar (located in project root)

### Description
This Java program simulates simple functionalities of IMDb in a CLI environment, using OOP concepts, JSON files and Design Pattern principles. 

### Upcoming update
GUI environment created with SWING package is on its way.

### How it works
- Tester.java is the class that contains `main()` method. Run it to launch program.
- `IMDB` is the main class of the program. It is a singleton class that contains arrays that store `Production`, `User`, `Actor` objects. It also has 'run()' method that runs the program.
- `Login`, `Parser` and `Actions` classes implement methods that perform the functionalities of the program
- `UserFactory` and `ProductionFactory` are classes that define how different user and production types are created by following factory pattern principle.
- `Observable` is a class used to implement observer pattern for different objects that suffers state changes : `Request`, `Actor`, `Production`

### Program flow
1. **Parsing files to memory**

   - In order to keep data consistency from one run of the program to another, the database elements are saved in JSON files.
   - At the beggining of every run `Parser.parseDatabaseToMemory()` method loads saved data into memory, every object type being added to its corresponding array list located in `IMDB`
2. **Solving discrepancies**

   - Since there are chances to have actors with performances or performances with actors that don't have a corresponding IMDb page, system automatically creates empty pages for them and sends edit requests to `Staff` members for those pages.
   - To implement notifications functionality every `Observable` object requires a list of observers that is constructed accordingly to their relation with the subject.
   
3. **Logging in**

   - To know which functionalities are available for a user it has to be logged in the platform. After the credentials are given, `Login.start()` method searches the user base and returns the `User` instance that matches the credentials.

4. **Running the aplication**

   - Depending on the account type of the logged user different commands are available (see below table).
   - A menu with the available commands is displayed and an index is requested to select a command.
   - Most of these functionalities are based on a loop system, meaning that the user will be able to make these actions until 'exit' or 'done' commands are given.

5. **Running actions**

   - `Actions` class contains static methods that implement all the required functionalities and static String members used for CLI display.

6. **Logging out**

   - When 'log out' command is given the main loop is breaked and the user has the option to log in again or to close the aplication.

7. **Parsing memory to files**

   - To save changes, before the program is finished, all elements of the database are parsed back into the original JSONs with `Parser.parseDatabaseToJSONs()` method.

---

| Option                      | Regular | Contributor | Admin |
|-----------------------------|---------|----------|-----|
| View all productions        | X       | X        | X   |
| View all actors             | X       | X        | X   |
| View all notifications      | X       | X        | X   |
| Find in database            | X       | X        | X   |
| Add page to favorites       | X       | X        | X   |
| Push/Pull a request         | X       | X        |     |
| Create/Delete a page        |         |          | X   |
| View requests               |         | X        | X   |
| Add a rating for production | X       |          |     |
| Add/Delete user             |         |          | X   |
| Log out                     | X       | X        | X   |

---
### Bibliography
- [JSON parsing](https://www.geeksforgeeks.org/parse-json-java/)
- [DP concepts](https://www.geeksforgeeks.org/java-design-patterns/?ref=lbp)
