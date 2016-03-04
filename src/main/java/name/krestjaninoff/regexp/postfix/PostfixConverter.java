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

        List<Character> operBefore = Arrays.asList('(');
        List<Character> operAfter = Arrays.asList(')', '?', '+', '*');

        for (int i = 0; i < source.length(); i++) {

            char current = source.charAt(i);
            result.append(current);

            Character next = i + 1 < source.length() ? source.charAt(i + 1) : null;
            if (next == null) {
                break;
            }

            boolean isOperandCase = !operations.containsKey(current) && (!operations.containsKey(next) || operBefore.contains(next));
            boolean isOperationCase = operAfter.contains(current) && !operations.containsKey(next);
            if (isOperandCase || isOperationCase) {

                result.append('.');
            }
        }

        return result.toString();
    }
}
