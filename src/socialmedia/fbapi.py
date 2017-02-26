import facebook

# FacebookAPI class that wraps up the call to Facebook-sdk api

class FacebookAPI(object):

    def __init__(self):
       app_id="565706786893040"
       app_secret="73ed9c7a6bf22bde6c371199f3447ce7"
       self.oauth_access_token = "EAACEdEose0cBAFNqm7OR18XY43ZAJRETKwtoN06iF7x2gwKbIyaHSk9nmdHzX5EE37p739rkj9sl0Mpuu0vwU8icFQML6PkZBTKdBLJmymxCBGKVLA46kl3PZCrIfAOqc0pk8sayyX8QWpokbUcVrM9V8WKRNhtDZCqaTegnzsrKlKq2qRU1lxxoXQAoW0kZD"


   #@return list of profiles matching the query, with size up to limit
    def searchUser(self, query, limit=10):
       self.facebook_graph = facebook.GraphAPI(self.oauth_access_token)
       listP = self.facebook_graph.get_object("search?q="+query+"&type=user&access_token="+self.oauth_access_token+"&limit="+str(limit)+"&fields=link,name")
       print listP['data']
       return listP
