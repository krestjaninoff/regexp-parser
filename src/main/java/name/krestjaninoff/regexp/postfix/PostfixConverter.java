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

    private static final Map<Character, Integer> operations;
    static {
        operations = new HashMap<>();
        operations.put('?', 3);
        operations.put('+', 3);
        operations.put('*', 3);
        operations.put('.', 2);
        operations.put('|', 1);
        operations.put('(', 0);
        operations.put(')', 0);
        operations.put('[', -1);
        operations.put(']', -1);
        operations.put('{', -1);
        operations.put('}', -1);
    }

    public String convertInfix(String source) {

        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        // Add concatenation operators to simplify the parsing process
        source = addConcatenation(source);

        // Parse the source
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);

            // Character is an operand
            if (!operations.containsKey(c)) {
                result.append(c);

            // Character is an operation
            } else {

                switch (c) {

                    // Grouping
                    case '(':
                        stack.push(c);
                        break;

                    case ')':

                        while (stack.peek() != '(') {
                            result.append(stack.pop());
                        }
                        stack.pop();

                        break;

                    // Character range
                    case '[':
                        CharacterRange.Result charRangeResult = new CharacterRange().expand(source, i);

                        source = charRangeResult.getRegexp();
                        i--;

                        break;

                    // Counted repetition
                    case '{':
                        CountedRepetition.Result countedRepeatResult = new CountedRepetition().expand(source, i,
                                result.charAt(result.length() - 1));

                        result.append(countedRepeatResult.getRegexp());
                        i = countedRepeatResult.getEndPosition() + 1;

                        break;

                    // Process an operator
                    default:

                        while (!stack.isEmpty() && operations.get(c) <= operations.get(stack.peek())) {
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
        List<Character> operAfter = Arrays.asList(')', '?', '+', '*');

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
            boolean isOperandCase = !operations.containsKey(current) && (!operations.containsKey(next) || operBefore.contains(next));
            boolean isOperationCase = operAfter.contains(current) && !operations.containsKey(next);
            if (isOperandCase || isOperationCase) {

                result.append('.');
            }
        }

        return result.toString();
    }
}
