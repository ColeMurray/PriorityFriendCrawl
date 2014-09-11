package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.objects.PriorityTwitUserImp;
import com.objects.PriorityUserStatusList;
import com.utility.PriorityUserException;
import com.utility.Utility;

public class PriorityTwitUserTest {
	PriorityTwitUserImp ptUser;
	PriorityTwitUserImp zeroTweetsPriorityUser;
	Twitter twitter;
	User user;
	User zeroTweetsUser;
	@Before
	public void setUp() throws Exception {
		twitter = Utility.getValidTwitterObject();
		ptUser = new PriorityTwitUserImp();
		user = twitter.showUser("SpiritAang");
		ptUser.setUser(user);
		ptUser.setTwitter(twitter);
		
		zeroTweetsPriorityUser = new PriorityTwitUserImp();
		zeroTweetsUser = twitter.showUser("blenkar");
		zeroTweetsPriorityUser.setUser(zeroTweetsUser);
		zeroTweetsPriorityUser.setTwitter(twitter);
	}
	@Test
	public void testPriorityStatusFromList (){
		Paging pg = new Paging();
		pg.setCount(100);
		List<Status> statuses = null;
		try {
			statuses = ptUser.retrieveUserTweets(pg);
		} catch (PriorityUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PriorityUserStatusList pStatuses = new PriorityUserStatusList(statuses);
		assert(!pStatuses.isEmpty());
	}

	@Test
	public void testRetrieve100TweetsFromId(){
		try { 
			ptUser.retrieve100TweetsAndUpdateUser();
			long firstLastIdRetrieved = ptUser.getLastId();
			System.out.println (firstLastIdRetrieved);
			
			ptUser.retrieve100TweetsAndUpdateUser();
			long secondLastIdRetrieved = ptUser.getLastId();
			assertTrue(firstLastIdRetrieved != secondLastIdRetrieved);
		}
		catch (PriorityUserException e){
			e.printStackTrace();
			fail();
		}
		
		try{
			zeroTweetsPriorityUser.retrieve100TweetsAndUpdateUser();
		}
		catch (PriorityUserException e ){
			assert(e.getMessage().equals(PriorityUserException.ALL_TWEETS));
		}
		
	}
	
	@Test
	public void testGetFriends(){
		List <User> userList = null;
		try {
			userList = ptUser.getFriendsOfUser(200);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(userList);
	}

}
