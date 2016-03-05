package name.krestjaninoff.regexp.nfa;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * A fragment of NFA
 */
@AllArgsConstructor
class Fragment {

    // The start State for the fragment
    @Getter
    private NfaState start;

    // A list of reachable States
    @Getter
    private List<NfaState.Holder> out;

    public void bindTo(NfaState state) {
        out.forEach(x -> x.set(state));
    }
}
