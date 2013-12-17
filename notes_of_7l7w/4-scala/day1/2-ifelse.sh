daijun@daijun-x64 ~
$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_21).
Type in expressions to have them evaluated.
Type :help for more information.

scala> 5 < 6
res0: Boolean = true

scala> 5 <= 6
res1: Boolean = true

scala> 5 <= 2
res2: Boolean = false

scala> 5 >= 2
res3: Boolean = true

scala> 5 != 2 
res4: Boolean = true

scala> val a = 1
a: Int = 1

scala> val b = 2
b: Int = 2

scala> if ( b < a ) {
     | println("true")
     | } else {
     | println("false")
     | }
false

scala> Nil
res6: scala.collection.immutable.Nil.type = List()

scala> if(0) {println("true")}
<console>:8: error: type mismatch;
 found   : Int(0)
 required: Boolean
              if(0) {println("true")}
                 ^

scala> if(Nil) {println("true")}
<console>:8: error: type mismatch;
 found   : scala.collection.immutable.Nil.type
 required: Boolean
              if(Nil) {println("true")}
                 ^

scala> 