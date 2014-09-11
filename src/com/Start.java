package com;

import java.util.List;

import twitter4j.TwitterException;
import twitter4j.User;

import com.objects.PriorityQueueTwitter;
import com.objects.PriorityQueueTwitter.PQTComparator;
import com.objects.PriorityTwitThreads.GetAndWriteTweetsThread;
import com.objects.PriorityTwitThreads.GetFriendsListThread;
import com.objects.PriorityTwitUserImp;
import com.utility.ConfigBuilder;
import com.utility.Utility;

public class Start {
	public static void main(String[] args) throws TwitterException {
		PriorityQueueTwitter pq;
		pq = new PriorityQueueTwitter(1, new PQTComparator());

		/*
		 * List <User> mockUserList = mockData(); for (User u : mockUserList){
		 * PriorityTwitUserImp pUser = new PriorityTwitUserImp(
		 * ConfigBuilder.getTwitter(), u); pq.add(pUser);
		 * 
		 * }
		 */

		List<User> userList = Utility.getUserListFromFile("userNames.json");
		for (User u : userList) {
			PriorityTwitUserImp pUser = new PriorityTwitUserImp(
					ConfigBuilder.getTwitter(), u);
			pq.add(pUser);
		}

		GetAndWriteTweetsThread tweetWriteThread = new GetAndWriteTweetsThread(
				"writeTweetsThread", pq);
		tweetWriteThread.start();
		GetFriendsListThread friendsListThread = new GetFriendsListThread(
				"friendsListThread", pq);
		friendsListThread.start();
	}

	

}
