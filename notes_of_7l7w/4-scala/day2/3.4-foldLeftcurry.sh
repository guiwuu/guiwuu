daijun@daijun-x64 /cygdrive/d/codes/guiwuu/notes_of_7l7w/4-scala/day2
$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_21).
Type in expressions to have them evaluated.
Type :help for more information.

scala> val list = List(1, 2, 3)
list: List[Int] = List(1, 2, 3)

scala> val sum = (0 /: list){(sum, i) => sum + i}
sum: Int = 6

scala> list.foldLeft(0)((sum, value) => sum + value)
res0: Int = 6

scala> 