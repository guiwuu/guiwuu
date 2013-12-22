daijun@daijun-x64 ~
$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_21).
Type in expressions to have them evaluated.
Type :help for more information.

scala> val ordinals = Map(0 -> "zero", 1 -> "one", 2 -> "two")
ordinals: scala.collection.immutable.Map[Int,String] = Map(0 -> zero, 1 -> one, 2 -> two)

scala> ordinals(2)
res0: String = two

scala> import scala.collection.mutable.HashMap
import scala.collection.mutable.HashMap

scala> val map = new HashMap[Int, String]
map: scala.collection.mutable.HashMap[Int,String] = Map()

scala> map += 4 -> "four"
res1: map.type = Map(4 -> four)

scala> map += 8 -> "eight"
res2: map.type = Map(8 -> eight, 4 -> four)

scala> map
res3: scala.collection.mutable.HashMap[Int,String] = Map(8 -> eight, 4 -> four)

scala> map += "zero" -> 8
<console>:10: error: type mismatch;
 found   : (String, Int)
 required: (Int, String)
              map += "zero" -> 8
                            ^

scala> 