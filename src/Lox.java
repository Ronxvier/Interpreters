import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Lox {
    static boolean hadError = false;
    public static void main(String[] args) throws IOException{
        if (args.length>1){
            System.out.println("Usage: jlox [script]");
            System.exit(64); // too many arguments exception
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError) System.exit(65); // code has an error exception
        /* This String() is a constructor that creates a String object by decoding the
        provided byte array using the platforms default character set.*/
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        /* Creates an InputStreamReader that wraps System.in (kbd input stream)
        System.in reads bytes, InputStreamReader converts bytes to characters. */
        BufferedReader reader = new BufferedReader(input);
        /* Wraps the InputStreamReader in a new BufferedReader
        BufferedReader lets you read text line by line using the readLine() method.
         */
        for (;;) {
            // for(;;) is an infinite loop, like while(true)
            System.out.print("> "); // prompt
            String line = reader.readLine(); // reads a line like a string
            if (line == null) break; // break the loop if there's no entry
            run(line);
            hadError = false; // reset flag in interactive loop
        }
    }

    private static void run(String source){
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens(); // will be instantiated when we write the scanner
        // For now, just print the tokens.
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
    static void error(int line, String message){
        report(line, "", message);
    }

    private static void report(int line, String where, String message){
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message
        );
        hadError = true;
    }
}

