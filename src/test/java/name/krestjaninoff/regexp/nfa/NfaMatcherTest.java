package name.krestjaninoff.regexp.nfa;

import name.krestjaninoff.regexp.postfix.PostfixConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link NfaMatcher}
 */
@RunWith(value = Parameterized.class)
public class NfaMatcherTest {

    private String regexp;
    private String candidate;
    private boolean result;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {

                // Empty pattern
                { "", "", false },
                { "", "a", false },

                // Strict match
                { "a", "a", true },
                { "a", "b", false },

                // Concatenation
                { "ab", "ab", true },
                { "ab", "aa", false },

                // Zero or one
                { "a?", "", true },
                { "a?", "a", true },
                { "a?", "aa", false },
                { "a?b", "b", true },
                { "a?b", "ab", true },
                { "a?b", "aab", false },

                // One or more
                { "a+", "", false },
                { "a+", "a", true },
                { "a+", "aa", true },
                { "a+b", "b", false },
                { "a+b", "ab", true },
                { "a+b", "aab", true },

                // Zero or more
                { "a*", "", true },
                { "a*", "a", true },
                { "a*", "aa", true },
                { "a*b", "b", true },
                { "a*b", "ab", true },
                { "a*b", "aab", true },
                { "a*b", "bab", false },

                // Alternation
                { "a|b", "a", true},
                { "a|b", "b", true},
                { "a|b", "c", false},

                // Grouping
                { "a(bb)+a", "abb", false},
                { "a(bb)+a", "abba", true},
                { "a(bb)+a", "abbbba", true},
                { "a(bb)+a", "abbbbab", false},
                { "a(bb)+a", "abbba", false},
                { "a(bb)+a", "aba", false},

                // Compound cases
                { "a(bc)?c*|cd", "a", true},
                { "a(bc)?c*|cd", "abc", true},
                { "a(bc)?c*|cd", "abcc", true},
                { "a(bc)?c*|cd", "cd", true},
                { "a(bc)?c*|cd", "abcbc", false}
        };

        return Arrays.asList(data);
    }

    public NfaMatcherTest(String regexp, String candidate, boolean result) {
        this.regexp = regexp;
        this.candidate = candidate;
        this.result = result;
    }

    @Test
    public void test() {
        String postfix = new PostfixConverter().convertInfix(regexp);
        NfaState nfa = new NfaCompiler().compile(postfix);

        boolean currResult = new NfaMatcher().match(nfa, candidate);
        assertEquals(candidate + " against " + regexp, result, currResult);
    }
}
