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

### Dependencies
This program uses JSON.simple toolkit to save database elements in JSON files. To compile code in IntelliJ add JSON.simple dependency as it follows:

    File > Project Structure... > Modules > Dependencies > + > JARs or Directories > json-simple-1.1.1.jar (located in project root)

### How it works
- Tester.java is the class that contains `main()` method. Run it to launch program.
- IMDB is the main class of the program. It is a singleton class that contains arrays that store `Production`, `User`, `Actor` objects. It also has 'run()' method that runs the program.