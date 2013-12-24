import scala.io._
import scala.actors._
import Actor._

object PageLoader {
	val reg = """<a[^>]*>[^<]*</a>""".r

	def parse(url: String): (Int, Int) = {
		val html = Source.fromURL(url)(Codec.UTF8).mkString
		val matches = reg.findAllMatchIn(html)
		return (html.length, matches.length)
	}
}

val urls = List("http://www.apple.com", "http://www.yahoo.com", "http://www.amazon.cn", "http://www.weibo.com",
	"http://www.ask.com", "http://www.stackoverflow.com", "http://www.zhihu.com", "http://www.douban.com",
	"http://www.weather.com", "http://www.apache.org", "http://www.foxnews.com", "http://www.wikipedia.com", "http://www.scala-lang.org")

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
			caller ! (url, PageLoader.parse(url))
		}
	}
	for(i <- 1 to urls.size) {
		receive {
			case (url, (size, count)) => {
				println(url + ", Size: " + size + ", Link Count: " + count)
			}
		}
	}
}

println("Running...")
timeMethod{ getPageSizeConcurrently }