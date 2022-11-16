package classes;

import java.util.Scanner;

public class Supporter {
    private final Scanner in;
    private final String url = "Enter the url of file: ";
    private final String path = "Enter the path to save download file: ";

    public Supporter() {
        in = new Scanner(System.in);
    }

    public String read(String value) {
        String message = switch (value) {
            case "url" -> url;
            case "path" -> path;
            default -> "Error message";
        };
        System.out.print(message);
        return in.nextLine();
    }

}
