A dummy regexp parser


### About the parser

This is the simplest regexp parser implementation that supports the following constructions:
 
 * a? - zero or one
 * a+ - one or more
 * a* - zero or more
 * a|b - alternation
 * (a) - braces
 
Implementation is based on the following article: https://swtch.com/~rsc/regexp/regexp1.html


### Usage

To test your string against a regular expression compile the project

```
mvn clean build
```

and run the following command

```
java -jar build/regexp-parser.jar a*b+ aaabb
```