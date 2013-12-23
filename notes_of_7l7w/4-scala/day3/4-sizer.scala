import scala.io._
import scala.actors._
import Actor._

object PageLoader {
	def getPageSize(url: String) = Source.fromURL(url)(Codec.UTF8).mkString.length
}

val urls = List("http://www.apple.com", "http://www.yahoo.com", "http://www.amazon.cn", "http://www.weibo.com",
	"http://www.ask.com", "http://www.stackoverflow.com", "http://www.zhihu.com", "http://www.douban.com",
	"http://www.weather.com", "http://www.apache.org", "http://www.foxnews.com", "http://www.wikipedia.com")

def timeMethod(method: () => Unit) = {
	val start = System.nanoTime
	method()
	val end = System.nanoTime
	println("Method took " + (end - start)/1000000000.0 + "seconds.")
}

def getPageSizeSequentially() = {
	for(url <- urls) {
		println("Size for " + url + ": " + PageLoader.getPageSize(url))
	}
}

def getPageSizeSequentially2() = {
	val caller = self
	for(url <- urls) {
		caller ! (url, PageLoader.getPageSize(url))
	}
	for(i <- 1 to urls.size) {
		receive {
			case (url, size) => println("Size for " + url + ": " + size)
		}
	}
}

def getPageSizeConcurrently() = {
	val caller = self
	for(url <- urls) {
		actor {
			caller ! (url, PageLoader.getPageSize(url))
		}
	}
	for(i <- 1 to urls.size) {
		receive {
			case (url, size) => println("Size for " + url + ": " + size)
		}
	}
}


println("Sequential run:")
timeMethod{ getPageSizeSequentially }
println("Sequential2 run:")
timeMethod{ getPageSizeSequentially2 }
println("Concurrrent run")
timeMethod{ getPageSizeConcurrently }