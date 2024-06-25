package tests;

import java.util.ArrayList;

public class Assertion {
    String SampleInputData;
    String ExpectedOutput;
    String ActualOutput;
    Boolean isActualExpected;
    Boolean isTrue;


    Assertion() {
        this.SampleInputData = "";
        this.ExpectedOutput =  "";
        this.ActualOutput = "";
        this.isActualExpected = true;
        this.isTrue = false;
    }
    
    Assertion(String SampleInputData, String ExpectedOutput, String ActualOutput, Boolean isTrue, Boolean isActualExpected) {
        this.SampleInputData = SampleInputData;
        this.ExpectedOutput = ExpectedOutput;
        this.ActualOutput = ActualOutput;
        this.isTrue = isTrue;
        this.isActualExpected = isActualExpected;
        
    }

    public String getSampleInputData() {
        return this.SampleInputData;
    }

    public String getExpectedOutput() {
        return this.ExpectedOutput;
    }
    
    public String getActualOutput() {
        return this.ActualOutput;
    }

    public Boolean getIsActualExpected() {
        return this.isActualExpected;
    }

    public Boolean getIsTrue() {
        return this.isTrue;
    }

    public Assertion AssertEqual(String SampleInputData, int ExpectedOutput, int ActualOutput){
        this.isActualExpected = true;
        this.SampleInputData = SampleInputData;
        this.ExpectedOutput = String.valueOf(ExpectedOutput);
        this.ActualOutput = String.valueOf(ActualOutput);
        this.isTrue = this.ExpectedOutput.equals(this.ActualOutput);
        return this;
    }

    public Assertion AssertEqual(String SampleInputData, String ExpectedOutput, String ActualOutput){
        this.isActualExpected = true;
        this.SampleInputData = SampleInputData;
        this.ExpectedOutput = String.valueOf(ExpectedOutput); 
        this.ActualOutput = String.valueOf(ActualOutput);
        this.isTrue = this.ExpectedOutput.equals(this.ActualOutput);
        return this;
    }

    public Assertion AssertContradict(String SampleInputData, int ExpectedOutput, int ActualOutput){
        this.isActualExpected = false;
        this.SampleInputData = SampleInputData;
        this.ExpectedOutput = String.valueOf(ExpectedOutput);
        this.ActualOutput = String.valueOf(ActualOutput);
        this.isTrue = !this.ExpectedOutput.equals(this.ActualOutput);
        return this;
    }

    public Assertion AssertContradict(String SampleInputData, String ExpectedOutput, String ActualOutput){
        this.isActualExpected = false;
        this.SampleInputData = SampleInputData;
        this.ExpectedOutput = String.valueOf(ExpectedOutput); 
        this.ActualOutput = String.valueOf(ActualOutput);
        this.isTrue = !this.ExpectedOutput.equals(this.ActualOutput);
        return this;
    }

}
