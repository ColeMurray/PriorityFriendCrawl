package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;

import com.objects.PriorityTwitUserImp;
import com.objects.PriorityUserStatusList;
import com.utility.PriorityUserException;
import com.utility.Utility;

public class PriorityTwitUserTest {
	

	// TODO finish Setup and testTweetRetrieval
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
		List <Status> statuses = ptUser.retrieveUserTweets();
		PriorityUserStatusList pStatuses = new PriorityUserStatusList(statuses);
		assert(!pStatuses.isEmpty());
	}
	
	
	@Test
	public void testRetrieve100TweetsFromId(){
		try { 
			PriorityUserStatusList statuses = ptUser.retrieve100TweetsFromId();
			long firstLastIdRetrieved = ptUser.getLastId();
			System.out.println (firstLastIdRetrieved);
			statuses.addAll( ptUser.retrieve100TweetsFromId());
			long secondLastIdRetrieved = ptUser.getLastId();
			assertTrue(firstLastIdRetrieved != secondLastIdRetrieved);
		}
		catch (PriorityUserException e){
			e.printStackTrace();
			fail();
		}
		
		try{
			PriorityUserStatusList emptyStatuses = zeroTweetsPriorityUser.retrieve100TweetsFromId();
		}
		catch (PriorityUserException e ){
			assert(e.getMessage().equals("User has no tweets"));
		}
		
	}
	@Test
	public void testGetFriends(){
		List <User> userList;
		userList = ptUser.getFriendsOfUser(200);
		assertNotNull(userList);
	}

}
