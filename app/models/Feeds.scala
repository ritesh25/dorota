package models

object Feeds {
  val current = Map(
    "Memeorandum" -> "http://www.memeorandum.com/feed.xml",
    "Google News" -> "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&output=rss",
    "CNN Top Stories" -> "http://rss.cnn.com/rss/cnn_topstories.rss",
    "TechCrunch" -> "http://feeds.feedburner.com/TechCrunch/",
    "Hacker News" -> "https://news.ycombinator.com/rss",
    "Gawker" -> "http://feeds.gawker.com/gawker/full",
    "Scala Reddit (/r/scala)" -> "http://www.reddit.com/r/scala.rss",
    "Pando Daily" -> "http://pandodaily.com.feedsportal.com/c/35141/f/650422/index.rss",
    "Guardian UK World News" -> "http://feeds.theguardian.com/theguardian/world/rss",
    "BBC - US and Canada News" -> "http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml",
    "Google News - Entertainment" -> "http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=e&output=rss"
  )
}

