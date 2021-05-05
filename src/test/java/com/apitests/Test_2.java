
package com.apitests;

import com.utils.HelperClass;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_2 {


	@Test
	public void T01_searchForUserName() throws MalformedURLException {
		String userName = "NotAUser";
		System.out.println("UserName====>" + userName);
		String searchResult = HelperClass.searchUser(userName);
		Assert.assertNotEquals(userName, searchResult);
		System.out.println("Search for the user " + searchResult + ": is Not found!!!");
	}


	@Test
	public void T02_searchForUserName() throws MalformedURLException {
		String userName = "12345678";
		System.out.println("UserName====>" + userName);
		String searchResult = HelperClass.searchUser(userName);
		Assert.assertNotEquals(userName, searchResult);
		System.out.println("Search for the user " + searchResult + ": is Not found!!!");
	}

	@Test
	public void searchForUserId() throws MalformedURLException {

		String UserName = "InvalidUser";
		int userId = HelperClass.getUserId(UserName);
		Assert.assertEquals(userId, -1);
		System.out.println(userId);
	}

	@Test
	public void fetchCommentsByUserName() throws MalformedURLException {

		String userName = "UnknowUser";
		int userId = HelperClass.getUserId(userName);
		Integer[] postId = HelperClass.getPostId(userId);
		Assert.assertNotNull(HelperClass.getComments(postId), "Comments are Not Listed Out for the User");
	}

	@Test
	public void fetchEmailsByPostIds() throws MalformedURLException {

		String userName = "UnknowUser";
		int userId = HelperClass.getUserId(userName);
		Integer[] postId = HelperClass.getPostId(userId);
		Assert.assertNotNull(HelperClass.getEmailAdresses(postId), "Email Addresses are Not Listed Out for the User");
	}

}
