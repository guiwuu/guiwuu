squareBrackets := method(
  l := List clone
  call message arguments foreach(arg,
    e := doMessage(arg)
    l push(e)
  )
  l
)


s := File with("5-exercise2.txt") openForReading contents
numbers := doString(s)
numbers println
numbers foreach(e,
  e type println
)