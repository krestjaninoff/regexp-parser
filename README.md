A dummy regexp parser


### About the parser

This is the simplest regexp parser implementation that supports the following constructions:
 
 * a?
 * a+
 * a*
 * a|b
 * (a)
 
Implementation is based on the following article: https://swtch.com/~rsc/regexp/regexp1.html
Plus, http://www.sluniverse.com/php/vb/script-library/58242-simple-regular-expression-parser.html


### Usage

To test your string against a regular expression compile the project

```
mvn clean build
```

and run the following command

```
java -jar build/regexp-parser.jar a*b+ aaabb
```