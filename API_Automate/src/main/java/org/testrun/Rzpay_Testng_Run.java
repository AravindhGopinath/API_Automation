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
import org.excel.ExcelUtils3;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.json.JSONObject;

public class Rzpay_Testng_Run {

	@DataProvider
	public Iterator<Object[]> getTestData() {

		ArrayList<Object[]> testData = ExcelUtils3.getDataFromexcel();
		return testData.iterator();

	}

	@Test(dataProvider = "getTestData")
	private void execution(String INDEX, String Reason, String Mode_Of_Payment, String Expected_Status_Code,
			String Expected_Reason) {

		try {

			System.out.println(INDEX + ") " + Mode_Of_Payment + " - " + Reason);

			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://sitfw.shriramgi.com/api/v1/payment/services");

			String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9zaXRmdy5zaHJpcmFtZ2kuY29tXC9hcGlcL3YxXC9hdXRoZW50aWNhdGlvbiIsImlhdCI6MTY5NDYwNTU3NCwiZXhwIjoxNjk0NjA5MTc0LCJuYmYiOjE2OTQ2MDU1NzQsImp0aSI6IjQwcjZmenZnS2VyRFJyUXkiLCJzdWIiOjEsInBydiI6IjE3ZTRlOWMzOTlmOWI2YWY4ZjFlZjU3ZGVhNjZlNzlhMWM3ZjAwYTUiLCJjb21wYW55SWQiOjF9.0ub2PECmIwCeKTS0g9ODBtPpTru6hKVlKQ8Gcd8IFBc";

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
//			System.out.println("Sheet Mode" + Mode_Of_Payment);
//			System.out.println("Sheet Reason" + Reason);
			String Pay_mode = Mode_Of_Payment;
			String Reason_Purpose = Reason;

			String requestBody = String.format(
					"{\"product_code\": \"FD\", \"company_code\": \"SFL\", \"component\": \"PaymentGateway\", \"action\": \"paymentStatusUpdate\", \"data\": {\"razorpay_order_id\": \"order_Mbo3ebXpW0IZJu\", \"paymode\": \"%s\", \"razorpay_error\": {\"code\": \"BAD_REQUEST_ERROR\", \"description\": \"Your payment has been cancelled. Try again or complete the payment later.\", \"source\": \"customer\", \"step\": \"payment_authentication\", \"reason\": \"%s\", \"metadata\": {\"order_id\": \"order_MblnZIgBYR64wg\",\"payment_id\": \"\"}},\"razorpay_status\": \"error\",\"status_code\": 400}}",
					Pay_mode, Reason_Purpose);

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
			JSONObject data = jsonObject.getJSONObject("data");

			int parseInt = Integer.parseInt(INDEX);

			String exp_status_code = Expected_Status_Code;

			String exp_status_reason = Expected_Reason;

			if (data.has("error_description")) {
				String errorDescription = data.getString("error_description");
				System.out.println("Error Description: " + errorDescription);
				ExcelUtils3.writeinexcel_Reason(errorDescription, parseInt);

				if (exp_status_reason.equals(errorDescription)) {

					ExcelUtils3.writeinexcel_Reason_Status("Pass", parseInt);
				}

				else {

					ExcelUtils3.writeinexcel_Reason_Status("Fail", parseInt);
				}
			} else {
				System.out.println("No error description found.");
			}

			if (data.has("error_code")) {
				int Error_code = data.getInt("error_code");

				System.out.println("Error code: " + Error_code);

				String custom_error_code = String.valueOf(Error_code);

				String Validation = custom_error_code + ".0";

				ExcelUtils3.writeinexcel_Code(custom_error_code, parseInt);

				if (exp_status_code.equals(Validation)) {

					ExcelUtils3.writeinexcel_Code_Status("Pass", parseInt);

				} else {

					ExcelUtils3.writeinexcel_Code_Status("Fail", parseInt);

				}

			} else {

				System.out.println("No Error code found.");
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
