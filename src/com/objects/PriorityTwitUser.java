package com.objects;

import java.util.Date;

import twitter4j.Twitter;
import twitter4j.User;

public class PriorityTwitUser extends PriorityBaseUser {
	Twitter twitter;
	Date lastRetrievedTweetDate;
	long sinceID;
	User user;
	
	
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

	public long getSinceID() {
		return sinceID;
	}

	public void setSinceID(long sinceID) {
		this.sinceID = sinceID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	
}
