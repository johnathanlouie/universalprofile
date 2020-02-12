from bs4 import BeautifulSoup
from bs4 import NavigableString
from bs4 import Tag
from bs4.diagnose import diagnose
import re

# FacebookProfileScrapper class that handles scrapping data out of Facebook User Profiles
#   Currently support scrapping data from About page, (contact basic tab) of User Profile
#


class FacebookProfileScrapper(object):

    def __init__(self):
        self.profiles = []
        self.bMain = {"class": "_4bl7 _pt5"}
        self.bFocus = {"class": "clearfix"}
        self.bFinal = {"class": "_50f4"}
        self.profile_attr = {"full_name", "email", "gender", "phone", "birthdate", "address"}

    def is_number(self, phone_number):
        if re.compile(r"(\d{3}[-\.\s]??\d{3}[-\.\s]??\d{4}|\(\d{3}\)\s*\d{3}[-\.\s]??\d{4}|\d{3}[-\.\s]??\d{4})").match(phone_number):
            return True
        return False

    def is_email(self, email):
        if re.compile(r"(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$)").match(email):
            return True
        return False

    def is_gender(self, g):
        if g == "Male" or g == "Female":
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

        # @return a profile from the profile page
        # Profile is a Dictionary/associate array with attributes as keys and values as values
    #
    # @param profile_page needs to be a user profile About page with contact basic tab
    #
    def scrapeAbout(self, profile_page):
        print "scrapeAbout()"
        if isinstance(profile_page, Page) is False:
            print "No Page Object"
            return None
        all_attr = []
        full_name = ""
        prof = None
        # diagnose(profile_page.content.encode("UTF-8"))
        #print profile_page.content
        #print "pre-soup-init()"
        #print profile_page.content
        soupA = BeautifulSoup(profile_page.content["contact"], "html.parser")
        soupB = BeautifulSoup(profile_page.content["living"], "html.parser")
        #print "post-soup-init()"
        try:
            #print name
            profile_attr = soupA.find_all("div", self.bMain)
            #print profile_attr
            for attr in profile_attr:
                sec = attr.find_all("div", self.bFocus)
                sec = sec[0].find("span", self.bFinal)
                #print type(bday.next)
                if isinstance(sec.next, NavigableString):
                    all_attr.append(sec.next)
                    #print bday.next
                elif isinstance(sec.next, Tag):
                    #print bday.next.get_text()
                    all_attr.append(sec.next.get_text())
            #print address
            # exit()
            name = soupA.find(id="fb-timeline-cover-name").next
            if len(name) > 1:
                prof = {}
                prof["full_name"] = name
                address = soupB.find(id="current_city").next.next.next.next.next.next.next.next.next.next.next
                prof["address"] = address
        except Exception as ex:
            print "Scrapper Exception"
            print ex
        except StandardError as er:
            print "Scrapper Error"
            print er

        if len(all_attr) > 0:
            pr = self.profile(all_attr)
            for attr in prof:
                pr[attr] = prof[attr]
            prof = pr

        return prof

        # @return list of profiles from the list of profile pages
        # Each Profile is a Dictionary/associate array with attributes as keys and values as values
    #
    # @param profile_pages needs to be list of profile_pages
    # 			Each profile page must be the About page with contact basic tab
    def scrapeAboutAll(self, profile_pages=[]):
        for pg in profile_pages:
            print "pg"
            cur_profile = self.scrapeAbout(pg)
            print "scraped"
            if cur_profile is not None:
                print "Adding profile to list"

                self.profiles.append(cur_profile)
            print "Added"
        return self.profiles

    @staticmethod
    def readAboutPages(num_pages=1):
        #print "static call"
        profiles = []
        for i in range(1, num_pages):
            scrapper = FacebookProfileScrapper()
            page = Page()
            htmlPage = open("pr_about_contact"+str(i), 'r')
            page.addSubPage("contact", htmlPage.read())
            htmlPage.close()
            htmlPage = open("pr_about_living"+str(i), 'r')
            page.addSubPage("living", htmlPage.read())
            htmlPage.close()
            profile = scrapper.scrapeAbout(page)
            if profile is not None:
                profiles.append(profile)
        return profiles


class Page(object):

    def __init__(self):
        self.content = {}

    def addSubPage(self, sec_name, content):
        self.content[sec_name] = content
