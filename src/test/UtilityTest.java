package test;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.utility.ConfigBuilder;
import com.utility.Utility;


public class UtilityTest{
	Twitter twitter;
	Date defaultConstructorDate;
	@Before
	public void setUp(){
		twitter = ConfigBuilder.getTwitter();
		defaultConstructorDate = new Date();

		
	}
	@Test
	public void testGetLastCreatedDateFromStatusList(){
		try {
			List <Status> statuses = twitter.getUserTimeline("twitter");
			Date receivedDate = Utility.getLastCreatedDateFromStatusList(statuses);
			System.out.println(receivedDate);
			assert(!receivedDate.equals(defaultConstructorDate) );
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//empty user
			List <Status> zeroStatuses = twitter.getUserTimeline("blenkar");
			Date receivedDate = Utility.getLastCreatedDateFromStatusList(zeroStatuses);
			assert (receivedDate.equals(defaultConstructorDate));
		}
		catch (TwitterException e)
		{}
		
	}
	

}
