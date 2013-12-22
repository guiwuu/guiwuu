daijun@daijun-x64 ~
$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_21).
Type in expressions to have them evaluated.
Type :help for more information.

scala> def double(x: Int): Int = x * 2
double: (x: Int)Int

scala> double(4)
res1: Int = 8

scala> def double(x:Int):Int = {
     | x * 2
     | }
double: (x: Int)Int

scala> double(6)
res2: Int = 12

scala> 