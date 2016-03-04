package name.krestjaninoff.regexp;

/**
 * The entry point
 */
public class Application {

    public static void main(String[] args) {

        // Get the input data
        if (args.length != 2) {
            System.out.println("Invalid arguments. Use \"APP_NAME regexp_patter string_to_test\"");
        }

        // Compile and match
        try {
            String pattern = args[0];
            String candidate = args[1];

            boolean result = new RegexpEngine().match(pattern, candidate);
            System.out.print(result ? "TRUE" : "FALSE");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
