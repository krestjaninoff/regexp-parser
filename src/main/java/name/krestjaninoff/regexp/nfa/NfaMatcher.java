package name.krestjaninoff.regexp.nfa;

import java.util.ArrayList;
import java.util.List;

/**
 * A matcher for applying NFA to a string
 */
public class NfaMatcher {

    public boolean match(NfaState nfa, String candidate) {

        List<NfaState> currentStates = new ArrayList<>();
        List<NfaState> nextStates = new ArrayList<>();
        List<NfaState> tmp;

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

    private boolean isMatch(List<NfaState> currentStates) {
        return currentStates.stream().anyMatch(x -> x.getType() == NfaState.Type.MATCH);
    }

    private void addState(List<NfaState> list, NfaState state) {

        if (state == null || list.contains(state)) {
            return;
        }

        if (state.getType() == NfaState.Type.SPLIT) {
            addState(list, state.getOut().get());
            addState(list, state.getOutAlt().get());

            return;
        }

        list.add(state);
    }

    private void makeStep(char value, List<NfaState> currentStates, List<NfaState> nextStates) {

        currentStates.stream()
                .filter(s -> s.getValue() == value)
                .forEach(s -> addState(nextStates, s.getOut().get()));
    }
}
