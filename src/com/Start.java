package com;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.objects.PriorityQueueTwitter;
import com.objects.PriorityQueueTwitter.PQTComparator;
import com.objects.PriorityTwitUserImp;
import com.utility.ConfigBuilder;
import com.utility.PriorityUserException;
import com.utility.Utility;

public class Start {
	public static void main(String[] args) throws TwitterException {
		PriorityQueueTwitter pq;
		pq = new PriorityQueueTwitter(1, new PQTComparator());
		
		List <User> mockUserList = mockData();
		for (User u : mockUserList){
			PriorityTwitUserImp pUser = new PriorityTwitUserImp(
					ConfigBuilder.getTwitter(), u);
			pq.add(pUser);
			
		}

	/*	List<User> userList = Utility.getUserListFromFile("userNames.json");
		for (User u : userList) {
			PriorityTwitUserImp pUser = new PriorityTwitUserImp(
					ConfigBuilder.getTwitter(), u);
			pq.add(pUser);
		} */

		GetAndWriteTweetsThread tweetWriteThread = new GetAndWriteTweetsThread(
				"writeTweetsThread", pq);
		tweetWriteThread.start();
		GetFriendsListThread friendsListThread = new GetFriendsListThread(
				"friendsListThread", pq);
		friendsListThread.start();
	}
	private static List <User> mockData() {
		Twitter twitter = ConfigBuilder.getTwitter();
		try {
			User u = twitter.showUser("SpiritAang");
			User blenkar = twitter.showUser("blenkar");
			List <User> userList = new ArrayList<User>();
			userList.add(u);
			userList.add(blenkar);
			return userList;
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}

	public static class GetAndWriteTweetsThread implements Runnable {
		private String threadName;
		private Thread t;
		private PriorityQueueTwitter pq;

		public GetAndWriteTweetsThread(String name, PriorityQueueTwitter pq) {
			threadName = name;
			this.pq = pq;
		}

		@Override
		public void run() {
			while (true) {
				PriorityTwitUserImp user;
				user = (PriorityTwitUserImp) pq.poll();
				if (user != null) {
					try {
						if (!user.isReceivedAllTweets())
						user.getUserTweetsAndWriteToFile();
						
						pq.add(user);
					} catch (PriorityUserException e) {
						// TODO Auto-generated catch block
						
						e.printStackTrace();
						if (e.getMessage().equals(PriorityUserException.INACCESSIBLE_USER))
						pq.remove();
						else{
							pq.add(user);
						}
					}

				}
			}
		}

		public void start() {
			System.out.println("Running" + threadName);
			if (t == null) {
				t = new Thread(this, threadName);
				t.start();
			}
		}

	}

	public static class GetFriendsListThread implements Runnable {
		private PriorityQueueTwitter pq;
		private Thread t;
		private String threadName;

		public GetFriendsListThread(String name, PriorityQueueTwitter pq) {
			threadName = name;
			this.pq = pq;

		}

		@Override
		public void run() {
			while (true) {

				PriorityTwitUserImp user;

				user = (PriorityTwitUserImp) pq.peek();
				if (user != null)

					if (!user.isHasRetrievedFriends()) {
						pq.addAll(user.getFriendsInPriorityQueue());

					}
			}

		}

		public void start() {
			System.out.println("Running" + threadName);
			if (t == null) {
				t = new Thread(this, threadName);
				t.start();
			}

		}

	}

}
