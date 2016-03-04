package name.krestjaninoff.regexp.nfa;

/**
 * A state of NFA
 */
public class NfaState {

    public enum Type {
        CHAR, SPLIT, MATCH
    }

    public static class Holder {
        private NfaState state;

        public NfaState get() {
            return this.state;
        }

        public void set(NfaState state) {
            this.state = state;
        }
    }

    private Character value;
    private Type type;

    private Holder out = new Holder();
    private Holder outAlt = new Holder();


    public NfaState(Character value, Type type, NfaState out, NfaState outAlt) {
        this.value = value;
        this.type = type;

        this.out.set(out);
        this.outAlt.set(outAlt);
    }


    public Character getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public Holder getOut() {
        return out;
    }

    public Holder getOutAlt() {
        return outAlt;
    }
}
