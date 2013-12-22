import scala.io.Source

trait Censor{
	def convert(word: String) : String = {
		if (dirtyDict.contains(word)) {
			return dirtyDict(word)
		} else {
			return word
		}
	}
}

class CensorTester(dirtyDict: Map[String, String]) extends Object with Censor

var dirtyDict = Map[String, String]()
if (args.length > 0) {
	var lineNo = 0
 	for (line <- Source.fromFile(args(0)).getLines) {
 		lineNo += 1
 		var splits = line.split(",")
 		if (splits.length != 2) {
 			Console.err.println("Warnning: parse line #" + lineNo + " failed: " + line) 
 		} else {
 			dirtyDict += splits(0) -> splits(1)
 		}
 	}
} else {
	Console.err.println("Please enter filename") 
}

val tester = new CensorTester(dirtyDict)
println(tester.convert("Pucky"))
println(tester.convert("Beans"))
println(tester.convert("Nonexist"))