package name.krestjaninoff.regexp.nfa;

import java.util.concurrent.atomic.AtomicReference;

/**
 * A state of NFA
 */
public class State {

    public enum Type {
        CHAR, SPLIT, MATCH;
    }

    private int value;
    private Type type;

    private AtomicReference<State> out;
    private AtomicReference<State> outAlt = new AtomicReference<>();

    private int lastList;


    public State(int value, Type type, State out, State outAlt) {
        this.value = value;
        this.type = type;

        this.out.set(out);
        this.outAlt.set(outAlt);
    }


    public int getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public AtomicReference<State> getOut() {
        return out;
    }
    void setOut(State out) {
        this.out.set(out);
    }

    public AtomicReference<State> getOutAlt() {
        return outAlt;
    }
    void setOutAlt(State outAlt) {
        this.outAlt.set(outAlt);
    }

    public int getLastList() {
        return lastList;
    }
}
