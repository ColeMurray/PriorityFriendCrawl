package com.objects;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public class PriorityTwitUserImp extends PriorityTwitUser {
	public void retrieveUserTweets() throws TwitterException {
		Twitter twitter = this.getTwitter();
		String screenName = getUser().getScreenName();

		twitter.getUserTimeline(screenName);

	}

}
