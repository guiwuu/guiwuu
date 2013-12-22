daijun@daijun-x64 ~
$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_21).
Type in expressions to have them evaluated.
Type :help for more information.

scala> val animals = Set("lions", "tigers", "bears")
animals: scala.collection.immutable.Set[String] = Set(lions, tigers, bears)

scala> animals + "armadillos"
res0: scala.collection.immutable.Set[String] = Set(lions, tigers, bears, armadillos)

scala> animals + "tigers"
res1: scala.collection.immutable.Set[String] = Set(lions, tigers, bears)

scala> animals + Set("armadills", "raccoons")
<console>:9: error: type mismatch;
 found   : scala.collection.immutable.Set[String]
 required: String
              animals + Set("armadills", "raccoons")
                           ^

scala> animals ++ Set("armadills", "raccoons")
res3: scala.collection.immutable.Set[String] = Set(bears, tigers, lions, armadills, raccoons)

scala> animals -- Set("lions", "bears")
res4: scala.collection.immutable.Set[String] = Set(tigers)

scala> animals ** Set("armadillos", "raccoons", "lions", "tiggers")
<console>:9: error: value ** is not a member of scala.collection.immutable.Set[String]
              animals ** Set("armadillos", "raccoons", "lions", "tiggers")
                      ^

scala> animals & Set("armadillos", "raccoons", "lions", "tiggers")
res7: scala.collection.immutable.Set[String] = Set(lions)

scala> Set(1, 2, 3) == Set(3, 2, 1)
res8: Boolean = true

scala> List(1, 2, 3) == List(3, 2, 1)
res9: Boolean = false

scala> 