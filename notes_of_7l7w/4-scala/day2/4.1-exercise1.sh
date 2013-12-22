daijun@daijun-x64 /cygdrive/d/codes/guiwuu/notes_of_7l7w/4-scala/day2
$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_21).
Type in expressions to have them evaluated.
Type :help for more information.

scala> val sentence = List("you", "rise", "me", "up")
sentence: List[String] = List(you, rise, me, up)

scala> (0 /: sentence){(sum, word) => sum + word.length}
res0: Int = 11

scala> sentence.foldLeft(0)((sum, word) => sum + word.length)
res1: Int = 11

scala> 