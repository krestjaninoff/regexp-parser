package name.krestjaninoff.regexp.postfix;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A helper to support Counted Repetitions
 */
class CountedRepetition {

    @AllArgsConstructor
    public class Result {

        @Getter
        private String regexp;
        @Getter
        private int endPosition;
    }

    /**
     * Replaces a Counted Repetition with a simple regexp
     *
     * <ul>
     *     <li>a{3} -> a.a.a</li>
     *     <li>a{3,} -> a.a.a+</li>
     *     <li>a{,3} -> a?.a?.a?</li>
     *     <li>a{3,5} -> a.a.a.a?.a?</li>
     * </ul>
     */
    public Result expand(String regexp, int start, Character value) {
        StringBuffer result = new StringBuffer();
        int end = start + 1;

        return new Result(result.toString(), end);
    }
}
