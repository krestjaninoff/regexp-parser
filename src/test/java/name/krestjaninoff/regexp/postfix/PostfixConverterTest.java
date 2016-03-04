package name.krestjaninoff.regexp.postfix;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link PostfixConverter}
 */
@RunWith(value = Parameterized.class)
public class PostfixConverterTest {

    private String source;
    private String result;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {

                // Empty case
                { "", "" },

                // One element string
                { "a", "a" },

                // Concatenation
                { "ab", "ab." },
                { "abc", "ab.c." },

                // Qualifiers
                { "a?b", "a?b." },
                { "a+b", "a+b." },
                { "a*b", "a*b." },

                // Alternation
                { "a|b", "ab|"},
                { "ab|cd", "ab.cd.|"},
                { "ab*|c", "ab*.c|"},
                { "ab*|c?d+", "ab*.c?d+.|"},

                // Grouping
                { "a(bb)+a", "abb.+.a."},
                { "(aa)?", "aa.?"},

                // Compound cases
                { "a(bc)?c*|cd", "abc.?.c*.cd.|"}
        };

        return Arrays.asList(data);
    }

    public PostfixConverterTest(String source, String result) {
        this.source = source;
        this.result = result;
    }

    @Test
    public void test() {
        String currResult = new PostfixConverter().convertInfix(source);
        assertEquals(source + " to " + currResult, result, currResult);
    }
}
