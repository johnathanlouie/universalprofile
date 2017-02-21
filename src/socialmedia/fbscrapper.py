from bs4 import BeautifulSoup
from bs4 import BeautifulSoup
from bs4 import NavigableString
from bs4 import Tag
import re


class FacebookProfileScrapper(object):
    
    def __init__(self):
        self.profiles=[]
        self.bMain={"class": "_4bl7 _pt5"}
        self.bFocus={"class":"clearfix"}
        self.bFinal = {"class":"_50f4"}
        self.profile_attr ={"email","gender","phone","birthdate", "Address"}

  
    def is_number(self,phone_number):
			if re.compile(r"(\d{3}[-\.\s]??\d{3}[-\.\s]??\d{4}|\(\d{3}\)\s*\d{3}[-\.\s]??\d{4}|\d{3}[-\.\s]??\d{4})").match(phone_number):
				 return True
			return False

    def is_email(self, email):
			if re.compile(r"(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$)").match(email):
				return True
			return False

    def is_gender(self, g):
			if g=="Male" or g=="Female":
				return True
			return False

    def is_birthdate(self, bd):
			if re.compile(r"(^[a-zA-Z-]{3,10}[ ]?[0-9-]{1,2}[,]?[ ]?[0-9-]{1,4}$)").match(bd):
				return True
			return False

    def profile(self, all_attr):
       profile = {}
       for at in self.profile_attr:
         profile[at] = ""
       for attr in all_attr:
         if self.is_number(attr):
            profile["phone"] = attr
         elif self.is_email(attr):
            profile["email"] = attr
         elif self.is_gender(attr):
            profile["gender"] = attr
         elif self.is_birthdate(attr):
            profile["birthdate"] = attr 
       return profile

    def scrapeAbout(self, profile_page):
        all_attr = []
        soup = BeautifulSoup(profile_page, 'html.parser')
        profile_attr  = soup.find_all("div",self.bMain)
        for attr in profile_attr:
            sec = attr.find_all("div",self.bFocus)
            sec = sec[0].find("span",self.bFinal)
            #print type(bday.next)
            if isinstance(sec.next, NavigableString):
              all_attr.append(sec.next)
              #print bday.next
            elif isinstance(sec.next, Tag):
              #print bday.next.get_text()
              all_attr.append(sec.next.get_text())
        return self.profile(all_attr)

    def scrapeAboutAll(self, profile_pages=[]):
       for pg in profile_pages:
          self.profiles.append(self.scrapeAbout(pg))
       return self.profiles
