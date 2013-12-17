daijun@daijun-x64 /cygdrive/d/codes/guiwuu/notes_of_7l7w/4-scala/day1
$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_21).
Type in expressions to have them evaluated.
Type :help for more information.

scala> var range = 0 until 10
range: scala.collection.immutable.Range = Range(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

scala> range.start
res0: Int = 0

scala> range.end
res1: Int = 10

scala> range.step
res2: Int = 1

scala> (0 to 10) by 5
res4: scala.collection.immutable.Range = Range(0, 5, 10)

scala> (0 to 10) by 6
res5: scala.collection.immutable.Range = Range(0, 6)

scala> (0 until 10 by 5)
res7: scala.collection.immutable.Range = Range(0, 5)

scala> val range = (10 until 0) by -1
range: scala.collection.immutable.Range = Range(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)

scala> val range = (10 until 0) 
range: scala.collection.immutable.Range = Range()

scala> val range = (0 to 10) 
range: scala.collection.immutable.Range.Inclusive = Range(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

scala> val range = 'a' to 'e'
range: scala.collection.immutable.NumericRange.Inclusive[Char] = NumericRange(a, b, c, d, e)

scala> val person = ("Elvis", "Presley")
person: (String, String) = (Elvis,Presley)

scala> person._1
res8: String = Elvis

scala> person._2
res9: String = Presley

scala> person._3
<console>:9: error: value _3 is not a member of (String, String)
              person._3
                     ^

scala> val (x, y) = (1, 2)
x: Int = 1
y: Int = 2

scala> val (a, b) = (1, 2, 3)
<console>:7: error: constructor cannot be instantiated to expected type;
 found   : (T1, T2)
 required: (Int, Int, Int)
       val (a, b) = (1, 2, 3)
           ^
<console>:7: error: recursive value x$1 needs type
       val (a, b) = (1, 2, 3)
            ^

scala> 