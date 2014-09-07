package com.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.utility.Utility;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.User;

public class PriorityTwitUserImp extends PriorityTwitUser {
	public List <Status> retrieveUserTweets()  {
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
	
	public List<String> getTweetsIn6Weeks(int retrievalLimit) throws IllegalArgumentException{
		if (!isValidArgs(retrievalLimit)){
			throw new IllegalArgumentException();
		}
		
		PriorityUserStatus userTweets =  (PriorityUserStatus) retrieveUserTweets();
		return userTweets.toJSON();
		
	}
	
	public List <String> retrieveTweetsInString (int retrievalLimit){
		int userStatusCount = getUser().getStatusesCount();
		List <String> userTweets = new ArrayList <String>();
		Paging pg = new Paging();
		pg.setCount(100);
		while (userTweets.size() <= retrievalLimit && userTweets.size() <= userStatusCount){
			try {
				PriorityUserStatus statuses = (PriorityUserStatus) twitter.getUserTimeline(this.getUser().getScreenName(), pg);
				long lastId = Utility.getLastId(statuses);
				this.setLastId(lastId);
				this.setLastRetrievedTweetDate(lastRetrievedTweetDate);
				userTweets.addAll(statuses.toJSON());
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		return userTweets;
		
	}
	
	public class PriorityUserStatus extends ArrayList<Status>{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3230766945065066029L;


		/**
		 * 
		 * @return
		 */
		public List <String> toJSON(){
			List <String> JSON = new ArrayList<String>();
			for ( Status s : this ){
				String tweetToJSON = TwitterObjectFactory.getRawJSON(s);
				JSON.add(tweetToJSON);
				
			}
			return JSON;
		}
		
	}
	
	private boolean isValidArgs(int retrievalLimit) {
		if (retrievalLimit <= 0)
			return false;
		return true;
		
	}
	public List<String> getTweetsIn6Weeks(Twitter twitter, User u,
			int count) {
		int days = 42; // 6 weeks in our example
		Paging pg = new Paging();
		pg.setCount(100);
		List<Status> retrievedStatuses = new ArrayList<Status>();
		long lastID = Long.MAX_VALUE;
		List<String> JSON = new ArrayList<String>();
		int userStatusCount = u.getStatusesCount();
		if (userStatusCount == 0){
			return new ArrayList<String>();
		}
		if (count == -1) {
			count = Integer.MAX_VALUE;
		}

		while (retrievedStatuses.size() < count
				&& retrievedStatuses.size() < userStatusCount) {
			try {

				List<Status> tweets = twitter.getUserTimeline(
						u.getScreenName(), pg);
				if (tweets.size() == 0){
					return JSON;
				}
				System.out.println("Retrieving tweets for: " + u.getScreenName() + 
						" retrieved: "+ tweets.size());

				/*
				 * Update user status count. Set here to prevent needing to make
				 * a call to lookup user
				 */
				
					
				
				for (Status t : tweets) {
					String tweetToJSON = TwitterObjectFactory.getRawJSON(t);
					JSON.add(tweetToJSON);

				}
				retrievedStatuses.addAll(tweets);
				if (getGapBetweenFirstAndLast(retrievedStatuses) >= days) {
					return JSON;
				}

				for (Status t : retrievedStatuses) {

					if (t.getId() < lastID)
						lastID = t.getId();
				}
			}

			catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return JSON;
			}
			pg.setMaxId(lastID - 1);
		}
		return JSON;

	}
	
	private static int getGapBetweenFirstAndLast(List<Status> statuses) {
		if (statuses.isEmpty()) {
			return 0;
		}

		Date lastTweetDate = statuses.get(statuses.size() - 1).getCreatedAt();
		Date firstTweetDate = statuses.get(0).getCreatedAt();
		DateTime lastTweet = new DateTime(lastTweetDate);
		DateTime firstTweet = new DateTime(firstTweetDate);
		int daysBetween = Days.daysBetween(lastTweet, firstTweet).getDays();
		System.out.println("Days Between: " + daysBetween);
		return daysBetween;
	}

}
