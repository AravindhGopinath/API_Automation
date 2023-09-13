package org.testrun;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.excel.ExcelUtils2;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.json.JSONObject;

public class Testng_Run {

	@DataProvider
	public Iterator<Object[]> getTestData() {

		ArrayList<Object[]> testData = ExcelUtils2.getDataFromexcel();
		return testData.iterator();

	}

	@Test(dataProvider = "getTestData")
	private void execution(String INDEX, String Reason, String Mode_Of_Payment, String Expected_Status_Code,
			String Expected_Reason) {

		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://sitfw.shriramgi.com/api/v1/payment/services");

			String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9zaXRmdy5zaHJpcmFtZ2kuY29tXC9hcGlcL3YxXC9hdXRoZW50aWNhdGlvbiIsImlhdCI6MTY5NDU5MTE4NiwiZXhwIjoxNjk0NTk0Nzg2LCJuYmYiOjE2OTQ1OTExODYsImp0aSI6IlBhRFlFaEVNd2xFSTVZRGUiLCJzdWIiOjEsInBydiI6IjE3ZTRlOWMzOTlmOWI2YWY4ZjFlZjU3ZGVhNjZlNzlhMWM3ZjAwYTUiLCJjb21wYW55SWQiOjF9.CSH4QDT40P_qNsrt4wN9Z1kYaY8HxF2WVXnWfW5eCx8";

			// Set headers
			httpPost.addHeader("api-version", "v1");
			httpPost.addHeader("api-source", "WEB");
			httpPost.addHeader("api-key", "00519ec7d40f0852d7752995f827fc0d");
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.addHeader("Authorization", "Bearer " + token);

			// Set Basic Auth credentials
//			String username = "superadmin@sfl.in";
//			String password = "admin@123";
//			String auth = username + ":" + password;
//			byte[] encodedAuth = java.util.Base64.getEncoder().encode(auth.getBytes());
//			System.out.println("conded auth"+encodedAuth);
//			String authHeader = "Basic " + new String(encodedAuth);
//			System.out.println("autheader"+authHeader);
//			httpPost.addHeader("Authorization", authHeader);

			// Set request body
			String Reason_Purpose = Reason;
			String Pay_mode = Mode_Of_Payment;

			String requestBody = String.format(
					"{\"product_code\": \"RD\", \"company_code\": \"SFL\", \"component\": \"PaymentGateway\", \"action\": \"errorMaster\", \"api_name\": \"\", \"data\": {\"reason\": \"%s\", \"payment_gateway\": \"rzpay\", \"payment_mode\": \"%s\"}}",
					Reason_Purpose, Pay_mode);

//			String requestBody = "{\"product_code\": \"RD\", \"company_code\": \"SFL\", \"component\": \"PaymentGateway\", \"action\": \"errorMaster\", \"api_name\": \"\", \"data\": {\"reason\": \"bank_account_validation_failed\", \"payment_gateway\": \"rzpay\", \"payment_mode\": \"UPI\"}}";
			StringEntity entity = new StringEntity(requestBody);
			httpPost.setEntity(entity);

			// Send the request
			HttpResponse response = client.execute(httpPost);

			// Print the response
			System.out.println("Response Code: " + response.getStatusLine().getStatusCode());
			String responseBody = EntityUtils.toString(response.getEntity());
			System.out.println("Response Body: " + responseBody);

			JSONObject jsonObject = new JSONObject(responseBody);

			String upiErrorReason = jsonObject.getString("upi_error_reason").trim();

			int int1 = jsonObject.getInt("custom_error_code");

			String custom_error_code = String.valueOf(int1);

			String Validation = custom_error_code + ".0";

//			System.out.println("Original upi_error_reason: " + jsonObject.getString("upi_error_reason"));

			System.out.println("Trimmed upi_error_reason: " + upiErrorReason);

			System.out.println("Trimmed custom_error_code: " + custom_error_code);

			int parseInt = Integer.parseInt(INDEX);

			ExcelUtils2.writeinexcel_Reason(upiErrorReason, parseInt);

			ExcelUtils2.writeinexcel_Code(custom_error_code, parseInt);

			String exp_status_code = Expected_Status_Code;

			System.out.println("excel data -> " + exp_status_code);

			String exp_status_reason = Expected_Reason;

			if (exp_status_code.equals(Validation)) {

				ExcelUtils2.writeinexcel_Code_Status("Pass", parseInt);

			} else {

				ExcelUtils2.writeinexcel_Code_Status("Fail", parseInt);

			}

			if (exp_status_reason.equals(upiErrorReason)) {

				ExcelUtils2.writeinexcel_Reason_Status("Pass", parseInt);
			}

			else {

				ExcelUtils2.writeinexcel_Reason_Status("Fail", parseInt);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@AfterTest
	private void execStop() {

		System.out.println("Execution done");
	}

}
