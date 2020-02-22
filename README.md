# Universal Profile
Universal Profile is a search engine designed to find people, companies, and social groups more easily. The system crawls various social media platforms and combines each entity into a unified profile by several similarity metrics, which fleshes out profiles by filling in missing information gathered from other platforms.

# Installing Web Server
* Install Node.js.
* Install MongoDB.
```
cd <project home>/web/
npm install
```

# Installing Facebook Crawler
* Install Python 2.7.
* On Microsoft Windows, autopy has other dependencies which need to be installed independently.
```
pip install facebook-sdk
pip install beautifulsoup4
pip install pymongo
pip install autopy
pip install Ghost.py
```

# Installing Combiner
* Install Java 8 or later.
* Install Apache Maven.
```
cd <project home>/combiner/
mvn install
```

# Running Web Server
* Run MongoDB.
```
cd <project home>/web/
npm start
```
* You should be able to see "Server is listening on port <port number>".
* Launch your browser and enter "localhost:<port>" as the URL.
## Note For Linux
* Need to change node to nodejs
* Need to change (character) ` to (character) '

# Running Facebook Crawler
* Run MongoDB.
```
cd <project home>/facebook/
python fb.py
```

# Running Combiner
* Run MongoDB.
```
cd <project home>/combiner/
java -jar target/uniprofile-1.0.jar <source 1> <source 2> <destination>
```

# Running Google Crawler
* Run MongoDB.
```
cd <project home>/combiner/
java -cp target/uniprofile-1.0.jar socialmedia.googlePlus.GooglePlusExtractor
```
