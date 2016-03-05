package name.krestjaninoff.regexp.postfix;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A Counted Repetitions helper
 */
class CountedRepetition {

    @AllArgsConstructor
    public class Result {

        @Getter
        private String source;
        @Getter
        private int newPosition;
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
    public Result expand(String source, int start, String value) {

        // Read the expression
        StringBuffer limits = new StringBuffer();
        int end = start + 1;

        for (; source.charAt(end) != '}' ; end++) {
            limits.append(source.charAt(end));
        }

        // Build the source
        StringBuilder regexp = new StringBuilder();
        if (value.length() > 1) {
            value = "(" + value + ")";
        }

        // Parse the limits
        int commaIndex = limits.lastIndexOf(",");
        boolean isStrict = commaIndex < 0;

        // Strict case: "a{3} -> a.a.a"
        if (isStrict) {

            int limit = Integer.valueOf(limits.toString());
            if (limit <= 0) {
                throw new RuntimeException("Invalid range: " + limit);
            }

            buildSequence(regexp, value, limit, null);

        // Common case
        } else {

            Integer leftLimit = (commaIndex > 0) ?
                    Integer.valueOf(limits.subSequence(0, commaIndex).toString()) : null;
            Integer rightLimit = (commaIndex < limits.length() - 1) ?
                    Integer.valueOf(limits.subSequence(commaIndex + 1, limits.length()).toString()) : null;

            // Check the limits
            if (leftLimit == null && rightLimit == null ||
                    (leftLimit != null && rightLimit != null && leftLimit >= rightLimit)) {
                throw new RuntimeException("Invalid range: " + limits.toString());
            }

            // At least X cases
            if (leftLimit != null) {
                buildSequence(regexp, value, leftLimit, null);

                // At least X case: "a{3,} -> a.a.a+"
                if (rightLimit == null) {
                    regexp.append('+');

                // At least X but not more than Y case: "a{3,5} -> a.a.a.a?.a?"
                } else {
                    regexp.append('.');
                    buildSequence(regexp, value, rightLimit - leftLimit, '?');
                }

            // Not more than Y case: "a{,3} -> a?.a?.a?"
            } else {
                buildSequence(regexp, value, rightLimit, '?');
            }
        }

        String result = source.substring(0, start - value.length()) + regexp.toString() + source.substring(end + 1);
        return new Result(result, start - 1 - value.length());
    }

    private void buildSequence(StringBuilder regexp, String value, int limit, Character qualifier) {

        for (int j = 0; j < limit; j++) {
            regexp.append(value);

            if (qualifier != null) {
                regexp.append(qualifier);
            }

            regexp.append('.');
        }

        regexp.deleteCharAt(regexp.length() - 1);
    }
}
