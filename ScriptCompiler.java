import java.io.*;
import java.util.*;

public class ScriptCompiler {
     public static void main(String[] args) {
            ScriptLexicalAnalyzer LA= new ScriptLexicalAnalyzer();

            // Check if file path is provided
            if (args.length != 1) {
                System.out.println("Inser Code  as Text File - <input_file.txt>");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
                String currLine;
                int lineNum=0;
                ArrayList<Object[]> tokens=new ArrayList<>();

                while ((currLine = reader.readLine()) != null) {
                    lineNum++;
                    ScriptLexicalAnalyzer.WordBreaker lineTemps= new ScriptLexicalAnalyzer.WordBreaker(currLine, lineNum);
                    ArrayList<String> tempLists =lineTemps.getTemps();
                    // System.out.println(tempLists);
                    // Tokenize the source code
                    tokens.addAll(LA.tokenize(tempLists,lineNum));
                    
                }              

                // Print tokens
                System.out.println("\n TOKENS:");
                for (Object[] arr : tokens) {
                    System.out.println(Arrays.toString(arr));
                }


                //SyntaxAnalyzer
                SyntaxAnalyzer SA = new SyntaxAnalyzer(tokens);
                boolean save=SA.Run();

                

            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        }
    
}
