import facebook

# FacebookAPI class that wraps up the call to Facebook-sdk api


class FacebookAPI(object):

    def __init__(self):
        app_id = "565706786893040"
        app_secret = "73ed9c7a6bf22bde6c371199f3447ce7"
        self.oauth_access_token = "EAACEdEose0cBAGiGeoe1RuFqIRZAqpnSiDbx7NZCzPh4ZB5R9ZCWQ7DetVJIsBPjk4dhdD89TGnvqFbUJPHuXHJbZBiEAtmIcWOMKZAxyOGQM2a4VPVxRlde4pZBdT1rkOHR5pZB8oeoh1MlU3THCZASZBPSzYr0sXpAHAIZAljZAhx81kBZAggY8t0pZCBwwW7nYn0ZAoZD"

    # @return list of profiles matching the query, with size up to limit

    def searchUser(self, query, limit=10):
        self.facebook_graph = facebook.GraphAPI(self.oauth_access_token)
        listP = self.facebook_graph.get_object("search?q="+query+"&type=user&access_token="+self.oauth_access_token+"&limit="+str(limit)+"&fields=link,name")
        print listP['data']
        return listP
