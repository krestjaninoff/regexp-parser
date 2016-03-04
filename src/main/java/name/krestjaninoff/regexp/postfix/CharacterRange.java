package name.krestjaninoff.regexp.postfix;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A helper to support Character Ranges
 */
class CharacterRange {

    @AllArgsConstructor
    public class Result {

        @Getter
        private String regexp;
        @Getter
        private int endPosition;
    }

    /**
     * Replaces a Character Range with an Alternation
     *
     * <p>
     *     It's better to introduce a separate NfaState for case, but since we're limited in time,
     *     that is the simplest solution.
     *
     *     Exampeles:
     *     [a-c] -> (a|b|c)
     *     [^a-X] -> (Y|Z)
     * </p>
     */
    public Result expand(String regexp, int start) {
        StringBuffer result = new StringBuffer();
        int end = start + 1;

        return new Result(result.toString(), end);
    }
}
