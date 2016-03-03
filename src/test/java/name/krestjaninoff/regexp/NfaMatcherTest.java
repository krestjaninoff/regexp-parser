package name.krestjaninoff.regexp;

import name.krestjaninoff.regexp.nfa.State;
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

                { "", "", false },
                { "", "a", false },

                { "a", "a", true },
                { "a", "b", false },

                { "ab", "ab", true },
                { "ab", "aa", false },

                { "a?", "", true },
                { "a?", "a", true },
                { "a?", "aa", false },
                { "a?b", "b", true },
                { "a?b", "ab", true },
                { "a?b", "aab", false },

                { "a+", "", false },
                { "a+", "a", true },
                { "a+", "aa", false },
                { "a+b", "b", false },
                { "a+b", "ab", true },
                { "a+b", "aab", true },

                { "a*", "", true },
                { "a*", "a", true },
                { "a*", "aa", true },
                { "a*b", "b", true },
                { "a*b", "ab", true },
                { "a*b", "aab", true },
                { "a*b", "bab", false },

                { "a|b", "a", true},
                { "a|b", "b", true},
                { "a|b", "c", false},

                { "a(bb)+a", "aa", true},
                { "a(bb)+a", "abb", true},
                { "a(bb)+a", "abba", true},
                { "a(bb)+a", "abbbba", true},
                { "a(bb)+a", "abbbbab", false},
                { "a(bb)+a", "abbba", false},
                { "a(bb)+a", "aba", false},

                { "a(bc)?c*|cd", "a", true},
                { "a(bc)?c*|cd", "abc", true},
                { "a(bc)?c*|cd", "abcc", true},
                { "a(bc)?c*|cd", "cd", true}
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
        State nfa = new NfaCompiler().compile(postfix);

        boolean currResult = new NfaMatcher().match(nfa, candidate);
        assertEquals(result, currResult);
    }
}
