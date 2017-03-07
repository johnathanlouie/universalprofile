from fbapi import FacebookAPI
from fbcrawler import FacebookCrawler
from fbscrapper import FacebookProfileScrapper
import sys
import xml.etree.cElementTree as ET

#This is the main object to extract user profile.
# Combines the use of both API and Crawler to get user data
# Functions provided as follows:
#     //Extracts the limit (default limit=5) number of facebook profiles with the given first_name and last_name
#      getProfile(first_name="", last_name="", limit=5);
#    
#
#
class Facebook(object):

    def __init__(self):
        self.api = FacebookAPI()
        self.crawler = FacebookCrawler()

    def getProfiles(self, first_name="", last_name="", limit=5):
       uList = self.api.searchUser(first_name+"+"+last_name, limit)
       print "here"
       uidList = []
       for en in uList['data']:
         if first_name.lower()+" "+last_name.lower() == en['name'].lower():
           uidList.append(en['id'])

       self.crawler.setUidList(uidList)
       pages = self.crawler.start_crawl()
       prof = FacebookProfileScrapper.readAboutPages(len(pages)+1)
       return prof

fb = Facebook()
profiles = ""
if isinstance(fb, Facebook): 
  print "Made fb"

print 'Number of arguments:', len(sys.argv), 'arguments.'
print 'Argument List:', str(sys.argv)
if(len(sys.argv)>2):
  profiles = fb.getProfiles(sys.argv[1],sys.argv[2])
  print sys.argv[1]
else:
  profiles = fb.getProfiles("Hamza","Salehbhai")

print profiles
print "executed"

#profiles = [{'gender': u'Male', 'birthdate': u'January 17, 1989', 'phone': u'(415) 342-7816', 'full_name': #u'Hamza Salehbhai', 'address': u'Mill Valley, California', 'email': u'hamza8907@yahoo.com'}]

root = ET.Element("Profiles")
#write profile to an xml Person Format
for prof in profiles:
  doc = ET.SubElement(root, "Profile")
  fne = ET.SubElement(doc, "FirstName")
  lne = ET.SubElement(doc, "LastName")
  em = ET.SubElement(doc, "Emails")
  eme = ET.SubElement(em,"Email")
  ge = ET.SubElement(doc,"Gender")
  if "full_name" in prof:
    full_name =  prof["full_name"].split(" ")
    fne.text = full_name[0]
    lne.text = full_name[1]
  if "email" in prof:
    eme.text = prof["email"]
  if "gender" in prof:
    ge.text = prof["gender"]

tree = ET.ElementTree(root)
tree.write("profiles.xml")

