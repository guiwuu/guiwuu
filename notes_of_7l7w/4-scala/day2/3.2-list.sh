daijun@daijun-x64 /cygdrive/d/codes/guiwuu/notes_of_7l7w/4-scala/day2
$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_21).
Type in expressions to have them evaluated.
Type :help for more information.

scala> val list = List("frodo","samwise", "pippin")
list: List[String] = List(frodo, samwise, pippin)

scala> list
res1: List[String] = List(frodo, samwise, pippin)

scala> list.isEmpty
res2: Boolean = false

scala> list.isEmpty
res3: Boolean = false

scala> Nil.isEmpty
res4: Boolean = true

scala> list.length
res5: Int = 3

scala> list.size
res6: Int = 3

scala> list.head
res7: String = frodo

scala> list.tail
res8: List[String] = List(samwise, pippin)

scala> list.last
res9: String = pippin

scala> list.init
res10: List[String] = List(frodo, samwise)

scala> list.reverse
res11: List[String] = List(pippin, samwise, frodo)

scala> list.drop(1)
res12: List[String] = List(samwise, pippin)

scala> list
res13: List[String] = List(frodo, samwise, pippin)

scala> list.drop(2)
res15: List[String] = List(pippin)

scala>