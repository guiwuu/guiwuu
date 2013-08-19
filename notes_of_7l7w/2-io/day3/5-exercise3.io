Builder := Object clone
Builder deep := 0
Builder forward := method(
  self tab(deep)
  write("<", call message name)
  args := call message arguments
  arg0 := self doMessage(args first)  
  if (arg0 type == "Map",
     arg0 foreach(k, v, 
       write(" ", k, "=\"", v, "\"")
     )
     args removeAt(0)
  ) 
  writeln(">")
  self deep := deep + 1
  args foreach(arg, 
    content := self doMessage(arg)
    if(content type == "Sequence",
      self tab(deep)
      writeln(content)
    ) 
  )
  self deep := deep - 1
  self tab(deep)
  writeln("</", call message name, ">")
)
Builder tab := method(j,
  for(i, 1, j, "  " print)
)

OperatorTable addAssignOperator(":", "atPutNumber")
curlyBrackets := method(
  r := Map clone
  call message arguments foreach(arg,
    write("--", arg type, "--")
  )
  r
)
Map atPutNumber := method(
  self atPut(
    call evalArgAt(0)  asMutable removePrefix("\"") removeSuffix("\""),
    call evalArgAt(1)
  )
)

Builder book({"author" : "Tate"},
  chapter({"no" : 1},
    "I am Tom"
  ),
  chapter({"no" : 2},
    "I am Jerry"
  )
)