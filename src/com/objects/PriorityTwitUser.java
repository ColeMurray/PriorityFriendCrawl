package com.objects;

import java.util.Date;

import twitter4j.Twitter;
import twitter4j.User;

public class PriorityTwitUser extends PriorityBaseUser {
	Twitter twitter;
	Date lastRetrievedTweetDate;
	long lastId;
	User user;
	PriorityUserStatusList statusList;
	
	public Twitter getTwitter() {
		return twitter;
	}

	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}

	public Date getLastRetrievedTweetDate() {
		return lastRetrievedTweetDate;
	}

	public void setLastRetrievedTweetDate(Date lastRetrievedTweetDate) {
		this.lastRetrievedTweetDate = lastRetrievedTweetDate;
	}

	public long getLastId() {
		return lastId;
	}

	public void setLastId(long lastId) {
		this.lastId = lastId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PriorityUserStatusList getStatusList() {
		return statusList;
	}

	public void setStatusList(PriorityUserStatusList statusList) {
		this.statusList = statusList;
	}


	
}
