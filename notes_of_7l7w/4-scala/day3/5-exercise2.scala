import xml._
import scala.io._
import scala.actors._
import Actor._

object PageLoader {
	val reg = """<a[^>]*>[^<]*</a>""".r

	def parse(url: String, level: Int): (Int, Int) = {
		val html = scala.io.Source.fromURL(url)(Codec.UTF8).mkString
		var links = List[String]()
		reg.findAllMatchIn(html).foreach {anchor =>
			try {
				val xml = XML.loadString(anchor.toString)
				val link = (xml \ "@href").toString
				println(anchor)
				links :+= link
			} catch {
				case e: Exception => println(anchor)				
			}
		}
		return (html.length, links.length)
	}
}

val urls = List("http://www.apple.com")

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
			case (url, (size, links)) => {
				println(url + ", Size: " + size + ", Link Count: " + links)
			}
		}
	}
}

println("Running...")
timeMethod{ getPageSizeConcurrently }