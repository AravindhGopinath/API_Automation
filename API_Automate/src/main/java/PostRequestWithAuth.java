import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class PostRequestWithAuth {
	public static void main(String[] args) {
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://sitfw.shriramgi.com/api/v1/payment/services");

			String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9zaXRmdy5zaHJpcmFtZ2kuY29tXC9hcGlcL3YxXC9hdXRoZW50aWNhdGlvbiIsImlhdCI6MTY5NDU5OTczNSwiZXhwIjoxNjk0NjAzMzM1LCJuYmYiOjE2OTQ1OTk3MzUsImp0aSI6ImJZY3p2VlhFeGJCaHFNYjUiLCJzdWIiOjEsInBydiI6IjE3ZTRlOWMzOTlmOWI2YWY4ZjFlZjU3ZGVhNjZlNzlhMWM3ZjAwYTUiLCJjb21wYW55SWQiOjF9.vWNVvuA_3F9qSKb0PWGl_DKZSg7W8k73WGmlhTvK7-E";

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
			String Reason_Purpose = "bank_account_validation_failed";
			String Pay_mode = "UPI";

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

//			String custom_error_codes = jsonObject.getString("custom_error_code").trim();
			
			int int1 = jsonObject.getInt("custom_error_code");
			
			String intAsString = String.valueOf(int1);

			System.out.println(intAsString);
			
//			System.out.println("Original upi_error_reason: " + jsonObject.getString("upi_error_reason"));

			System.out.println("Trimmed upi_error_reason: " + upiErrorReason);

			System.out.println("Trimmed custom_error_code: " + intAsString);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
