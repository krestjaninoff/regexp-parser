package name.krestjaninoff.regexp.nfa;

import org.apache.commons.collections.ListUtils;

import java.util.Arrays;
import java.util.Stack;

/**
 * A compiler of NonDeterministic Finite State Machine
 */
public class NfaCompiler {

    public NfaState compile(String postfix) {
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
        top.bindTo(new NfaState(null, NfaState.Type.MATCH, null, null));

        return top.getStart();
    }

    private void buildCharacter(Stack<Fragment> stack, char value) {

        NfaState state = new NfaState(value, NfaState.Type.CHAR, null, null);
        Fragment fragment = new Fragment(state, Arrays.asList(state.getOut()));

        stack.push(fragment);
    }

    private void buildAlternation(Stack<Fragment> stack) {

        Fragment fragment2 = stack.pop();
        Fragment fragment1 = stack.pop();

        NfaState state = new NfaState(null, NfaState.Type.SPLIT, fragment1.getStart(), fragment2.getStart());
        Fragment fragment = new Fragment(state, ListUtils.union(fragment1.getOut(), fragment2.getOut()));

        stack.push(fragment);
    }

    private void buildZeroOrMore(Stack<Fragment> stack) {

        Fragment fragment1 = stack.pop();

        NfaState state = new NfaState(null, NfaState.Type.SPLIT, fragment1.getStart(), null);
        fragment1.bindTo(state);

        Fragment fragment = new Fragment(state, Arrays.asList(state.getOutAlt()));

        stack.push(fragment);
    }

    private void buildOneOrMore(Stack<Fragment> stack) {

        Fragment fragment1 = stack.pop();

        NfaState state = new NfaState(null, NfaState.Type.SPLIT, fragment1.getStart(), null);
        fragment1.bindTo(state);

        Fragment fragment = new Fragment(fragment1.getStart(), Arrays.asList(state.getOutAlt()));

        stack.push(fragment);
    }

    private void buildZeroOrOne(Stack<Fragment> stack) {

        Fragment fragment1 = stack.pop();

        NfaState state = new NfaState(null, NfaState.Type.SPLIT, fragment1.getStart(), null);
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
