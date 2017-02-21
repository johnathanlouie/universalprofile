from fbapi import FacebookAPI
from fbcrawler import FacebookCrawler
from fbscrapper import FacebookProfileScrapper

class Facebook(object):

    def __init__(self):
        self.api = FacebookAPI()
        self.crawler = FacebookCrawler()

    def getProfiles(self, first_name="", last_name=""):
       uList = self.api.searchUser(first_name+"+"+last_name)
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
