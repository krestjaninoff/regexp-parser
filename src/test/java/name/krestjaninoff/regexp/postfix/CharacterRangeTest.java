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
public class CharacterRangeTest {
    private String source;
    private String result;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {

                { "[a]", "a" },
                { "[abc]", "(a|b|c)" },
                { "[a-c]", "(a|b|c)" },
                { "[ac-d]", "(a|c|d)" },
                { "[^a-w]", "(x|y|z)" },
                { "[^za-x]", "y" },
        };

        return Arrays.asList(data);
    }

    public CharacterRangeTest(String source, String result) {
        this.source = source;
        this.result = result;
    }

    @Test
    public void test() {
        String currResult = new CharacterRange().expand(source, 0).getRegexp();
        assertEquals(source + " to " + currResult, result, currResult);
    }
}
