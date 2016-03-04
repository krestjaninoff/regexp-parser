package name.krestjaninoff.regexp;

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

                { "ab", "ab." },
                { "ab*|c", "ab*.c|"},
                { "a(bb)+a", "abb.+.a."}
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
        assertEquals(result, currResult);
    }
}
