package com.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.objects.PriorityQueueTwitter.PQTComparator;
import com.utility.PriorityUserException;
import com.utility.Utility;
import com.utility.WriteFunctions;

public class PriorityTwitUserImp extends PriorityTwitUser {
	/**
	 * Everything is initialized as null
	 * 
	 */
	public PriorityTwitUserImp() {
		setupPriorityTwitUserImp();
	}

	public PriorityTwitUserImp(Twitter t, User u) {
		setupPriorityTwitUserImp();
		this.setTwitterAndUser(t, u);
		
	}
	public PriorityTwitUserImp(Twitter t, User u, String parentScreenName ){
		setupPriorityTwitUserImp();
		this.setTwitterAndUser(t,u);
		this.setParentScreenName(parentScreenName);
	}

	public void setupPriorityTwitUserImp() {
		this.setUser(null);
		this.setTwitter(null);
		this.setLastId(Long.MAX_VALUE);
		this.setLastRetrievedTweetDate(new Date());
		this.setStatusList(new PriorityUserStatusList(new ArrayList<Status>()));
		this.setParentScreenName("");
		this.setHasRetrievedFriends(false);
		this.setReceivedAllTweets(false);
	}

	public String getPath() {
		if (this.getParentScreenName() == "") {
			return this.getScreenName();
		} else {
			return this.getParentScreenName() + File.separator
					+ this.getScreenName();
		}

	}

	public String getScreenName() {
		return this.getUser().getScreenName();
	}

	public List<Status> retrieveUserTweets() {

		Twitter twitter = this.getTwitter();
		String screenName = getUser().getScreenName();

		try {
			return twitter.getUserTimeline(screenName);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public PriorityUserStatusList retrieve100TweetsFromId()
			throws PriorityUserException {
		if (this.getUser().getStatusesCount() == 0) {
			this.setLastRetrievedTweetDate(new Date (Long.MIN_VALUE));
			this.setReceivedAllTweets(true);
			throw new PriorityUserException(PriorityUserException.ALL_TWEETS);
		}
		
		if (this.isReceivedAllTweets()){
			throw new PriorityUserException(PriorityUserException.ALL_TWEETS);
		}
		long lastId = this.getLastId();
		Paging pg = new Paging();
		pg.setCount(100);
		// used if user has not set a maxID
		if (lastId != Long.MAX_VALUE) {
			pg.setMaxId(this.getLastId() - 1);
		}
		List<Status> statuses = new ArrayList<Status>();
		PriorityUserStatusList pStatuses = new PriorityUserStatusList(statuses);
		try {

			statuses = this.getTwitter().getUserTimeline(
					this.getUser().getScreenName(), pg);
			if (statuses.size() == 0){
				this.setReceivedAllTweets(true);
				throw new PriorityUserException(PriorityUserException.ALL_TWEETS);
			}
			pStatuses.addAll(statuses);
			pStatuses.statusesInJSON = pStatuses.toJSON();
			Object[] lastIdAndLastDate = Utility
					.getLastIdAndLastCreatedDate(pStatuses);
			this.setLastId((Long) lastIdAndLastDate[0]);
			pg.setMaxId(getLastId());
			this.setLastRetrievedTweetDate((Date) lastIdAndLastDate[1]);

		} catch (TwitterException e) {
			if (checkAndWaitForConnection(e)) {
				return retrieve100TweetsFromId();
			} else {
				e.printStackTrace();
				throw new PriorityUserException(PriorityUserException.INACCESSIBLE_USER);
			}
		}
		return pStatuses;
	}

	public List<User> getFriendsOfUser(int friendAmountToReturn) {
		if (hasRetrievedFriends){
			return getFriendsList();
		}
		PagableResponseList<User> friendsList;
		List<User> friendsListToReturn = new ArrayList<User>();
		long cursor = -1;
		try {
			do {
				friendsList = this.getTwitter().getFriendsList(
						this.getUser().getScreenName(), cursor);
				friendsListToReturn
						.addAll(purgeHighFollowingCountUsers(friendsList));
			} while ((cursor = friendsList.getNextCursor()) != 0
					&& !hasReachedFriendCapCount(friendsList,
							friendAmountToReturn));
		} catch (TwitterException e) {
			if (checkAndWaitForConnection(e)) {
				return getFriendsOfUser(friendAmountToReturn);

			} else
				e.printStackTrace();

		}
		this.setHasRetrievedFriends(true);
		this.setFriendsList(friendsListToReturn);
		return friendsListToReturn;

	}
	public void getUserTweetsAndWriteToFile() throws PriorityUserException {
			this.setStatusList(this.retrieve100TweetsFromId());
			String path = this.getPath();
			WriteFunctions.writeTweetsToFile(this.getStatusList()
					.getStatusesInJSON(), path);
	

	}

	public boolean checkAndWaitForConnection(TwitterException e) {
		Scanner scanner = new Scanner(System.in);

		if (e.isCausedByNetworkIssue()) {
			System.out
					.println("Connection issues, Confirm connected to internet");
			System.out.println("Press enter when connection resumed");
			String s = "";
			while (s != null && !s.equals("y")) {
				s = scanner.nextLine();
			}
			return true;
		} else {
			return false;
		}
	}

	public PriorityQueueTwitter getFriendsInPriorityQueue() {
		List<User> friendList = getFriendsOfUser(200);
		PriorityQueueTwitter pq = new PriorityQueueTwitter(friendList,
				new PQTComparator(), this.getScreenName());
		return pq;
	}

	private List<User> purgeHighFollowingCountUsers(List<User> userList) {
		int HighFollowingCount = 10000;
		List<User> purgedList = new ArrayList<User>();
		for (User u : userList) {
			if (!u.isProtected() && u.getFollowersCount() < HighFollowingCount) {
				purgedList.add(u);

			}
		}
		return purgedList;

	}

	private boolean hasReachedFriendCapCount(List<User> userList,
			int friendCapCount) {
		if (userList.size() < friendCapCount) {
			return false;
		}
		return true;

	}

}
