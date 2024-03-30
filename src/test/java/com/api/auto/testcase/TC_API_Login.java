package com.api.auto.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.auto.utils.PropertiesFileUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class TC_API_Login {
	private String account;
	private String password;
	private Response response;
	private ResponseBody responseBody;
	private JsonPath jsonBody;
	@BeforeClass
	public void init() {
		String baseUrl = PropertiesFileUtils.getProperty("baseurl");
		String loginPath = PropertiesFileUtils.getProperty("loginPath");
		account = PropertiesFileUtils.getProperty("account");
		password = PropertiesFileUtils.getProperty("password");

		RestAssured.baseURI = baseUrl;
		
		
		Map<String, Object>body = new HashMap<String, Object>();
		body.put("account", account);
		body.put("password", password);
		
		RequestSpecification request = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(body);
		
		response = request.post(loginPath);
		responseBody = response.body();
		jsonBody = responseBody.jsonPath();
		
		System.out.println("" + responseBody.asPrettyString());
				
	}
	@Test(priority = 0)
	public void TC01_Validate200Ok() {
		assertEquals(response.getStatusCode(), 200, "Status Check Failed");
	}
	@Test(priority = 1)
	public void TC02_ValidateMessage() {
		if(responseBody.asString().contains("message"))
			assertEquals(jsonBody.get("message"), "Đăng nhập thành công", "Message does not contain 'Đăng nhập thành công'");
		else
			assertEquals(true, false, "Message check failed!");
	}
	@Test(priority = 2)
	public void TC03_ValidateToken() {
		assertTrue(responseBody.asString().contains("token"), "token field check Failed!");
		String token = jsonBody.get("token");
		PropertiesFileUtils.saveToken("token", token);
	}
	@Test(priority = 3)
	public void TC04_ValidateUserType() {
		assertTrue(responseBody.asString().contains("user"), "user field check Failed!");
		String user_type = jsonBody.getString("user.type");
		if( user_type != null) {
			assertEquals(true, true, "user does not contain 'type' field");
		}
		
		assertEquals(user_type, "UNGVIEN", "type is UNGVIEN");
	}
	@Test(priority = 4)
	public void TC05_ValidateAccount() {
		String res_acc = jsonBody.get("user.account");
		String res_pass = jsonBody.get("user.password");
		if(res_acc != null && res_pass != null) {
			assertEquals(res_acc, account, "Account not match!");
			assertEquals(res_pass, password, "Password not match!");
			
		}
		else if(res_acc != null || res_pass != null) {
			if(res_acc != null){				
				assertEquals(res_acc, account, "Account not match!");
			}else {
				assertEquals(true, false, "Account check failed!");
			}
			if(res_pass != null){				
				assertEquals(res_pass, password, "Password not match!");
			}else {
				assertEquals(true, false, "Password check failed!");
			}
		}
		else assertEquals(true, false, "Account & Password check failed!");
			}
}
