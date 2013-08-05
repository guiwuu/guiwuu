sum := method(matrix,
  num := 0
  matrix foreach(element,
    element foreach(e, num := num + e)
  )
)

a := list(list(1,1,1), list(1,1,1), list(1,1,1))
sum(a) println
