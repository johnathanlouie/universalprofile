package socialmedia.googlePlus;

import java.util.Map;

/**
 * Code Skeleton for UCF ACM Social Media API Tutorial.
 *
 * @author Shane Chism (schism at acm dot org)
 */
class Main {

	/** A Sample Google+ User ID. Defaults to 117976156812233500456 (author). */
	public static final String DEFAULT_GOOGLEPLUS_UID = "117862007149936554018";
	
	public static void main( String[] args ) {

		
		// Google+ API Example.
		doGooglePlus();
		
		// Exit program.
		System.exit(0);
		
	}
	
	
	/**
	 * Load the Google+ API and query using the DEFAULT_GOOGLEPLUS_UID class variable.
	 */
	public static void doGooglePlus(){
		
		// Initialize the Google+ API.
		GooglePlusAPI gpAPI = new GooglePlusAPI();
		
		// Load our public user object.
		// DEFAULT_GOOGLEPLUS_UID is set in the beginning of this class as a constant.
		// Defaults to Shane Chism's Google+ page;
		// To change, replace DEFAULT_GOOGLEPLUS_UID with a string containing a Google+ User ID number.
		GP_PublicUser user = gpAPI.get( DEFAULT_GOOGLEPLUS_UID );
		
		// Output the data we've received from our API GET request!
		printHeader( "Google+ API Results" );
		System.out.println( "Google Plus Full Name: " + user.getDisplayName() );
		
		// The Google+ API only returns the full name, so to match the Facebook API let's go ahead and cut
		// the string off at the first occurrence of a space (the ' ' character).
		String userFirstName = user.getDisplayName().substring( 0, user.getDisplayName().indexOf(' ') );
		
		System.out.println( "See " + userFirstName + "'s Profile Here: " + user.getURL() + "\n" );
		System.out.println(user.getAboutMe());
                System.out.println(user.getGender()+" "+user.getPlacesLived());
                
		// Print out the user's publicly listed URLs.
		// (This is a good example of iterating through user data which contains multiple records)
		//for( Map<String,String> url : user.getURLs() )
		//	System.out.println( "One of " + userFirstName + "'s URLs: " + url.get( "value" ) );
		
	}
	
	/**
	 * Keepin' our code cool as a cucumber.
	 * 
	 * @param str Text to include inside the printed header block.
	 */
	public static void printHeader( String str ){
		System.out.println( "\n============== " + str + " ==============\n" );
	}
	
}