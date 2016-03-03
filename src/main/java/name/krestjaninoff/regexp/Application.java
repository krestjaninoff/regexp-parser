package name.krestjaninoff.regexp;

import name.krestjaninoff.regexp.nfa.State;

/**
 * The entry point
 */
public class Application {

    public static void main(String[] args) {

        // Get the input data
        if (args.length != 2) {
            System.out.println("Invalid arguments. Use \"app.name regexp_patter string_to_test\"");
        }

        // Compile the pattern
        String pattern = args[0];
        try {
            String postfix = new PostfixConverter().convertInfix(pattern);
            State nfa = new NfaCompiler().compile(postfix);

            String candidate = args[0];
            boolean result = new NfaMatcher().match(nfa, candidate);

            System.out.print(result ? "TRUE" : "FALSE");

        } catch (Exception e) {
            System.err.println("Failed to test the regexp " + pattern);
            e.printStackTrace();
        }
    }
}
