Matrix := Object clone
Matrix dim := method(x, y,
  self datas := list()
  self rowNum := x
  self columnNum := y
  for(i, 1, y,
    column := list() 
    for(j, 1, x, 
      column push(nil)
    )
    datas push(column)
  )
)
Matrix set := method(x, y, value,
  column := datas at(y)
  column atPut(x, value)
)
Matrix get := method(x, y,
  column := datas at(y)
  column at(x)
)
Matrix inverse := method(
  x := self rowNum
  y := self columnNum
  inversed := Matrix clone
  inversed dim(y, x)
  for(i,0,x-1,
    for(j,0,y-1,
      inversed set(j, i, self get(i, j))
    )
  )
  return inversed
)
Matrix writeFile := method(name,
  f := File clone
  f open(name)
  x := self rowNum
  y := self columnNum
  for(i,0,x-1,
    for(j,0,y-1,
      data := self get(i,j)
      if(data != nil)then(
        f write(data asString)
      )
      f write(",")
    )
    f write("\n")
  )
  f close
)
Matrix readFile := method(name,
  f := File clone
  f openForReading(name)
  datas := list()
  f foreachLine(line,
    row := list()
    splits := line split(",")
    splits foreach(split, row push(split))
    datas push(row)
  )
  x := datas size 
  y := datas at(0) size
  self dim(x, y)
  for(i, 0, x-1,
    for(j, 0, y-1,
      value := datas at(i) at(j)
      self set(i,j,value)
    )
  )
)

//5
matrix := Matrix clone
matrix dim(3,2)
matrix set(2,1,"99")
matrix get(2,1) println

//6
inversed := matrix inverse
(matrix get(2,1) == inversed get(1,2)) println

//7
matrix writeFile("matrix")
newmatrix := Matrix clone
newmatrix readFile("matrix")
(matrix get(2,1) == newmatrix get(2,1)) println
