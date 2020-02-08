/*
 * This is a Java Helper Class with all core functions/methods for this project 'Backend Test Challenge - FreeNow'
 * search by User and return the userNameList/boolean
 * Find the Id for a particular user and return as integer value
 * Find the PostId for a particular userId and return as integer array
 * Fetch the Comments content for each postIds of a particular UserId and return as Array of Strings
 * Fetch the EmaiAddresses for each postIds of a particular UserId and return as Array of Strings
 */
package com.sindhu.utils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import sun.util.logging.PlatformLogger.ConfigurableBridge.LoggerConfiguration;

public class HelperMethods {
	public static String searchUser(String searchUserName) throws MalformedURLException {
		/*
		 * Search By User Method to find a particular user given the userName as input
		 * argument from the '/users' API response content
		 */

		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/users");

		int code = response.getStatusCode();
		System.out.println("Search User : Status Code:" + code);
		
		String responseString = response.asString();

//		List<Object> fetchUserName = com.jayway.jsonpath.JsonPath.parse(responseString).read("$.[?(@.username=='"+searchUserName+"')].username");
		List<Object> fetchUserName = com.jayway.jsonpath.JsonPath.parse(responseString).read("$.[?(@.username=='Samantha')].username");
		String getUserName = fetchUserName.toString();			
//		System.out.println("HelperMethods -> Searched User Name Value is "+getUserName);
		
		return getUserName;
	}

	public static int getUserId(String UserName) throws MalformedURLException {
		/*
		 * Get the UserId for a particular user given the userName as input argument
		 * from the '/users' API response content
		 * 
		 */
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/users");

		int code = response.getStatusCode();
		System.out.println("Get UserID : Status Code:" + code);

		String responseString = response.body().asString();

		// Fetching the UserId of a particular userName as given parameter

		List<Object> filteredIds = com.jayway.jsonpath.JsonPath.parse(responseString)
				.read("$[?(@.username == 'Samantha')].id");

		Integer id = (Integer) filteredIds.get(0);

		System.out.println("HelperMethods -> Fetched User ID = " + id);

		return id;

	}

	public static Integer[] getPostId(int userId) throws MalformedURLException {
		/*
		 * Get the UserId for a particular user given the userName as input argument
		 * from the '/posts' API response content
		 */

		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/posts");

		int code = response.getStatusCode();
		System.out.println("Post Id : Status Code:" + code);
		// Assert.assertEquals(code,200);

		String responseString = response.asString();

		List<Object> fetchPostIds = com.jayway.jsonpath.JsonPath.parse(responseString)
				.read("$[?(@.userId == " + userId + ")].id");

		Integer[] postIds = fetchPostIds.toArray(new Integer[0]);

		return postIds;

	}

	public static ArrayList<String> getComments(Integer[] PostId) throws MalformedURLException {
		/*
		 * Get the List of Comments for a particular user's posts given their postIds as
		 * input argument from the '/comments' API response content
		 */

		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/comments");

		int code = response.getStatusCode();
		System.out.println("Status Code:" + code);
		//	Assert.assertEquals(code,200);

		String responseString = response.asString();
		ArrayList<String> postsList = new ArrayList<String>();

		for (int pIds : PostId) {
			List<Object> fetchComments = com.jayway.jsonpath.JsonPath.parse(responseString)
					.read("$[?(@.postId == " + pIds + ")].body");
			String postedComments = fetchComments.toString();
			System.out.println("-------------Posted Comments---------" + postedComments);

			postsList.add(postedComments);
		}
		return postsList;
	}

	public static ArrayList<String> getEmailAdresses(Integer[] PostId) throws MalformedURLException {
		/*
		 * Get the List of Email Addresses for a particular user's posts given their
		 * postIds as input argument from the '/comments' API response content
		 */

		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/comments");

		int code = response.getStatusCode();
		System.out.println("Status Code:" + code);
		Assert.assertEquals(code,200);

		String responseString = response.asString();
		ArrayList<String> emailList = new ArrayList<String>();

		for (int pIds : PostId) {
			List<Object> fetchEmaiAddress = com.jayway.jsonpath.JsonPath.parse(responseString)
					.read("$[?(@.postId == " + pIds + ")].email");
			String postedEmail = fetchEmaiAddress.toString();
			System.out.println("-------------Email Addresses----------" + postedEmail);

			emailList.add(postedEmail);
		}
		return emailList;
	}

	public static void displayStringList(ArrayList<String> dataContents) throws MalformedURLException {
		Iterator iter = dataContents.iterator();
		while (iter.hasNext()) {
			System.out.println("------Comments data ------" + iter.next());
		}
	}

	public static void main(String[] args) throws MalformedURLException {

		String UserName = "Samantha";
		System.out.println("UserName====>" + UserName);
		String searchResult = searchUser(UserName);
		System.out.println(searchResult);

		int getUserId = getUserId(UserName);
		Integer postIds[] = getPostId(getUserId);
		for (int pIds : postIds) {
			System.out.println("Post Ids:" + pIds);
		}

		ArrayList<String> fetchListOfComments = getComments(postIds);
//	displayStringList(fetchListOfComments);

		ArrayList<String> fetchListOfEmails = getEmailAdresses(postIds);
//	displayStringList(fetchListOfEmails);

	}

}
