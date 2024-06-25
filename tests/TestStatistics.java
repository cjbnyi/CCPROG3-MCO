package tests;
import java.util.ArrayList;

public class TestStatistics{
    private String currentTestName;
    private int testOrder;
    private int corrects;
    private int errors;
    private ArrayList<Integer> correcTests = new ArrayList<Integer>();
    private ArrayList<Integer> errorTests = new ArrayList<Integer>();
    private ArrayList<String> testNames = new ArrayList<String>();
    private ArrayList<Assertion> assertions = new ArrayList<Assertion>();

    TestStatistics(){
        this.testOrder = 1;
        this.corrects = 0;
        this.errors = 0;
    }

    public int getTestOrder() {
        return testOrder;
    }

    public int getCorrects() {
        return corrects;
    }

    public int getErrors() {
        return errors;
    }

    public void setCurrentTestName(String currentTestName) {
        this.currentTestName = currentTestName;
    }

    public void captureAssertion(Assertion incomingAssertion){
        Assertion newCapturedAssertion = new Assertion(incomingAssertion.getSampleInputData(), incomingAssertion.getExpectedOutput(), incomingAssertion.getActualOutput(), incomingAssertion.getIsTrue(), incomingAssertion.getIsActualExpected());
        this.assertions.add(newCapturedAssertion);
        this.testNames.add(this.currentTestName);

        if (incomingAssertion.getIsTrue()){
            this.corrects++;
            this.correcTests.add(this.testOrder);
        } else {
            this.errors++;
            this.errorTests.add(this.testOrder);
        }

        this.testOrder++;
    }

    public void printResults(){
        System.out.println("Corrects: " + this.corrects);
        System.out.println("Correct tests: " + this.correcTests);

        System.out.println("Errors: " + this.errors);
        System.out.println("Error tests: " + this.errorTests);
    }

}
