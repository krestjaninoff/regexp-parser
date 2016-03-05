package name.krestjaninoff.regexp.postfix;

import org.apache.commons.collections.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A Character Ranges helper
 */
class CharacterRange {

    private static final List<Integer> ALPHABET = IntStream.rangeClosed('a', 'z').boxed().
            collect(Collectors.toList());

    /**
     * Replaces a Character Range with an Alternation
     *
     * <p>
     *     There are rumors that it's better to introduce a separate NfaState for this case, but for now things are
     *     how they are.
     * </p>
     *
     * Examples:
     * <ul>
     *     <li>[a-c] -> (a|b|c)</li>
     *     <li>[^a-x] -> (y|z)</li>
     * </ul>
     */
    public String expand(String source, int start) {
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

        // Build the regular expression for the range
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

        return regexp;
    }
}
