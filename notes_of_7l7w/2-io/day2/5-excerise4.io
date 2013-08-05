List myAverage := method(
  sum := 0
  count := 0
  self foreach(e,
    if (e type != "Number", 
      Exception raise("Not all elements are numbers!") 
    )
    count := count + 1
    sum := sum + e
  )
  if (count == 0, 0, sum / count)
)

list() myAverage println
list(1,2,3) myAverage println
list(1,"2",3) myAverage println
