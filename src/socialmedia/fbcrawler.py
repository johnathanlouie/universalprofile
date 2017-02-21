from ghost import Ghost, Session



class FacebookCrawler(object):
    
    def __init__(self):
        self.pages = []
        self.ghost = Ghost()
        self.USERAGENT = "Mozilla/5.0 (Ubuntu; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0"


    def __session_handle(self):
       session =""
       with self.ghost.start():
          session = Session(self.ghost, download_images=False, display=False, user_agent=self.USERAGENT)
          #print session.display
          #exit()
          page, rs = session.open("https://m.facebook.com/login.php", timeout=120)
          assert page.http_status == 200

          session.evaluate("""
               document.querySelector('input[name="email"]').value = 'sashi_thapaliya@hotmail.com';
               document.querySelector('input[name="pass"]').value = 'Myname123$';
               """)

          session.evaluate("""document.querySelector('input[name="login"]').click();""",
                 expect_loading=True)
       return session


    #@return facebook profile pages of the users with the uid in the uidList
    # 	Specefically returns about page along with the contact basic tab open
    def profileAbout(self, uidList):
          session = self.__session_handle()
          #print session
          for uid in uidList:
                  page, rs = session.open("https://www.facebook.com/"+uid)
                  session.wait_for_page_loaded()
                  page, resources = session.evaluate("""document.querySelector('[data-tab-key="about"]').click();""", expect_loading=True)
                  session.wait_for_page_loaded()
                  page, resources = session.evaluate("""document.querySelector('[data-testid="nav_contact_basic"]').click();""", expect_loading=True)
                  session.wait_for_page_loaded()
                  self.pages.append(session.content.encode("UTF-8"))
          return self.pages
