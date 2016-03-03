package name.krestjaninoff.regexp;

import name.krestjaninoff.regexp.nfa.Fragment;
import name.krestjaninoff.regexp.nfa.State;
import org.apache.commons.collections.ListUtils;

import java.util.Arrays;
import java.util.Stack;

/**
 * A compiler of NonDeterministic Finite State Machine
 */
public class NfaCompiler {

    public State compile(String postfix) {
        Stack<Fragment> stack = new Stack<>();

        for (int i = 0; i < postfix.length(); i++) {
            char value = postfix.charAt(i);

            switch (value) {

                // Concatenation
                case '.':
                    buildConcatenation(stack);
                    break;

                // Zero or One
                case '?':
                    buildZeroOrOne(stack);
                    break;

                // One or more
                case '+':
                    buildOneOrMore(stack);
                    break;

                // Zero or more
                case '*':
                    buildZeroOrMore(stack);
                    break;

                // Alternation
                case '|':
                    buildAlternation(stack);
                    break;

                // Character
                default:
                    buildCharacter(stack, value);
                    break;
            }
        }

        Fragment top = stack.pop();
        return top.getStart();
    }

    private void buildCharacter(Stack<Fragment> stack, char value) {

        State state = new State(value, State.Type.CHAR, null, null);
        Fragment fragment = new Fragment(state, Arrays.asList(state.getOut()));

        stack.push(fragment);
    }

    private void buildAlternation(Stack<Fragment> stack) {

        Fragment fragment2 = stack.pop();
        Fragment fragment1 = stack.pop();

        State state = new State(0, State.Type.SPLIT, fragment1.getStart(), fragment2.getStart());
        Fragment fragment = new Fragment(state, ListUtils.union(fragment1.getOut(), fragment2.getOut()));

        stack.push(fragment);
    }

    private void buildZeroOrMore(Stack<Fragment> stack) {

        Fragment fragment1 = stack.pop();

        State state = new State(0, State.Type.SPLIT, fragment1.getStart(), null);
        fragment1.bindTo(state);

        Fragment fragment = new Fragment(state, Arrays.asList(state.getOutAlt()));

        stack.push(fragment);
    }

    private void buildOneOrMore(Stack<Fragment> stack) {

        Fragment fragment1 = stack.pop();

        State state = new State(0, State.Type.SPLIT, fragment1.getStart(), null);
        fragment1.bindTo(state);

        Fragment fragment = new Fragment(fragment1.getStart(), Arrays.asList(state.getOutAlt()));

        stack.push(fragment);
    }

    private void buildZeroOrOne(Stack<Fragment> stack) {

        Fragment fragment1 = stack.pop();

        State state = new State(0, State.Type.SPLIT, fragment1.getStart(), null);
        Fragment fragment = new Fragment(state, ListUtils.union(fragment1.getOut(), Arrays.asList(state.getOutAlt())));

        stack.push(fragment);
    }

    private void buildConcatenation(Stack<Fragment> stack) {

        Fragment fragment2 = stack.pop();
        Fragment fragment1 = stack.pop();

        fragment1.bindTo(fragment2.getStart());

        Fragment fragment = new Fragment(fragment1.getStart(), fragment2.getOut());
        stack.push(fragment);
    }
}
