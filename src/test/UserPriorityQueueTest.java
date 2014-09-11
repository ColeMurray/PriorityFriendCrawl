package test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.objects.PriorityQueueTwitter;
import com.objects.PriorityTwitUser;
import com.objects.PriorityTwitUserImp;
import com.objects.PriorityQueueTwitter.PQTComparator;
import com.utility.ConfigBuilder;

public class UserPriorityQueueTest {

	@Before
	public void setUp() throws Exception {
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testUserPriorityQueue() {
		PriorityQueueTwitter pq = new PriorityQueueTwitter(1,
				new PriorityQueueTwitter.PQTComparator());
		Date userADate = new Date(2014, 10, 02);
		Date userBDate = new Date(2013, 10, 02);
		PriorityTwitUser a = new PriorityTwitUser();
		a.setLastRetrievedTweetDate(userADate);
		PriorityTwitUser b = new PriorityTwitUser();
		b.setLastRetrievedTweetDate(userBDate);
		pq.add(b);
		pq.add(a);
		assertEquals("Expected 2014/10/2", userADate,
				((PriorityTwitUser) pq.peek()).getLastRetrievedTweetDate());
		pq.clear();
		pq.add(a);
		pq.add(b);
		assertEquals("Expected 2014/10/2", userADate,
				((PriorityTwitUser) pq.peek()).getLastRetrievedTweetDate());
	}

	@Test
	public void testAddAllUserFriends() {
		PriorityQueueTwitter pq;
		PriorityTwitUserImp freshUser;
		Twitter twitter;
		User freshTwitUser = null;

		twitter = ConfigBuilder.getTwitter();
		try {
			freshTwitUser = twitter.showUser("SpiritAang");
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		pq = new PriorityQueueTwitter(1, new PQTComparator());
		freshUser = new PriorityTwitUserImp(ConfigBuilder.getTwitter(),
				freshTwitUser);
		pq.add(freshUser);
		try {
			pq.addAll(freshUser.getFriendsInPriorityQueue());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		assert (pq.size() != 1);
	}

}
