package name.krestjaninoff.regexp.postfix;

import java.util.*;

/**
 * A converter to Postfix record format
 *
 * <p>
 *     Based on Shunting-yard algorithm
 * </p>
 *
 * @link https://en.wikipedia.org/wiki/Reverse_Polish_notation
 * @link https://en.wikipedia.org/wiki/Shunting-yard_algorithm
 */
public class PostfixConverter {

    public static final Map<Character, Integer> OPERATIONS;
    static {
        Map<Character, Integer> operationsBuilder = new HashMap<>();
        operationsBuilder.put('?', 3);
        operationsBuilder.put('+', 3);
        operationsBuilder.put('*', 3);
        operationsBuilder.put('.', 2);
        operationsBuilder.put('|', 1);
        operationsBuilder.put('(', 0);
        operationsBuilder.put(')', 0);
        operationsBuilder.put('[', -1);
        operationsBuilder.put(']', -1);
        operationsBuilder.put('{', -1);
        operationsBuilder.put(',', -1);
        operationsBuilder.put('}', -1);

        OPERATIONS = Collections.unmodifiableMap(operationsBuilder);
    }

    public String convertInfix(String source) {

        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        // We have to track groups for extracting them in case of Counted Repetition
        int lastGroupStart = -1;
        int lastGroupEnd = -1;

        // Add concatenation operators to simplify the parsing process
        source = addConcatenation(source);

        // Parse the source
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);

            // Character is an operand
            if (!OPERATIONS.containsKey(c)) {
                result.append(c);

            // Character is an operation
            } else {

                switch (c) {

                    // Grouping
                    case '(':
                        lastGroupStart = i;

                        stack.push(c);
                        break;

                    case ')':

                        while (stack.peek() != '(') {
                            result.append(stack.pop());
                        }
                        stack.pop();

                        lastGroupEnd = i;
                        break;

                    // Character range
                    case '[':
                        CharacterRange.Result charRangeResult = new CharacterRange().expand(source, i);

                        source = charRangeResult.getRegexp();
                        i--;

                        break;

                    // Counted repetition
                    case '{':
                        String value = i - 1 != lastGroupEnd ?
                                String.valueOf(result.charAt(result.length() - 1)) :
                                source.substring(lastGroupStart + 1, lastGroupEnd);
                        result.delete(result.length() - value.length(), result.length());

                        // Insert the repetition code
                        CountedRepetition.Result countedRepeatResult = new CountedRepetition().expand(source, i, value);

                        source = countedRepeatResult.getSource();
                        i = countedRepeatResult.getNewPosition();

                        break;

                    // Process an operator
                    default:

                        while (!stack.isEmpty() && OPERATIONS.get(c) <= OPERATIONS.get(stack.peek())) {
                            result.append(stack.pop());
                        }
                        stack.push(c);

                        break;
                }
            }
        }

        // Add all the remaining operations to the results
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }

    private String addConcatenation(String source) {
        StringBuilder result = new StringBuilder();

        List<Character> operBefore = Arrays.asList('(', '[');
        List<Character> operAfter = Arrays.asList(')', '?', '+', '*', '}');

        for (int i = 0; i < source.length(); i++) {

            // Save the current element
            char current = source.charAt(i);
            result.append(current);

            // Process Character Ranges
            if (current == '[') {
                for (i = i + 1; source.charAt(i) != ']'; i++) {
                    result.append(source.charAt(i));
                }

                result.append(source.charAt(i));
                continue;
            }

            // Get the next element
            Character next = i + 1 < source.length() ? source.charAt(i + 1) : null;
            if (next == null) {
                break;
            }

            // Append the Concatenation
            boolean isOperandCase = !OPERATIONS.containsKey(current) && (!OPERATIONS.containsKey(next) || operBefore.contains(next));
            boolean isOperationCase = operAfter.contains(current) && !OPERATIONS.containsKey(next);
            if (isOperandCase || isOperationCase) {

                result.append('.');
            }
        }

        return result.toString();
    }
}
