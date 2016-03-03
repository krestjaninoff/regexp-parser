package name.krestjaninoff.regexp.nfa;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A fragment of the state
 */
public class Fragment {

    // The start state for the fragment
    private State start;

    // A list of reachable States
    private List<AtomicReference<State>> out;


    public Fragment(State start, List<AtomicReference<State>> out) {
        this.start = start;
        this.out = out;
    }

    public Fragment(State start, AtomicReference<State> outState) {
        this.start = start;
        this.out = Arrays.asList(outState);
    }


    public State getStart() {
        return start;
    }

    public List<AtomicReference<State>> getOut() {
        return out;
    }


    public void bindTo(State state) {
        out.forEach(x -> x.set(state));
    }
}
