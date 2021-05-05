package com.apitests;

import com.utils.HelperClass;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Test_1 {

	@BeforeTest

	@Test
	public void T1_searchForUserName() throws MalformedURLException {
		String userName = "Delphine";
		System.out.println("Search by UserName:" + userName);
		String searchResult = HelperClass.searchUser(userName).toString();

		System.out.println("Search for the user " + searchResult + ": is found!!!");

	}

	@Test
	public void searchForUserId() throws MalformedURLException {

		String UserName = "Delphine";
		int userId = HelperClass.getUserId(UserName);
		Assert.assertNotNull(userId, "UserId Exists");
		System.out.println(userId);
	}

	@Test
	public void fetchCommentsByUserName() throws MalformedURLException {

		String userName = "Delphine";
		int userId = HelperClass.getUserId(userName);
		Integer[] postId = HelperClass.getPostId(userId);
		Assert.assertNotNull(HelperClass.getComments(postId), "Comments are Listed Out for the User");
	}

	@Test
	public void fetchEmailsByPostIds() throws MalformedURLException {

		String userName = "Delphine";
		int userId = HelperClass.getUserId(userName);
		Integer[] postId = HelperClass.getPostId(userId);
		Assert.assertNotNull(HelperClass.getEmailAdresses(postId), "Email Addresses are Listed Out for the User");
	}

}
