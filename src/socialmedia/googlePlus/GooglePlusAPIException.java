package socialmedia.googlePlus;

import java.util.Collection;
import java.util.Map;

import com.google.gson.Gson;

/**
 * Custom Exception to return a JSON encoded error message when dealing with the Google+ API.
 *
 * @author Shane Chism (schism at acm dot org)
 */
public class GooglePlusAPIException extends Exception {

	private String errType, errMessage;
	
	/**
	 * This deals with serialized components in Java. It does not directly relate to the purpose of this tutorial.
	 * For more information: http://java.dzone.com/articles/dont-ignore-serialversionuid.
	 */
	private static final long serialVersionUID = -4914754445188651128L;
	
	public GooglePlusAPIException( String response ){
		
		// Initialize Google's JSON encoder/decoder class (this is a utility, it's API independent)
		Gson gson = new Gson();
		
		try {
			
			// Attempt to parse the server response (JSON assumed) into our FBError class
			// -- Explanation:
			// An example error string: 
			// { "error": {  
			// 	"errors": [   {    "domain": "global",    "reason": "badRequest",    "message": "Bad Request"   }  ],
			//  "code": 400,  "message": "Bad Request" }
			// }
			// This setup is more compelx than the Facebook. We have a mixed grouping of objects, and the errors array is contained
			// within another array. We must create a data structure that matches the same format as the JSON string (see GPErrorContainer).
			// When we call the .get( KEY ) method, it is the same operation as calling myArray[<index>] on a standard numeric array.			
			GPErrorContainer requestError = gson.fromJson( response, GPErrorContainer.class );
			
			// Assign the appropriate map values to our exception class' variables
			errType = requestError.getErrors().get("reason");
			errMessage = requestError.getErrors().get( "message" );
			
		}catch( Exception e ){
			System.out.println( e.getMessage() );
			// Unable to parse the returned JSON string into the GPErrorContainer/GPError object
			errType		= 	"Unknown Error";
			errMessage	= 	"An unkown error has occured. Unable to process response from server: \n" + response;
		}
		
	}
	
	/**
	 * Shows the error message returned from the triggering malformed Google+ API request.
	 * Error displays in the command line.
	 */
	public void show(){
	    Main.printHeader( "[Google Plus API Error] " + this.errType );
		System.out.println( this.errMessage );
		System.exit( 1 );
	}
	
	/**
	 * Returns the error message returned from the triggering malformed Google+ API request.
	 *
	 * @return String containing the message returned
	 */
	public String getMessage(){
		return this.errMessage;
	}
	
	/**
	 * Returns the error type returned from the triggering malformed Google+ API request.
	 *
	 * @return String containing the error type returned
	 */
	public String getType(){
		return this.errType;
	}
	
	/**
	 * Container for the JSON parse of a server returned error. JSON elements in the array are parsed to this.
	 *
	 * @author Shane Chism (schism at acm dot org)
	 */
	private class GPErrorContainer { 
		private GPError error;
		public Map<String,String> getErrors(){ return error.errors.iterator().next(); }
		public String getCode(){ return error.code; }
		public String getMessage(){ return error.message; }
	}
	/**
	 * Contains the actual data elements that are returned as part of the JSON response.
	 *
	 * @author Shane Chism (schism at acm dot org)
	 */
	private class GPError {
		protected Collection<Map<String,String>> errors;
		protected String code;
		protected String message;
	}
	
}
