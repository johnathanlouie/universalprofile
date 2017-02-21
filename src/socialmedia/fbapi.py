import facebook



class FacebookAPI(object):

    def __init__(self):
       app_id="565706786893040"
       app_secret="73ed9c7a6bf22bde6c371199f3447ce7"
       self.oauth_access_token =     "EAAKZCSSrc6uEBAO0V49Qldwm4K3l7sTmgbFL63q3SOeS2f3Ys7zEEwNAjZBJGZBUj5UjcOKJackJ07uUH0YjTMWkggK1kVevdhQWrYcQ7aGonoonoWYb7GE0bE1ZANgsiKCFh91yxMAxpSFiOBaOYqKytEgL4KEZD"


   #@return list of profiles matching the query
    def searchUser(self, query):
       self.facebook_graph = facebook.GraphAPI(self.oauth_access_token)
       listP = self.facebook_graph.get_object("search?q="+query+"&type=user&access_token="+self.oauth_access_token+"&limit=10&fields=link,name")
       #print listP['data']
       return listP
