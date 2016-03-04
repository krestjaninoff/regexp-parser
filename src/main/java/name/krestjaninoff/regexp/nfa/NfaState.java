package name.krestjaninoff.regexp.nfa;

import java.util.concurrent.atomic.AtomicReference;

/**
 * A state of NFA
 */
public class NfaState {

    public enum Type {
        CHAR, SPLIT, MATCH;
    }

    private int value;
    private Type type;

    private AtomicReference<NfaState> out;
    private AtomicReference<NfaState> outAlt = new AtomicReference<>();

    private int lastList;


    public NfaState(int value, Type type, NfaState out, NfaState outAlt) {
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

    public AtomicReference<NfaState> getOut() {
        return out;
    }
    void setOut(NfaState out) {
        this.out.set(out);
    }

    public AtomicReference<NfaState> getOutAlt() {
        return outAlt;
    }
    void setOutAlt(NfaState outAlt) {
        this.outAlt.set(outAlt);
    }

    public int getLastList() {
        return lastList;
    }
}
