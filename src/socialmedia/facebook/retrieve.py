from pymongo import MongoClient
from fbcrawler import FacebookCrawler
from fbscrapper import FacebookProfileScrapper
from fbscrapper import Page
import re
import gc
import sys



#read marker from file 
# marker denotes the starting uid to start retreiving profile from
marker = open("marker", 'r+')
start = int(marker.read())
end = start+5
marker.seek(0)
marker.write(str(end))
marker.close()


# create uid list from start to end
uid=[]
for i in range(start,end):
  uid.append(i)
print uid

crawler = FacebookCrawler()
crawler.setUidList(uid)
pages = crawler.start_crawl()



