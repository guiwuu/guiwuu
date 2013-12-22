daijun@daijun-x64 /cygdrive/d/codes/guiwuu/notes_of_7l7w/4-scala/day2
$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_21).
Type in expressions to have them evaluated.
Type :help for more information.

scala> val list = List("frodo","samwise", "pippin")
list: List[String] = List(frodo, samwise, pippin)

scala> list.foreach(hobbit => println(hobbit))
frodo
samwise
pippin

scala> val hobbits = Set("frodo", "samwise", "pippin")
hobbits: scala.collection.immutable.Set[String] = Set(frodo, samwise, pippin)

scala> hobbits.foreach(hobbit => println(hobbit))
frodo
samwise
pippin

scala> val hobbits = Map("frodo" -> "hobbit", "samwise" -> "hobbit", "pippin" -> "hobbit")
hobbits: scala.collection.immutable.Map[String,String] = Map(frodo -> hobbit, samwise -> hobbit, pippin -> hobbit)

scala> hobbits.foreach(hobbit => println(hobbit))
(frodo,hobbit)
(samwise,hobbit)
(pippin,hobbit)

scala> hobbits.foreach(hobbit => println(hobbit._1))
frodo
samwise
pippin

scala> hobbits.foreach(hobbit => println(hobbit._2))
hobbit
hobbit
hobbit

scala> 