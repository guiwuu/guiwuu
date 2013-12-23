diancang@diancang /cygdrive/d/codes/guiwuu/notes_of_7l7w/4-scala/day3
$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_21).
Type in expressions to have them evaluated.
Type :help for more information.

scala> val movies =
     | <movies>
     |   <movie genre="action">Pirates of the Caribbean</movie>
     |   <movie genre="fairytale">Edward Scissorhands</movie>
     | </movies>
movies: scala.xml.Elem = 
<movies>
  <movie genre="action">Pirates of the Caribbean</movie>
  <movie genre="fairytale">Edward Scissorhands</movie>
</movies>

scala> movies.text
res0: String = 
"
  Pirates of the Caribbean
  Edward Scissorhands
"

scala> val movieNodes = movies \ "movie"
movieNodes: scala.xml.NodeSeq = NodeSeq(<movie genre="action">Pirates of the Caribbean</movie>, <movie genre="fairytale">Edward Scissorhands</movie>)

scala> movieNodes(0)
res1: scala.xml.Node = <movie genre="action">Pirates of the Caribbean</movie>

scala> movieNodes(0) \ "@genre"
res2: scala.xml.NodeSeq = action

scala>