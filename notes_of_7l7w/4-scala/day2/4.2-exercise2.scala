trait Censor{
	val dirtyDict = Map("Pucky" -> "Shoot", "Beans" -> "Darn")
	def convert(word: String) : String = {
		if (dirtyDict.contains(word)) {
			return dirtyDict(word)
		} else {
			return word
		}
	}
}

class CensorTester extends Object with Censor

val tester = new CensorTester()
println(tester.convert("Pucky"))
println(tester.convert("Beans"))
println(tester.convert("Nonexist"))