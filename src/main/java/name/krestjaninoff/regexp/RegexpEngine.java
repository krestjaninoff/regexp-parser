package name.krestjaninoff.regexp;

import name.krestjaninoff.regexp.nfa.NfaCompiler;
import name.krestjaninoff.regexp.nfa.NfaMatcher;
import name.krestjaninoff.regexp.nfa.NfaState;
import name.krestjaninoff.regexp.postfix.PostfixConverter;

/**
 * A simple regexp engine
 */
public class RegexpEngine {

    private PostfixConverter postfixConverter = new PostfixConverter();

    private NfaCompiler nfaCompiler = new NfaCompiler();
    private NfaMatcher nfaMatcher = new NfaMatcher();

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

            String postfix = postfixConverter.convertInfix(pattern);
            NfaState nfa = nfaCompiler.compile(postfix);

            result = nfaMatcher.match(nfa, candidate);

        } catch (Exception e) {
            throw new RuntimeException("Failed to test the regexp '" + pattern +
                    "' against the candidate '" + candidate + "'", e);
        }

        return result;
    }
}
