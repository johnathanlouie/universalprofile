from ghost import Ghost, Session
from fbscrapper import Page
import re

# FacebookCrawler class to crawl facebook page
#   Currently supports crawling of User Profile About Page
# 
class FacebookCrawler(object):
    about_page = {"contact":"","overview":"","education":"","living":""}

    def __init__(self):
       self.pages = []
       self.ghost = Ghost()
       self.USERAGENT = "Chrome/54.0.2840.100"
       self.crawl_pages = {"profile":{"about":True,"timeline":False,"friends":False,"photos":False}}
   
    def setProfileCrawlPages(self, about=True, timeline=False, friends=False, photos=False):
       self.crawl_pages["profile"]["about"]=about
       self.crawl_pages["profile"]["timeline"] = timeline
       self.crawl_pages["profile"]["friends"]=friends
       self.crawl_pages["profile"]["photos"]=photos

    def setUidList(self, newUidList):
       self.uidList = newUidList

    def write_pages(self, pages):
      i=1
      #print pages
      for pg in pages:
        print i
        for sec in pg.content:
          htmlpage = open("pr_about_"+sec+str(i),'w')
          #print len(pg.content)
          #print sys.getsizeof(pg.content)
          htmlpage.write(pg.content[sec])
          htmlpage.close()
        i+=1

    def getProfileUrl(self,resources):
      url=""
      for r in resources:
        if re.compile(r"(^https://www.facebook.com/[a-zA-Z_.+-]+[0-9-]?$)").match(r.url):
          url = r.url
          break
      return url

    def __session_handle(self):
       session =""
       with self.ghost.start():
          session = Session(self.ghost, download_images=False, display=False, user_agent=self.USERAGENT)
       return session

    def __session_login(self, session):
       page, rs = session.open("https://m.facebook.com/login.php")
       assert page.http_status == 200
       session.evaluate("""
               document.querySelector('input[name="email"]').value = 'thesashi7@gmail.com';
               document.querySelector('input[name="pass"]').value = 'justpassword123';
               """)

       session.evaluate("""document.querySelector('input[name="login"]').click();""",
                 expect_loading=True)
       return session     

    def start_crawl(self):
       session = self.__session_handle()
       self.__session_login(session)
       pages = self.crawlProfile(session)
       self.write_pages(pages)
       return pages
       

    def getAboutSections(self, about_url):
       about={}
       for p in FacebookCrawler.about_page:
         about[p]=""
       about["contact"] = about_url+"/about?section=contact-info"
       about["overview"] = about_url+"/about?section=overview"
       about["education"] = about_url+"/about?section=education"
       about["living"] = about_url+"/about?section=living"
       return about

    def crawlProfile(self, session):
       for uid in self.uidList:
          base_url = "https://www.facebook.com/"+str(uid)
          self.crawlPage(session,base_url,True)
          base_url = self.getProfileUrl(self.curRs) 
          #print "base_url:"+base_url

          if self.crawl_pages["profile"]["about"]==True:
            crawledPage = self.crawlAbout(session, base_url)
            if isinstance(crawledPage,Page):
               self.pages.append(crawledPage)
       return self.pages 
             

    def crawlPage(self, session, url, resource=False):
       page, rs = session.open(url,timeout=1000,user_agent=self.USERAGENT)
       print "After Page request: "+url
       session.wait_for_page_loaded()
       session.sleep(1)
       session.evaluate("""window.scrollTo(0,100);""")
       session.sleep(1)
       session.wait_for_page_loaded()
       if resource==True:
          self.curRs = rs
       return session.content.encode("UTF-8")


    #@return facebook profile pages of the users with the uid in the uidList
    # 	Specefically returns about page along with the contact basic tab open
    def crawlAbout(self, session, base_url):
       crawledPage = None
       try:
           about  = self.getAboutSections(base_url)
           page = Page()
           #print about["contact"]
           crawledPage = self.crawlPage(session,about["contact"])
           page.addSubPage("contact",crawledPage)
           crawledPage = self.crawlPage(session,about["living"])
           page.addSubPage("living",crawledPage)
           crawledPage = page
       except Exception as ex:
          print "Exception"
          print ex
       except Error as er:
          print "Error"
          print er
       return crawledPage
          


