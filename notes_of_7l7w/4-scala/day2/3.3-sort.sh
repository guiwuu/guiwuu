$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_21).
Type in expressions to have them evaluated.
Type :help for more information.

scala> val words = List("peg", "al", "bud", "kelly")
words: List[String] = List(peg, al, bud, kelly)

scala> words.count(word => word.size > 2)
res0: Int = 3

scala> words.filter(word => word.size > 2)
res1: List[String] = List(peg, bud, kelly)

scala> words.map(word => word.size)
res2: List[Int] = List(3, 2, 3, 5)

scala> words.forall(word => words.size > 1)
res5: Boolean = true

scala> words.exists(word => word.size > 4)
res6: Boolean = true

scala> words.exists(word => word.size > 5)
res7: Boolean = false

scala> words.sortWith((s, t) => s.charAt(0).toLower < t.charAt(0).toLower)
res16: List[String] = List(al, bud, kelly, peg)

scala> words.sortWith((s, t) => s.size < t.size)
res17: List[String] = List(al, peg, bud, kelly)

scala> 