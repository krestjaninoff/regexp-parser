package name.krestjaninoff.regexp.nfa;

import java.util.List;

/**
 * A fragment of the state
 */
class Fragment {

    // The start state for the fragment
    private NfaState start;

    // A list of reachable States
    private List<NfaState.Holder> out;


    public Fragment(NfaState start, List<NfaState.Holder> out) {
        this.start = start;
        this.out = out;
    }


    public NfaState getStart() {
        return start;
    }

    public List<NfaState.Holder> getOut() {
        return out;
    }


    public void bindTo(NfaState state) {
        out.forEach(x -> x.set(state));
    }
}
