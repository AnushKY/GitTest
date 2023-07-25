package utility;

 

import lombok.Getter;
import lombok.Setter;

 

public class TestCaseDetails {
    @Getter @Setter
    String testSuiteId;
    String testSuiteName;
    String testCaseId;
    String testCaseName;
    String testPointId;
    String testResultId;
    public TestCaseDetails(String testSuiteId, String testSuiteName, String testCaseId, String testCasename,
                           String testPointId) {
        super();
        this.testSuiteId = testSuiteId;
        this.testSuiteName = testSuiteName;
        this.testCaseId = testCaseId;
        this.testCaseName = testCasename;
        this.testPointId = testPointId;
    }

 

    @Override
    public String toString() {
        return "TestCaseDetails [testSuiteId=" + testSuiteId + ", testSuiteName=" + testSuiteName + ", testCaseId="
                + testCaseId + ", testCasename=" + testCaseName + ", testPointId=" + testPointId + ", testResultId="
                + testResultId + "]";
    }

 

}