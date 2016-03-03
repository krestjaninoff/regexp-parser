package name.krestjaninoff.regexp;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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

                // Process the braces
                if (c == '(') {
                    stack.push(c);

                } else if (c == ')') {

                    while (stack.peek() != '(') {
                        result.append(stack.pop());
                    }
                    stack.pop();

                // Process the operator
                } else {
                    while (!stack.isEmpty() && operations.get(c) <= operations.get(stack.peek())) {
                        result.append(stack.pop());
                    }
                    stack.push(c);
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

        for (int i = 0; i < source.length(); i++) {
            result.append(source.charAt(i));

            if (!operations.containsKey(source.charAt(i)) &&
                    (i + 1 < source.length()) && !operations.containsKey(source.charAt(i + 1))) {

                result.append('.');
                result.append(source.charAt(i + 1));

                i++;
            }
        }

        return result.toString();
    }
}
