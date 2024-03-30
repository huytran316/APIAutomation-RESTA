package com.api.auto.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.auto.utils.PropertiesFileUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class TC_API_CreateWork {
	private String token;
	private Response response;
	private ResponseBody responseBody;
	private JsonPath jsonBody;
	
	private String myWork = "ky su";
	private String myExperience = "2 nam";
	private String myEducation = "Dai hoc";
	@BeforeClass
	public void init() {
		String baseUrl = PropertiesFileUtils.getProperty("baseurl");
		String createWorkPath =  PropertiesFileUtils.getProperty("createWorkPath");
		String token = PropertiesFileUtils.getToken("token");
		
		RestAssured.baseURI = baseUrl;
		

		Map<String, Object>body = new HashMap<String, Object>();
		body.put("nameWork", myWork);
		body.put("experience", myExperience);
		body.put("education", myEducation);
		
		RequestSpecification request = RestAssured.given()
				.contentType(ContentType.JSON)
				.header("token", token)
				.body(body);
		
		response = request.post(createWorkPath);
		responseBody = response.body();
		jsonBody = responseBody.jsonPath();
		
		System.out.println("" + responseBody.asPrettyString());
	}
	@Test(priority = 0)
	public void TC01_Validate201Create() {
			assertEquals(response.getStatusCode(), 201, "Status Check Failed");
	}	
	@Test(priority = 1)
	public void TC02_ValidateWorkId() {
			assertTrue(responseBody.asString().contains("id"), "id field check Failed!");
	}
	@Test(priority = 2)
	public void TC03_ValidateNameOfWorkMatched() {
		String res_nameWork = jsonBody.get("nameWork");
		assertEquals(res_nameWork, myWork, "work is not matching!");
	}
	@Test(priority = 3)
	public void TC04_ValidateExperienceMatched() {
		String res_exp = jsonBody.get("experience");
		assertEquals(res_exp, myExperience, "experience is not matching!");
	}
	@Test(priority = 4)
	public void TC05_ValidateEducationMatched() {
		String  res_edu = jsonBody.get("education");
		assertEquals(res_edu, myEducation, "education is not matching!");
	}
}
