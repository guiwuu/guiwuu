import xml._
import scala.io._
import scala.actors._
import Actor._

object PageLoader {
	val reg1 = """<a[^>]*>[^<]*</a>""".r
	val reg2 = """http://[^/]*/?""".r
	val reg3 = """href=['"][^'"]*['"]""".r

	def parse(url: String, level: Int): (Int, List[String]) = {
		var links = List[String]()
		try {
			val html = scala.io.Source.fromURL(url)(Codec.UTF8).mkString
			reg1.findAllMatchIn(html).foreach { anchor =>
				try {
					//val xml = XML.loadString(anchor.toString)
					//val href = (xml \ "@href").toString
					val href = reg3.findFirstIn(anchor.toString).mkString.replaceAll("'", "").replaceAll("\"", "").replaceAll("href=", "")
					if (!href.startsWith("#") && !href.startsWith("https://")) {
						var link = href
						if (href.startsWith("http://")) {
							link = href
						} else if (href.startsWith("/")) {
							var rootPath = reg2.findFirstIn(url).mkString
							if (rootPath.endsWith("/")) {
								rootPath = rootPath.substring(0, rootPath.length-1)
							}
							link = rootPath + href
						} else {
							if (url.endsWith("/")) {
								link = url + href
							} else {
								link = url + "/" + href
							}
						}
						links :+= link
					}
				} catch {
					case e: Exception => {}			
				}
			}
			return (html.length, links)
		} catch {
			case e: Exception => {}
			return (0, links)
		}
	}
}


def timeMethod(method: () => Unit) = {
	val start = System.nanoTime
	method()
	val end = System.nanoTime
	println("Method took " + (end - start)/1000000000.0 + "seconds.")
}

def getPageSizeConcurrently() = {
	val caller = self
	for(url <- urls) {
		actor {
			caller ! (url, PageLoader.parse(url, 0))
		}
	}
	for(i <- 1 to urls.size) {
		receive {
			case (a, (b, c)) => {
				val url = a.asInstanceOf[String]	
				val size = b.asInstanceOf[Int]
				val links = c.asInstanceOf[List[String]]
				var secondLinkCount = countSecondLinkConcurrently(links)
				println(url + ", Size: " + size + ", 1st Link Count: " + links.length + ", 2nd Link Count: " + secondLinkCount)
			}
		}
	}
}

def countSecondLinkConcurrently(links : List[String]) : Int = {
	val caller = self
	for(url <- links) {
		actor {
			caller ! (url, PageLoader.parse(url, 1))
		}
	}
	val secondLinkCount = new java.util.concurrent.atomic.AtomicInteger()
	for(i <- 1 to links.size) {
		receive {
			case (a, (b, c)) => {
				secondLinkCount.getAndAdd(c.asInstanceOf[List[String]].length)
			}
		}
	}
	return secondLinkCount.intValue
}

val urls = List("http://www.scala-lang.org")

println("Running...")
timeMethod{ 
	getPageSizeConcurrently
}