package utility;

 

import helper.WebHelper;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import services.WebServices;

 

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

 

public class AzureTestPlan {

 

    public static List<TestCaseDetails> testCaseDetails = new ArrayList<TestCaseDetails>();

 

    private static String baseUrl = "https://dev.azure.com/PhilipsAgile/1.0%20DC%20Digital/_apis";
    private static String accessToken = "6yjlfkt3m6hkxx456qzn55esujppmrbtp3gajo7uniehk4i5rp2q";
    static String credentials = ":" + accessToken;
    public static String base64ClientCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
    public static String runId = null;
    private static String createRunEndPoint = baseUrl + "/test/runs?api-version=5.1";

 

    public static void fetchTestData(String planId, String suiteId) {
        try {
            runId = CreateTestRun(planId, suiteId);
            System.out.println(" I create RunID -- " + runId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 

    public static String CreateTestRun(String planID, String suiteID) throws ClientProtocolException, IOException {
        getAllTestcaseDetails(planID, suiteID);
        int[] pointId = new int[testCaseDetails.size()];
        for (int i = 0; i < testCaseDetails.size(); i++) {
            pointId[i] = Integer.parseInt(testCaseDetails.get(i).testPointId);
        }
        JSONObject requestBody = new JSONObject();
        requestBody.put("automated", true);
        requestBody.put("name", ".COM_BDD_Automation");
        requestBody.put("state", "InProgress");
        JSONObject plan = new JSONObject();
        plan.put("id", planID);
        requestBody.put("plan", plan);
        requestBody.put("type", "Web");
        requestBody.put("pointIds", pointId);
        StringEntity params = new StringEntity(requestBody.toString());
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(createRunEndPoint);
        postRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        postRequest.setHeader("Authorization", "Basic " + base64ClientCredentials);
        postRequest.setEntity(params);
        HttpResponse response = httpClient.execute(postRequest);
        JSONObject jsonresponse = new JSONObject(new BasicResponseHandler().handleResponse(response));
        runId = jsonresponse.get("id").toString();
        return runId;
    }
    
    public static void getAllTestcaseDetails(String planID, String suiteID) throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getrequest = new HttpGet(baseUrl + "/testplan/Plans/" + planID + "/Suites/" + suiteID + "/TestCase");
        getrequest.setHeader(HttpHeaders.ACCEPT, "application/json");
        getrequest.setHeader("Authorization", "Basic " + base64ClientCredentials);
        HttpResponse response = httpClient.execute(getrequest);
        JSONObject jsonResponse = new JSONObject(new BasicResponseHandler().handleResponse(response));
        JSONArray array = jsonResponse.getJSONArray("value");
        for (int i = 0; i < array.length(); i++) {
            String testSuitId = array.getJSONObject(i).getJSONObject("testSuite").get("id").toString();
            String testSuiteName = array.getJSONObject(i).getJSONObject("testSuite").get("name").toString();
            String testCaseName = array.getJSONObject(i).getJSONObject("workItem").get("name").toString();
            String testCaseId = array.getJSONObject(i).getJSONObject("workItem").get("id").toString();
            String testPointId = array.getJSONObject(i).getJSONArray("pointAssignments").getJSONObject(0).get("id")
                    .toString();
            TestCaseDetails test = new TestCaseDetails(testSuitId, testSuiteName, testCaseId, testCaseName,
                    testPointId);
            testCaseDetails.add(test);
        }
    }

 

    public static void updateResults(String testCaseID, String status) throws ClientProtocolException, IOException {
        UpdateTestCase(getTestResults(runId, testCaseID), runId, status);
    }

 

    public static void UpdateTestCase(String testResultId, String runId, String outcome)
            throws ClientProtocolException, IOException {
        JSONObject requestbody = new JSONObject();
        JSONArray ja = new JSONArray();
        requestbody.put("id", testResultId);
        requestbody.put("state", "Completed");
        requestbody.put("outcome", outcome);
        ja.put(requestbody);
        StringEntity params = new StringEntity(ja.toString());
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPatch patchrequest = new HttpPatch(baseUrl + "/test/Runs/" + runId + "/results?api-version=5.1");
        patchrequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        patchrequest.setHeader("Authorization", "Basic " + base64ClientCredentials);
        patchrequest.setEntity(params);
        HttpResponse response = httpClient.execute(patchrequest);
    }

 

    public static String getTestResults(String runId, String testCaseId) throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getrequest = new HttpGet(baseUrl + "/test/Runs/" + runId + "/results?api-version=5.1");
        getrequest.setHeader(HttpHeaders.ACCEPT, "application/json");
        getrequest.setHeader("Authorization", "Basic " + base64ClientCredentials);
        HttpResponse response = httpClient.execute(getrequest);
        JSONObject jsonresponse = new JSONObject(new BasicResponseHandler().handleResponse(response));
        JSONArray testresultArray = jsonresponse.getJSONArray("value");
        String testResultId = null;
        String testId = null;
        for (int i = 0; i < testresultArray.length(); i++) {
            testResultId = testresultArray.getJSONObject(i).get("id").toString();
            testId = testresultArray.getJSONObject(i).getJSONObject("testCase").getString("id").toString();
            if (testId.equalsIgnoreCase(testCaseId)) {
                break;
            }
        }
        return testResultId;
    }
    public static void createPlanId() {
        String planId = WebHelper.getValue(WebServices.PLAN_ID);
        String suiteId = WebHelper.getValue(WebServices.SUITE_ID);
        AzureTestPlan.fetchTestData(planId, suiteId);

 

    }

 

    public static void updateAzureTestStatus(File file) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(file));
            org.json.simple.JSONObject testResultDetails = (org.json.simple.JSONObject) obj;
            org.json.simple.JSONArray arr = (org.json.simple.JSONArray) testResultDetails.get("TestResult");
            for (int i = 0; i < arr.size(); i++) {
                org.json.simple.JSONObject ob = (org.json.simple.JSONObject) arr.get(i);
                String tcID = ob.get("TestCaseID").toString();
                String tcResult = ob.get("Result").toString();
                System.out.println("TestCaseID: " + tcID + " || Result: " + tcResult);
                AzureTestPlan.updateResults(tcID, tcResult);
            }
        } catch (Exception e) {
        }
    }
}
    