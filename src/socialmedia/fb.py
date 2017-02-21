from fbapi import FacebookAPI
from fbcrawler import FacebookCrawler
from fbscrapper import FacebookProfileScrapper

#This is the main object to extract user profile.
# Combines the use of both API and Crawler to get user data
# Functions provided as follows
#
#     //Extracts the limit (default limit=10) number of facebook profiles with the given first_name and last_name
#      getProfile(first_name="", last_name="", limit=10);
#    
#
#
class Facebook(object):

    def __init__(self):
        self.api = FacebookAPI()
        self.crawler = FacebookCrawler()

    def getProfiles(self, first_name="", last_name="", limit=10):
       uList = self.api.searchUser(first_name+"+"+last_name,limit)
       uidList = []
       for en in uList['data']:
         if first_name.lower()+" "+last_name.lower() == en['name'].lower():
           uidList.append(en['id'])

       pages = self.crawler.profileAbout(uidList)
       scrapper = FacebookProfileScrapper()
       prof = scrapper.scrapeAboutAll(pages)
       return prof

#just testing
fb = Facebook()
if isinstance(fb, Facebook): 
  print "Made fb"

print fb.getProfiles("Hamza","Salehbhai")
