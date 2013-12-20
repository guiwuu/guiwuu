class TicTacToe(first : String) {

	val board = Array(" ", " ", " ", " ", " ", " ", " ", " ", " ")

	def fill(token : String, x : Int, y : Int) = {
		board(x+y*3) = token
	}

	def player() : String = {
		var countO = 0
		var countX = 0
		for(k <- 0 until 9) {
			var token = board(k)
			if (token == "O") {
				countO = countO + 1
			} else if (token == "X") {
				countX = countX + 1
			}
		}

		if (countO == countX) {
			return first
		} else if (countO > countX) {
			return "X"
		} else {
			return "O"
		}
	}

	def render() = {
		for(i <- 0 until 3) {
			print("|")
			for(j <- 0 until 3) {
				print(board(i+j*3))
				print("|")
			}
			println
		}
		println(draw)
	}

	def draw() : String = {
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
		for(k <- 0 until 9) {
			var token = board(k)
			if (token == " ") {
				countBlank = countBlank + 1
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
			return "Winner is " + first
		} else if (lineO) {
			return "Winner is O"
		} else if (lineX) {
			return "Winner is X"
		} else if (countBlank == 0) {
			return "Tired"
		} else if (countBlank == 9) {
			return "New: " + first
		} else {
			return "Continue: " + player
		}
	}
}

val ttt = new TicTacToe("O")
ttt.render
ttt.fill("O", 0, 2)
ttt.render
ttt.fill("X", 1, 1)
ttt.render
ttt.fill("O", 1, 2)
ttt.render
ttt.fill("X", 2, 2)
ttt.render
ttt.fill("O", 2, 0)
ttt.render
ttt.fill("X", 0, 0)
ttt.render

ttt.fill("X", 0, 1)
ttt.render
ttt.fill("X", 2, 1)
ttt.render
ttt.fill("O", 2, 2)
ttt.render

ttt.fill("O", 1, 1)
ttt.render
ttt.fill("X", 1, 2)
ttt.render
ttt.fill("X", 2, 0)
ttt.render
ttt.fill("O", 1, 0)
ttt.render

class Game() {
	var first = ""
	var second = ""
	var ttt = new TicTacToe("")
	var x = 0
	var y = 0
	
	def start() = {
		println("Please choose who to play first: X or O?")
		first = readLine
		ttt = new TicTacToe(first)
		if (first == "X") {
			second = "O"
		} else {
			second = "X"
		}
		ttt.render
	}

	def play() = {
		println("Please input where to fill, x=?")
		x = readInt
		println("Please input where to fill, y=?")
		y = readInt
		ttt.fill(ttt.player, x, y)
		ttt.render
	}

	def finished() : Boolean = {
		val draw = ttt.draw
		if (draw == "Tired" || draw.startsWith("Winner")) {
			return true
		} else {
			return false
		}
	}

}

var game = new Game()
game.start
while(!game.finished) {
	game.play
}