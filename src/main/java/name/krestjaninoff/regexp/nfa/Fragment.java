package name.krestjaninoff.regexp.nfa;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A fragment of the state
 */
class Fragment {

    // The start state for the fragment
    private NfaState start;

    // A list of reachable States
    private List<AtomicReference<NfaState>> out;


    public Fragment(NfaState start, List<AtomicReference<NfaState>> out) {
        this.start = start;
        this.out = out;
    }

    public Fragment(NfaState start, AtomicReference<NfaState> outState) {
        this.start = start;
        this.out = Arrays.asList(outState);
    }


    public NfaState getStart() {
        return start;
    }

    public List<AtomicReference<NfaState>> getOut() {
        return out;
    }


    public void bindTo(NfaState state) {
        out.forEach(x -> x.set(state));
    }
}
