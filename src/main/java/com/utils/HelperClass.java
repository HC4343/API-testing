package com.utils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.restassured.RestAssured;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.regex.*;

public class HelperClass {

	public static String searchUser(String searchUserName) throws MalformedURLException {

		baseURI = "https://jsonplaceholder.typicode.com";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/users");

		int code = response.getStatusCode();
		System.out.println("Search User : Status Code:" + code);

		String responseString = response.asString();

		List<Object> userList = com.jayway.jsonpath.JsonPath.parse(responseString)
				.read("$.[?(@.username=='" + searchUserName + "')].username");

		if (userList == null || userList.isEmpty()) {
			return null;
		}

		return (String) userList.get(0);
	}

	public static int getUserId(String userName) throws MalformedURLException {

		baseURI = "https://jsonplaceholder.typicode.com";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/users");

		int code = response.getStatusCode();
		System.out.println("Get UserID : Status Code:" + code);

		String responseString = response.body().asString();


		System.out.println("userName === " + userName);

		List<Object> filteredIds = com.jayway.jsonpath.JsonPath.parse(responseString)
				.read("$[?(@.username == '" + userName + "')].id");

		if (filteredIds == null || filteredIds.isEmpty()) {
			return -1;
		}

		Integer id = (Integer) filteredIds.get(0);

		System.out.println("HelperMethods -> Fetched User ID = " + id);

		return id;

	}

	public static Integer[] getPostId(int userId) throws MalformedURLException {

		baseURI = "https://jsonplaceholder.typicode.com";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/posts");

		int code = response.getStatusCode();
		System.out.println("Post Id : Status Code:" + code);


		String responseString = response.asString();

		List<Object> fetchPostIds = com.jayway.jsonpath.JsonPath.parse(responseString)
				.read("$[?(@.userId == " + userId + ")].id");

		Integer[] postIds = fetchPostIds.toArray(new Integer[0]);

		return postIds;

	}

	public static ArrayList<String> getComments(Integer[] postId) throws MalformedURLException {

		baseURI = "https://jsonplaceholder.typicode.com";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/comments");

		int code = response.getStatusCode();
		System.out.println("Status Code:" + code);

		String responseString = response.asString();
		ArrayList<String> postsList = new ArrayList<String>();

		for (int pIds : postId) {
			List<Object> fetchComments = com.jayway.jsonpath.JsonPath.parse(responseString)
					.read("$[?(@.postId == " + pIds + ")].body");
			String postedComments = fetchComments.toString();
			System.out.println("-------------Posted Comments---------" + postedComments);

			postsList.add(postedComments);
		}
		return postsList;
	}

	public static ArrayList<String> getEmailAdresses(Integer[] PostId) throws MalformedURLException {

		baseURI = "https://jsonplaceholder.typicode.com";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/comments");

		int code = response.getStatusCode();
		System.out.println("Status Code:" + code);
		Assert.assertEquals(code, 200);

		String responseString = response.asString();
		ArrayList<String> emailList = new ArrayList<String>();

		for (int pIds : PostId) {
			List<Object> fetchEmaiAddress = com.jayway.jsonpath.JsonPath.parse(responseString)
					.read("$[?(@.postId == " + pIds + ")].email");
			String postedEmail = fetchEmaiAddress.toString();
			System.out.println("-------------Email Addresses of the PostID# " + pIds + "----------" + postedEmail);

			emailList.add(postedEmail);
		}
		return emailList;
	}

	public static ArrayList<String> getEmailAdressesByPostId(int PostId) throws MalformedURLException {

		baseURI = "https://jsonplaceholder.typicode.com";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/comments");

		int code = response.getStatusCode();
		System.out.println("Status Code:" + code);
		Assert.assertEquals(code, 200);

		String responseString = response.asString();
		ArrayList<String> emailList = new ArrayList<String>();

		List<Object> fetchEmaiAddress = com.jayway.jsonpath.JsonPath.parse(responseString)
				.read("$[?(@.postId == " + PostId + ")].email");

		for (Object object : fetchEmaiAddress) {
			emailList.add((String) object);
		}

		return emailList;
	}

	public static void displayStringList(ArrayList<String> dataContents) throws MalformedURLException {
		Iterator<String> iter = dataContents.iterator();
		while (iter.hasNext()) {
			System.out.println("------Comments data ------" + iter.next());
		}
	}

	public static boolean isValidEmailAddress(ArrayList<String> email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		boolean emailValidationResult = true;
		Pattern pattern = Pattern.compile(emailRegex);

		Object[] objects = email.toArray();

		// Printing array of objects

		int len = objects.length;
		System.out.println("Object Length is:" + len);

		for (Object emailID : email) {
			System.out.println("Object Value is:" + emailID);
			emailValidationResult = pattern.matcher((CharSequence) emailID).matches();
			if (!emailValidationResult) {
				return false;
			}
		}

		return emailValidationResult;
	}

}
