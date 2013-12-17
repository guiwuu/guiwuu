def forLoop {
		println("for loop using Java-style iterration")
		for(i <- 0 until args.length) {
			println(args(i))
		}
}

forLoop