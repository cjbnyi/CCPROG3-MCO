package tests;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileOutputStream;


public class MainFunctionTester {

    public static void main(String[] args) throws Exception {
        ArrayList<String> TestName = new ArrayList<String>();
        TestStatistics ts = new TestStatistics();
        Assertion as = new Assertion();
        FileInputStream FileInput;
        //FileOutputStream FileOutput;
        try {
            //FileOutput = new FileOutputStream("D\\tests\\output.txt");
            FileInput = new FileInputStream("input.txt");
            
            System.setIn(FileInput);
        } catch (Exception e) {
            System.err.println("No FileInput.txt file located.");
            return;
        }
        
        ProcessBuilder pb = new ProcessBuilder("java", "Main.java");
        
        try {
            pb.start();
        } catch (Exception e) {
            System.err.println("No Main.java file located.");
            //FileOutput.close();
            return;
        }

        TestName.add("Create Hotel.");
        ts.setCurrentTestName(TestName.getLast());
        
        ts.captureAssertion(as.AssertEqual(null, null, null));
        //FileOutput.close();
        FileInput.close();

    }
}