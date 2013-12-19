class TicTacToe(first : String) {

	val board = Array(" ", " ", " ", " ", " ", " ", " ", " ", " ")

	def fill(token : String, x : Int, y : Int) {
		board(x+y*3) = token
	}

	def render() {
		for(i <- 0 until 3) {
			print("|")
			for(j <- 0 until 3) {
				print(board(i+j*3))
				print("|")
			}
			println
		}
	}

	def draw() {
		var lines = List[List[(Int, Int)]]()
		for(i <- 0 until 3) {
			var row = List[(Int, Int)]()
			var col = List[(Int, Int)]()
			for(j <- 0 until 3) {
				row = row :+ (i, j)
				col = col :+ (j, i)
			}
			lines = lines :+ row
			lines = lines :+ col
		}
		var diag1 = List[(Int, Int)]()
		var diag2 = List[(Int, Int)]()
		for(k <- 0 until 3) {
			diag1 = diag1 :+ (k, k)
			diag2 = diag2 :+ (k, 2-k)
		}
		lines = lines :+ diag1
		lines = lines :+ diag2

		var countBlank = 0
		var countO = 0
		var countX = 0
		for(k <- 0 until 9) {
			var token = board(k)
			if (token == " ") {
				countBlank = countBlank + 1
			} else if (token == "O") {
				countO = countO + 1
			} else {
				countX = countX + 1
			}
		}

		var lineO = false
		var lineX = false
		for(i <- 0 until lines.length) {
			var line = lines(i)
			var lineTokens = List[String]()
			for (j <- 0 until 3) {
				var token = board(line(j)._1 + line(j)._2 * 3)
				lineTokens = lineTokens :+ token
			}
			var a = lineTokens(0)
			var b = lineTokens(1)
			var c = lineTokens(2)
			if (a == b && b == c) {
				if (a == "O") {
					lineO = true
				} else if (b == "X") {
					lineX = true
				}
			}
		}
		if (lineO && lineX) {
			println("Winner is " + first)
		} else if (lineO) {
			println("Winner is O")
		} else if (lineX) {
			println("Winner is X")
		} else if (countBlank == 0) {
			println("Tired")
		} else if (countBlank == 9) {
			println("New: " + first)
		} else if (countO == countX) {
			println("Continue: " + first)
		} else if (countO > countX) {
			println("Continue: X")
		} else {
			println("Continue: O")
		}
	}
}

val ttt = new TicTacToe("O")
ttt.render
ttt.draw
ttt.fill("O", 0, 2)
ttt.render
ttt.draw
ttt.fill("X", 1, 1)
ttt.render
ttt.draw
ttt.fill("O", 1, 2)
ttt.render
ttt.draw
ttt.fill("X", 2, 2)
ttt.render
ttt.draw
ttt.fill("O", 2, 0)
ttt.render
ttt.draw
ttt.fill("X", 0, 0)
ttt.render
ttt.draw

ttt.fill("X", 0, 1)
ttt.render
ttt.draw
ttt.fill("X", 2, 1)
ttt.render
ttt.draw
ttt.fill("O", 2, 2)
ttt.render
ttt.draw

ttt.fill("O", 1, 1)
ttt.render
ttt.draw
ttt.fill("X", 1, 2)
ttt.render
ttt.draw
ttt.fill("X", 2, 0)
ttt.render
ttt.draw
ttt.fill("O", 1, 0)
ttt.render
ttt.draw