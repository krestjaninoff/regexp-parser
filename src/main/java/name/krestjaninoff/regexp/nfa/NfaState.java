package name.krestjaninoff.regexp.nfa;

import lombok.Getter;

/**
 * A state of NFA
 */
public class NfaState {

    enum Type {
        CHAR, SPLIT, MATCH
    }

    static class Holder {
        private NfaState state;

        public NfaState get() {
            return this.state;
        }

        public void set(NfaState state) {
            this.state = state;
        }
    }

    @Getter
    private Character value;
    @Getter
    private Type type;

    @Getter
    private Holder out = new Holder();
    @Getter
    private Holder outAlt = new Holder();


    public NfaState(Character value, Type type, NfaState out, NfaState outAlt) {
        this.value = value;
        this.type = type;

        this.out.set(out);
        this.outAlt.set(outAlt);
    }
}
