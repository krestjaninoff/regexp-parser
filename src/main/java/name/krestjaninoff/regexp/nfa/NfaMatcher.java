package name.krestjaninoff.regexp.nfa;

import java.util.HashSet;
import java.util.Set;

/**
 * A matcher for applying NFA to a string
 *
 * @link https://en.wikipedia.org/wiki/Thompson%27s_construction
 */
public class NfaMatcher {

    public boolean match(NfaState nfaHead, String candidate) {

        // Add the initial state
        Set<NfaState> currentStates = new HashSet<>();
        addState(currentStates, nfaHead);

        // Traverse through the NFA
        for (int i = 0; i < candidate.length(); i++) {

            // Find the possible steps
            currentStates = getNextStates(candidate.charAt(i), currentStates);
        }

        boolean isMatch = isMatch(currentStates);
        return isMatch;
    }

    private void addState(Set<NfaState> states, NfaState state) {

        if (state == null || states.contains(state)) {
            return;
        }

        if (state.getType() == NfaState.Type.SPLIT) {
            addState(states, state.getOut().get());
            addState(states, state.getOutAlt().get());

            return;
        }

        states.add(state);
    }

    private Set<NfaState> getNextStates(Character value, Set<NfaState> currentStates) {
        Set<NfaState> nextStates = new HashSet<>();

        currentStates.stream()
                .filter(s -> value.equals(s.getValue()))
                .forEach(s -> addState(nextStates, s.getOut().get()));

        return nextStates;
    }

    private boolean isMatch(Set<NfaState> currentStates) {
        return currentStates.stream().anyMatch(x -> x.getType() == NfaState.Type.MATCH);
    }
}
