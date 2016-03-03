package name.krestjaninoff.regexp;

import name.krestjaninoff.regexp.nfa.State;

import java.util.ArrayList;
import java.util.List;

/**
 * A matcher for applying NFA to a string
 */
public class NfaMatcher {

    public boolean match(State nfa, String candidate) {

        List<State> currentStates = new ArrayList<>();
        List<State> nextStates = new ArrayList<>();
        List<State> tmp;

        // Add initial state
        addState(currentStates, nfa);

        // Traverse through the NFA
        for (int i = 0; i < candidate.length(); i++) {

            makeStep(candidate.charAt(i), currentStates, nextStates);

            tmp = nextStates;
            nextStates = currentStates;
            currentStates = tmp;
        }

        boolean isMatch = isMatch(currentStates);
        return isMatch;
    }

    private boolean isMatch(List<State> currentStates) {
        return currentStates.stream().anyMatch(x -> x.getType() == State.Type.MATCH);
    }

    private void addState(List<State> list, State state) {

        if (state == null || list.contains(state)) {
            return;
        }

        if (state.getType() == State.Type.SPLIT) {
            addState(list, state.getOut().get());
            addState(list, state.getOutAlt().get());

            return;
        }

        list.add(state);
    }

    private void makeStep(char value, List<State> currentStates, List<State> nextStates) {

        currentStates.stream()
                .filter(s -> s.getValue() == value)
                .forEach(s -> addState(nextStates, s.getOut().get()));
    }
}
