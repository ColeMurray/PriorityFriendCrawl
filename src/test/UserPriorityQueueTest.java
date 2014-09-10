package test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.objects.PriorityQueueTwitter;
import com.objects.PriorityTwitUser;

public class UserPriorityQueueTest {
	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUserPriorityQueue() {
		PriorityQueueTwitter pq = new PriorityQueueTwitter(1, new PriorityQueueTwitter.PQTComparator());
		Date userADate = new Date(2014,10,02);
		Date userBDate = new Date(2013,10,02);
		PriorityTwitUser a = new PriorityTwitUser();
							a.setLastRetrievedTweetDate(userADate);
		PriorityTwitUser b = new PriorityTwitUser();
							b.setLastRetrievedTweetDate(userBDate);
		pq.add(b);
		pq.add(a);
		assertEquals("Expected 2014/10/2",userADate, ((PriorityTwitUser) pq.peek()).getLastRetrievedTweetDate());
		pq.clear();
		pq.add(a);
		pq.add(b);
		assertEquals("Expected 2014/10/2",userADate, ((PriorityTwitUser) pq.peek()).getLastRetrievedTweetDate());
	}


}
