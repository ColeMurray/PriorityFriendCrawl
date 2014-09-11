package com.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	public PriorityTwitUserImp(Twitter t, User u, String parentScreenName) {
		setupPriorityTwitUserImp();
		this.setTwitterAndUser(t, u);
		this.setParentScreenName(parentScreenName);
	}

	public void setupPriorityTwitUserImp() {
		this.setUser(null);
		this.setTwitter(null);
		this.setLastId(Long.MAX_VALUE);
		this.setLastRetrievedTweetDate(new Date());
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

	public List<Status> retrieveUserTweets(Paging pg)
			throws PriorityUserException {

		Twitter twitter = this.getTwitter();
		String screenName = getUser().getScreenName();

		try {
			return twitter.getUserTimeline(screenName, pg);
		} catch (TwitterException e) {
			e.printStackTrace();
			throw new PriorityUserException(
					PriorityUserException.INACCESSIBLE_USER);
		}

	}

	public List<String> retrieve100TweetsAndUpdateUser()
			throws PriorityUserException {
		long lastId;
		Paging pg;
		List<String> statusesInJSON;
		List<Status> statuses;

		checkZeroOrAllStatusesReceived();

		pg = new Paging();
		pg.setCount(100);

		lastId = getLastId();
		if (lastId != Long.MAX_VALUE)
			pg.setMaxId(lastId - 1);
		statuses = retrieveUserTweets(pg);
		statusesInJSON = Utility.toJSON(statuses);
		if (statuses.isEmpty()) {
			setReceivedAllTweets(true);
		}
		updateLastIdAndLastReceivedDate(statuses);
		return statusesInJSON;

	}

	private void checkZeroOrAllStatusesReceived() throws PriorityUserException {
		if (isReceivedAllTweets()) {
			throw new PriorityUserException(PriorityUserException.ALL_TWEETS);
		} else if (getUser().getStatusesCount() == 0) {
			updateZeroStatusUser();
			throw new PriorityUserException(PriorityUserException.ALL_TWEETS);
		}
	}

	private void updateZeroStatusUser() {
		this.setLastRetrievedTweetDate(new Date(Long.MIN_VALUE));
		this.setReceivedAllTweets(true);
	}

	private void updateLastIdAndLastReceivedDate(List<Status> statuses) {
		for (Status s : statuses) {
			if (s.getId() < getLastId()) {
				setLastId(s.getId());
			}
			if (s.getCreatedAt().before(getLastRetrievedTweetDate())) {
				setLastRetrievedTweetDate(s.getCreatedAt());
			}
		}
	}

	public void getUserTweetsAndWriteToFile() throws PriorityUserException {
		List<String> statuses = retrieve100TweetsAndUpdateUser();
		String path = this.getPath();
		WriteFunctions.writeTweetsToFile(statuses, path);

	}

	public PriorityQueueTwitter getFriendsInPriorityQueue()
			throws TwitterException {
		List<User> friendList = getFriendsOfUser(200);
		PriorityQueueTwitter pq = new PriorityQueueTwitter(friendList,
				new PQTComparator(), this.getScreenName());
		return pq;
	}

	public List<User> getFriendsOfUser(int friendAmountToReturn)
			throws TwitterException {
		PagableResponseList<User> friendsList;
		List<User> friendsListToReturn = new ArrayList<User>();
		long cursor = -1;
		try {
			do {
				friendsList = this.getTwitter().getFriendsList(
						this.getUser().getScreenName(), cursor);
				friendsListToReturn.addAll(Utility
						.purgeHighFollowingCountUsers(friendsList));
			} while ((cursor = friendsList.getNextCursor()) != 0
					&& !Utility.hasReachedFriendCapCount(friendsList,
							friendAmountToReturn));
		} catch (TwitterException e) {
			e.printStackTrace();
			throw new TwitterException(e);
		}
		this.setHasRetrievedFriends(true);
		this.setFriendsList(friendsListToReturn);
		return friendsListToReturn;

	}

}
