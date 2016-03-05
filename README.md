A dummy regexp parser


### About the parser

This is the simplest regexp parser implementation that supports the following constructions:
 
 * a? - zero or on;
 * a+ - one or more
 * a* - zero or more
 * a|b - alternation
 * (a) - grouping
 * {n,m} - counted repetition
 * \[a-z\] - character range (including negative variant)
 

### Usage

To test your string against a regular expression compile the project

```
mvn clean package
```

and run the following command

```
java -jar target/regexp-parser-1.0-SNAPSHOT-jar-with-dependencies.jar "a*b+" "aaabb"
```

Alternatively, you can take a look at JUnit tests :)