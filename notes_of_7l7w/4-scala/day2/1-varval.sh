daijun@daijun-x64 ~
$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_21).
Type in expressions to have them evaluated.
Type :help for more information.

scala> var mutable = "I am mutable"
mutable: String = I am mutable

scala> mutable = "Touch me, change me..."
mutable: String = Touch me, change me...

scala> val immutable = "I am not mutable"
immutable: String = I am not mutable

scala> immutable = "Can't touch this"
<console>:8: error: reassignment to val
       immutable = "Can't touch this"
                 ^

scala> 