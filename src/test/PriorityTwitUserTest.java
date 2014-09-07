package test;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.URLEntity;
import twitter4j.User;

import com.objects.PriorityTwitUserImp;
import com.utility.Utility;

public class PriorityTwitUserTest {
	protected class PriorityTwitDummyUser implements User{

		@Override
		public int compareTo(User o) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getAccessLevel() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public RateLimitStatus getRateLimitStatus() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getBiggerProfileImageURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getBiggerProfileImageURLHttps() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getCreatedAt() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public URLEntity[] getDescriptionURLEntities() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getFavouritesCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getFollowersCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getFriendsCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getId() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getLang() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getListedCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getMiniProfileImageURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getMiniProfileImageURLHttps() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getOriginalProfileImageURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getOriginalProfileImageURLHttps() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileBackgroundColor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileBackgroundImageURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileBackgroundImageUrlHttps() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileBannerIPadRetinaURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileBannerIPadURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileBannerMobileRetinaURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileBannerMobileURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileBannerRetinaURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileBannerURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileImageURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileImageURLHttps() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileLinkColor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileSidebarBorderColor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileSidebarFillColor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProfileTextColor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getScreenName() {
			// TODO Auto-generated method stub
			return "SpiritAang";
		}

		@Override
		public Status getStatus() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getStatusesCount() {
			// TODO Auto-generated method stub
			return 100;
		}

		@Override
		public String getTimeZone() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public URLEntity getURLEntity() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getUtcOffset() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isContributorsEnabled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isDefaultProfile() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isDefaultProfileImage() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isFollowRequestSent() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isGeoEnabled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isProfileBackgroundTiled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isProfileUseBackgroundImage() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isProtected() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isShowAllInlineMedia() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isTranslator() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isVerified() {
			// TODO Auto-generated method stub
			return false;
		}
		
		
	}

	// TODO finish Setup and testTweetRetrieval
	PriorityTwitUserImp ptUser;
	Twitter twitter;
	User user;
	@Before
	public void setUp() throws Exception {
		twitter = Utility.getValidTwitterObject();
		user = twitter.showUser("SpiritAang");
		ptUser = new PriorityTwitUserImp();
		ptUser.setUser(user);
		ptUser.setTwitter(twitter);
		
		
		
	
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	public void testTweetRetrieval(){
		List <String> returned = ptUser.retrieveTweetsInString(0);
	}

}
