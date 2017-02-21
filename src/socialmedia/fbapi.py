import facebook


# FacebookAPI class that wraps up the call to Facebook-sdk api
#
class FacebookAPI(object):

    def __init__(self):
       app_id="565706786893040"
       app_secret="73ed9c7a6bf22bde6c371199f3447ce7"
       self.oauth_access_token = "self.oauth_access_token = "EAACEdEose0cBAB5NVWXco5z8izzxi503ZAgVjLXMAKJlutfpOCUjuEMp7PUI4XKyvBPYwLBbdV1ZBpPjEZAIpYeKtkJbe06rvQo2mcxezL73ZAXWyrfjngzqwET9WZCZAjEvX6PElqFDT1U0RMgaqRLtfbIMalRcengRjYUCImFdYAYdYJi4nEtPfR0UHOBOEZD""


   #@return list of profiles matching the query, with size up to limit
    def searchUser(self, query, limit=10):
       self.facebook_graph = facebook.GraphAPI(self.oauth_access_token)
       listP = self.facebook_graph.get_object("search?q="+query+"&type=user&access_token="+self.oauth_access_token+"&limit="+str(limit)+"&fields=link,name")
       #print listP['data']
       return listP
