package name.krestjaninoff.regexp.postfix;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link CharacterRange}
 */
@RunWith(value = Parameterized.class)
public class CountedRepetitionTest {
    private String source;
    private String result;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {

                // Basic cases
                { "a{3}", "a.a.a" },
                { "a{3,}", "a.a.a+" },
                { "a{,3}", "a?.a?.a?" },
                { "a{3,5}", "a.a.a.a?.a?" },

                // Two symbols integer
                { "a{10,}", "a.a.a.a.a.a.a.a.a.a+" }
        };

        return Arrays.asList(data);
    }

    public CountedRepetitionTest(String source, String result) {
        this.source = source;
        this.result = result;
    }

    @Test
    public void test() {
        String currResult = new CountedRepetition().expand(source, 1, "a").getSource();
        assertEquals(source + " to " + currResult, result, currResult);
    }
}

