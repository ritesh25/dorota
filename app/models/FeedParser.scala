package models

import scala.xml.XML
class FeedParser(xmlText: String) {
  def titles: Seq[String] = { 
    val root = XML.loadString(xmlText)
    val items = for (n <- (root \\ "item" )) yield n
    val titles = items.map( (_ \ "title" ) ).map(_.text)
    titles
  }
}
