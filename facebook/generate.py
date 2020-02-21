import gc
import re

from pymongo import MongoClient

from fbcrawler import FacebookCrawler
from fbscrapper import FacebookProfileScrapper, Page


# read profile pages
profiles = FacebookProfileScrapper.readAboutPages(6)
print len(profiles)
# for p in profiles:
#  print p

# make database entry
client = MongoClient()
client = MongoClient('localhost', 27017)
db = client['fbprofile']
profile = db.profiles
new_result = profile.insert_many(profiles)
print('Multiple posts: {0}'.format(new_result.inserted_ids))
