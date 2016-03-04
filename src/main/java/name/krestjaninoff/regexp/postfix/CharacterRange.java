package name.krestjaninoff.regexp.postfix;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.collections.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A helper to support Character Ranges
 */
class CharacterRange {

    private static final List<Integer> ALPHABET = IntStream.rangeClosed('a', 'z').boxed().
            collect(Collectors.toList());


    @AllArgsConstructor
    public class Result {

        @Getter
        private String regexp;
        @Getter
        private int endPosition;
    }

    /**
     * Replaces a Character Range with an Alternation
     *
     * <p>
     *     It's better to introduce a separate NfaState for case, but since we're limited in time,
     *     that is the simplest solution.
     *
     *     Exampeles:
     *     [a-c] -> (a|b|c)
     *     [^a-x] -> (y|z)
     * </p>
     */
    public Result expand(String source, int start) {
        int pointer = start + 1;

        // Check if negative
        boolean isNegative = '^' == source.charAt(pointer);
        if (isNegative) {
            pointer++;
        }

        // Gather all the symbols
        List<Integer> symbols = new ArrayList<>();

        int i = pointer;
        for (; source.charAt(i) != ']'; i++) {
            char value = source.charAt(i);

            if (value != '-') {
                symbols.add((int) value);

            } else {
                value = source.charAt(++i);

                for (int j = symbols.get(symbols.size() - 1) + 1; j <= value; j++) {
                    symbols.add(j);
                }
            }
        }

        // Invert symbols, if necessary
        if (isNegative) {
            symbols = ListUtils.removeAll(ALPHABET, symbols);
        }

        // Build the regular expression for our range
        StringBuffer alternation = new StringBuffer();

        if (symbols.size() == 1) {
            alternation.append((char) symbols.iterator().next().intValue());

        } else if (symbols.size() > 1) {

            alternation.append('(');
            for (Integer symbol : symbols) {

                alternation.append((char) symbol.intValue());
                alternation.append('|');
            }

            alternation.deleteCharAt(alternation.length() - 1).append(')');
        }

        // Rebuild the original expression
        String regexp = source.substring(0, start) + alternation.toString() + source.substring(i + 1);

        return new Result(regexp, i);
    }
}
