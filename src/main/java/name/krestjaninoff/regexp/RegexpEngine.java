package name.krestjaninoff.regexp;

import name.krestjaninoff.regexp.nfa.NfaCompiler;
import name.krestjaninoff.regexp.nfa.NfaMatcher;
import name.krestjaninoff.regexp.nfa.NfaState;
import name.krestjaninoff.regexp.postfix.PostfixConverter;

/**
 * A simple regexp engine
 */
public class RegexpEngine {

    /**
     * Test if the candidate matches with the pattern
     *
     * @param pattern a regular expression
     * @param candidate a string to test against the regular expression
     * @return if the candidate matches with the string or not
     * @throws RuntimeException
     */
    public boolean match(String pattern, String candidate) {

        boolean result;
        try {

            String postfix = new PostfixConverter().convertInfix(pattern);
            NfaState nfa = new NfaCompiler().compile(postfix);

            result = new NfaMatcher().match(nfa, candidate);

        } catch (Exception e) {
            throw new RuntimeException("Failed to test the regexp '" + pattern +
                    "' against candidate '" + candidate + "'", e);
        }

        return result;
    }
}
