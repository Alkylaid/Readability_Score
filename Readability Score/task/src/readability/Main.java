package readability;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
public class Main {


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Readability analysis = new Readability(args[0], scanner);
        analysis.start();
    }

}

