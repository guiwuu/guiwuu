daijun@daijun-x64 ~
$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_21).
Type in expressions to have them evaluated.
Type :help for more information.

scala> List(1, 2, 3)
res0: List[Int] = List(1, 2, 3)

scala> List("one", "two", "three")
res1: List[String] = List(one, two, three)

scala> List("one", "two", 3)
res2: List[Any] = List(one, two, 3)

scala> List("one", "two", 3)(2)
res3: Any = 3

scala> List("one", "two", 3)(4)
java.lang.IndexOutOfBoundsException: 4
        at scala.collection.LinearSeqOptimized$class.apply(LinearSeqOptimized.scala:52)
        at scala.collection.immutable.List.apply(List.scala:84)
        at .<init>(<console>:8)
        at .<clinit>(<console>)
        at .<init>(<console>:7)
        at .<clinit>(<console>)
        at $print(<console>)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:601)
        at scala.tools.nsc.interpreter.IMain$ReadEvalPrint.call(IMain.scala:734)
        at scala.tools.nsc.interpreter.IMain$Request.loadAndRun(IMain.scala:983)
        at scala.tools.nsc.interpreter.IMain.loadAndRunReq$1(IMain.scala:573)
        at scala.tools.nsc.interpreter.IMain.interpret(IMain.scala:604)
        at scala.tools.nsc.interpreter.IMain.interpret(IMain.scala:568)
        at scala.tools.nsc.interpreter.ILoop.reallyInterpret$1(ILoop.scala:756)
        at scala.tools.nsc.interpreter.ILoop.interpretStartingWith(ILoop.scala:801)
        at scala.tools.nsc.interpreter.ILoop.command(ILoop.scala:713)
        at scala.tools.nsc.interpreter.ILoop.processLine$1(ILoop.scala:577)
        at scala.tools.nsc.interpreter.ILoop.innerLoop$1(ILoop.scala:584)
        at scala.tools.nsc.interpreter.ILoop.loop(ILoop.scala:587)
        at scala.tools.nsc.interpreter.ILoop$$anonfun$process$1.apply$mcZ$sp(ILoop.scala:878)
        at scala.tools.nsc.interpreter.ILoop$$anonfun$process$1.apply(ILoop.scala:833)
        at scala.tools.nsc.interpreter.ILoop$$anonfun$process$1.apply(ILoop.scala:833)
        at scala.tools.nsc.util.ScalaClassLoader$.savingContextLoader(ScalaClassLoader.scala:135)
        at scala.tools.nsc.interpreter.ILoop.process(ILoop.scala:833)
        at scala.tools.nsc.MainGenericRunner.runTarget$1(MainGenericRunner.scala:83)
        at scala.tools.nsc.MainGenericRunner.process(MainGenericRunner.scala:96)
        at scala.tools.nsc.MainGenericRunner$.main(MainGenericRunner.scala:105)
        at scala.tools.nsc.MainGenericRunner.main(MainGenericRunner.scala)


scala> List("one", "two", 3)(-1)
java.lang.IndexOutOfBoundsException: -1
        at scala.collection.LinearSeqOptimized$class.apply(LinearSeqOptimized.scala:52)
        at scala.collection.immutable.List.apply(List.scala:84)
        at .<init>(<console>:8)
        at .<clinit>(<console>)
        at .<init>(<console>:7)
        at .<clinit>(<console>)
        at $print(<console>)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:601)
        at scala.tools.nsc.interpreter.IMain$ReadEvalPrint.call(IMain.scala:734)
        at scala.tools.nsc.interpreter.IMain$Request.loadAndRun(IMain.scala:983)
        at scala.tools.nsc.interpreter.IMain.loadAndRunReq$1(IMain.scala:573)
        at scala.tools.nsc.interpreter.IMain.interpret(IMain.scala:604)
        at scala.tools.nsc.interpreter.IMain.interpret(IMain.scala:568)
        at scala.tools.nsc.interpreter.ILoop.reallyInterpret$1(ILoop.scala:756)
        at scala.tools.nsc.interpreter.ILoop.interpretStartingWith(ILoop.scala:801)
        at scala.tools.nsc.interpreter.ILoop.command(ILoop.scala:713)
        at scala.tools.nsc.interpreter.ILoop.processLine$1(ILoop.scala:577)
        at scala.tools.nsc.interpreter.ILoop.innerLoop$1(ILoop.scala:584)
        at scala.tools.nsc.interpreter.ILoop.loop(ILoop.scala:587)
        at scala.tools.nsc.interpreter.ILoop$$anonfun$process$1.apply$mcZ$sp(ILoop.scala:878)
        at scala.tools.nsc.interpreter.ILoop$$anonfun$process$1.apply(ILoop.scala:833)
        at scala.tools.nsc.interpreter.ILoop$$anonfun$process$1.apply(ILoop.scala:833)
        at scala.tools.nsc.util.ScalaClassLoader$.savingContextLoader(ScalaClassLoader.scala:135)
        at scala.tools.nsc.interpreter.ILoop.process(ILoop.scala:833)
        at scala.tools.nsc.MainGenericRunner.runTarget$1(MainGenericRunner.scala:83)
        at scala.tools.nsc.MainGenericRunner.process(MainGenericRunner.scala:96)
        at scala.tools.nsc.MainGenericRunner$.main(MainGenericRunner.scala:105)
        at scala.tools.nsc.MainGenericRunner.main(MainGenericRunner.scala)


scala> Nil
res6: scala.collection.immutable.Nil.type = List()

scala> 