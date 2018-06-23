package com.mjz.ebay.controller;

import com.ebay.sdk.*;
import com.ebay.sdk.call.FetchTokenCall;
import com.ebay.sdk.call.GetSessionIDCall;
import com.ebay.soap.eBLBaseComponents.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EbayTest extends com.ebay.sdk.ApiCall{

    private static Logger logger = LoggerFactory.getLogger(EbayTest.class);
    private static String APPID = "-zbtest-SBX-17141958a-261de1e1";
    private static String DEVID = "bee3987f-377f-41db-820d-1170363ca3ff";
    private static String CERTID = "SBX-82d79e23735d-356b-4a9c-a9e3-5ab7";
//    private static String apiServerUrl = "https://api.ebay.com/wsapi";
    private static String apiServerUrl = "https://api.sandbox.ebay.com/wsapi";
    private static String ruName = "_--zbtest-SBX-171-hjyou";

    public static void main(String[] args) throws Exception {
//        String sessionID = getSessionID();
//        logger.info(sessionID);
//        String url = "https://signin.ebay.com/ws/eBayISAPI.dll?SignIn&runame=_--zbtest-PRD-671-dgglmtm&SessID="+ sessionID;
//        openBrowser(url);

//        String userToken = obtainUserToken("F1cEAA**b4ebac011630ad78ab856a75fffff3f0");
        ApiContext apiContext = getEabyApiContext("AgAAAA**AQAAAA**aAAAAA**9s4PWw**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4agDpSEqQSdj6x9nY+seQ**aZsEAA**AAMAAA**MSF1Tm3MVNQq2rnbC3a21bXvzg+nQavFn3J4FChoAuTo9AvTXUcaQ849dlh3hcHLK8ZBxYYMWNWCLVkXxclV+N/wx3Nm3yQZROQRR4/MMt7ApwF1KimaEPvsZ4AFeGt7PxC4u3KQmzK74rN7cYOdnzd5ZcS7YkTM9aBIqG9tQJCUziGi3Fg1zqEusWtk2tPphhI9MRrsbEuUpsPyXLvJvvjNvVcNxO05glF6RvJQQPO3hJEEuaRg7CSX1ga1Zhqa+Hu3h5ePsO7Oxhwm5i57oW2wy5KGyv9IxW3BFOASFae8LuAvVhTMBjjRI9qnOD/RbjRTxzALQlUmy0Hk584Y+NgteX+YRfCvfK5iImjgxg8CUPhtLtHgK4tteyiNvLT8Rj2zfUj0F+QQgyUQZdwPKyHcxDQTlDRLvQdpl7mBQbv0i/wN2j8QNWbV0FDjVsKu1ijbSDggmzewYgNOC/e7h2TIkqxUL9h5TIUpNfAjDyvJ8ZfOBSO692iWy0sO1/ejWw+1sfGDYN34lyNVE/yirhreTkpbFErFLa9M67KIwlCd3yWD7cJn3OIqR6NormoAQQ92li7KtLWS+d85sUwWg8rUMhgzSzfPHxQZDbJx+aPrjry0FpgbeHZOARcCmWj4MGXUsSi30Xg1PR4WEDCPiqaEyPPwrDwE4n+Lb/S7tRIwGy48xPfykxn5Pp8fr4Jqfx+RyfyMhp0D53k1mgcmZhoLfDL1tlcD6kUz/IIYvV0Cgz0kIK9iLXBnd69MXX49");
        ApiCall call = new ApiCall(apiContext);
        getOrders(call);
    }

    private static void getOrders(ApiCall call) throws SdkException {
        GetOrdersRequestType request = new GetOrdersRequestType();
        OrderIDArrayType orderIDArrayType = new OrderIDArrayType();
        String[] orderIds = new String[]{"10011"};
        orderIDArrayType.setOrderID(orderIds);
        request.setOrderIDArray(orderIDArrayType);
        AbstractResponseType response = call.executeByApiName("GetOrders", request);
    }

    private static void getEbayOfficialTime(ApiCall call) throws SdkException {
        GeteBayOfficialTimeRequestType request = new GeteBayOfficialTimeRequestType();
        AbstractResponseType response = call.executeByApiName("GeteBayOfficialTime", request);
    }

    private static String obtainUserToken(String sessionID) throws Exception {
        ApiContext apiContext = getEabyApiContext();
        FetchTokenCall fetchTokenCall = new FetchTokenCall(apiContext);
        fetchTokenCall.setSessionID(sessionID);
        String token = fetchTokenCall.fetchToken();
        logger.info("token :"+token);
        return token;
    }

    public static String getSessionID() throws Exception {
        ApiContext apiContext = getEabyApiContext();
        String sessionID = obtainSessionID(apiContext);
        return sessionID;
    }

    private static String obtainSessionID(ApiContext apiContext) throws Exception {
        //Create call object and execute the call
        GetSessionIDCall apiCall = new GetSessionIDCall(apiContext);

        apiCall.setRuName(ruName);
        String sessionID = apiCall.getSessionID();
        //Handle the result returned
        logger.info("sessionID : " + sessionID);
        return sessionID;
    }

    public static boolean openBrowser(String url) {
        if (url == null) return false;
        String[] unixBrowser = new String[] { "google-chrome", "firefox" };
        boolean success = false;
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            try {
                Runtime.getRuntime().exec(
                        new String[] { "rundll32.exe", "url.dll,FileProtocolHandler", url });
                success = true;
            } catch (Exception e) {
            }
        } else {
            for (int i = 0; i < unixBrowser.length; ++i)
                try {
                    Runtime.getRuntime().exec(new String[] { unixBrowser[0], url });
                    success = true;
                    break;
                } catch (Exception e) {
                }
        }
        return success;
    }

    public static ApiContext getEabyApiContext(){
        ApiContext apiContext = new ApiContext();
        //set Api Server Url
        apiContext.setApiServerUrl(apiServerUrl);
        ApiAccount apiAccount = new ApiAccount();
        apiAccount.setApplication(APPID);
        apiAccount.setDeveloper(DEVID);
        apiAccount.setCertificate(CERTID);
        apiContext.getApiCredential().setApiAccount(apiAccount);
        return apiContext;
    }

    public static ApiContext getEabyApiContext(String userToken){
        ApiContext apiContext = new ApiContext();
        //set Api Server Url
        apiContext.setApiServerUrl(apiServerUrl);
        ApiAccount apiAccount = new ApiAccount();
        apiAccount.setApplication(APPID);
        apiAccount.setDeveloper(DEVID);
        apiAccount.setCertificate(CERTID);
        ApiCredential apiCredential = new ApiCredential();
        apiCredential.seteBayToken(userToken);
        apiContext.setApiCredential(apiCredential);
        return apiContext;
    }

}
