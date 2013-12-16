daijun@daijun-x64 ~
$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_21).
Type in expressions to have them evaluated.
Type :help for more information.

scala> println("Hello, surreal world")
Hello, surreal world

scala> 1+1
res1: Int = 2

scala> (1). + (1)
res2: Int = 2

scala> 5 + 4 * 3
res3: Int = 17

scala> 5.+(4.*(3))
warning: there were 2 deprecation warning(s); re-run with -deprecation for details
res4: Double = 17.0

scala> (5).+((4).*(3))
res5: Int = 17

scala> "abc".size
res6: Int = 3

scala> "abc" + 4
res7: String = abc4

scala> 4 + "abc"
res8: String = 4abc

scala> 4 + "1.0"
res9: String = 41.0

scala> 4 * "abc"
<console>:8: error: overloaded method value * with alternatives:
  (x: Double)Double <and>
  (x: Float)Float <and>
  (x: Long)Long <and>
  (x: Int)Int <and>
  (x: Char)Int <and>
  (x: Short)Int <and>
  (x: Byte)Int
 cannot be applied to (String)
              4 * "abc"
                ^

scala> 