package com.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.User;

import com.utility.PriorityUserException;
import com.utility.Utility;

public class PriorityTwitUserImp extends PriorityTwitUser {
	/**
	 * Everything is initialized as null
	 * 
	 */
	public PriorityTwitUserImp() {
		this.setUser(null);
		this.setTwitter(null);
		this.setLastId(Long.MAX_VALUE);
		this.setLastRetrievedTweetDate(new Date());
		this.setStatusList(new PriorityUserStatusList(new ArrayList<Status>()));
		this.setParentScreenName("");
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
			throw new PriorityUserException("User has no tweets");
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
			pStatuses.addAll(statuses);
			pStatuses.statusesInJSON = pStatuses.toJSON(); 
			Object[] lastIdAndLastDate = Utility.
									getLastIdAndLastCreatedDate(pStatuses);
			this.setLastId((Long) lastIdAndLastDate[0]);
			pg.setMaxId(getLastId());
			this.setLastRetrievedTweetDate((Date) lastIdAndLastDate[1]);

		} catch (TwitterException e) {
			e.printStackTrace();
			return new PriorityUserStatusList(new ArrayList<Status>());
		}
		return pStatuses;
	}

	public List<User> getFriendsOfUser(int friendAmountToReturn) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return friendsListToReturn;

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
