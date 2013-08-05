fib_recursive := method(num,
  if(num == 1, 1, 
    if(num == 2, 1, 
      fib_recursive(num-1) + fib_recursive(num-2)
    )
  )
)

fib_for := method(num,
  a := 0
  b := 0
  for(i, 1, num,
    c := if(i == 1, 1, 
      if(i == 2, 1, a + b)
    )
    a := b
    b := c
  ) 
)

"num\trecursive\tfor" println
for (i, 1, 10, 
  "\t" print
  i print
  "\t" print
  fib_recursive(i) print
  "\t" print
  fib_for(i) println
)
