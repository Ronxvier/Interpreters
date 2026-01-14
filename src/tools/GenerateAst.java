package tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if (args.length !=1) {
            System.err.println("Usage: generate_ast <output dir>");
            System.exit(64);
        }
        String outputDir = args[0];

    }
    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException{
        String path = outputDir + "/" + baseName + ".java"; // ex. outputDir/expr.java
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        writer.println("import java.util.List");
        writer.println();
        writer.println("abstract class " + baseName + " {");
        writer.println("}");
        writer.close();
    }
}
