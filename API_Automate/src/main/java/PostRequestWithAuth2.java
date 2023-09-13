import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class PostRequestWithAuth2 {
	public static void main(String[] args) {
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://sitfw.shriramgi.com/api/v1/payment/services");

			String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9zaXRmdy5zaHJpcmFtZ2kuY29tXC9hcGlcL3YxXC9hdXRoZW50aWNhdGlvbiIsImlhdCI6MTY5NDYwMzAwNCwiZXhwIjoxNjk0NjA2NjA0LCJuYmYiOjE2OTQ2MDMwMDQsImp0aSI6ImRkN1RjTGRZRG40MkFtYk0iLCJzdWIiOjEsInBydiI6IjE3ZTRlOWMzOTlmOWI2YWY4ZjFlZjU3ZGVhNjZlNzlhMWM3ZjAwYTUiLCJjb21wYW55SWQiOjF9.0qepvuUlttPimtpDA4JiW14VZQEpze609F3FmOjThWA";

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
			String Reason_Purpose = "authentication_failed";
			String Pay_mode = "Card";

			String requestBody = String.format(
					"{\"product_code\": \"FD\", \"company_code\": \"SFL\", \"component\": \"PaymentGateway\", \"action\": \"paymentStatusUpdate\", \"data\": {\"razorpay_order_id\": \"order_MblnZIgBYR64wg\", \"paymode\": \"%s\", \"razorpay_error\": {\"code\": \"BAD_REQUEST_ERROR\", \"description\": \"Your payment has been cancelled. Try again or complete the payment later.\", \"source\": \"customer\", \"step\": \"payment_authentication\", \"reason\": \"%s\", \"metadata\": {\"order_id\": \"order_MblnZIgBYR64wg\",\"payment_id\": \"\"}},\"razorpay_status\": \"error\",\"status_code\": 400}}",
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

			if (data.has("error_description")) {
				String errorDescription = data.getString("error_description");
				System.out.println("Error Description: " + errorDescription);
			} else {
				System.out.println("No error description found.");
			}

			if (data.has("error_code")) {
				int Error_code = data.getInt("error_code");
				System.out.println("Error code: " + Error_code);

			} else {

				System.out.println("No Error code found.");
			}

			String upiErrorReason = jsonObject.getString("error_description").trim();

//			String custom_error_codes = jsonObject.getString("custom_error_code").trim();

			int int1 = jsonObject.getInt("error_code");

			String intAsString = String.valueOf(int1);

			System.out.println(intAsString);

//			System.out.println("Original upi_error_reason: " + jsonObject.getString("upi_error_reason"));

//			System.out.println("Trimmed upi_error_reason: " + upiErrorReason);

			System.out.println("Trimmed custom_error_code: " + intAsString);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
