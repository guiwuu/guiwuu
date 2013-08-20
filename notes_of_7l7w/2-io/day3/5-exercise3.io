Builder := Object clone
Builder deep := 0
Builder tab := method(j,
  "  " repeated(j)
)
Builder forward := method(
  write(tab(deep), "<", call message name)
  args := call message arguments
  arg0 := args first 
  if (arg0 asString findSeq("curlyBrackets") == 0,
     doString(arg0 asString) foreach(k, v, 
       write(" ", k, "=\"", v, "\"")
     )
     args removeAt(0)
  ) 
  writeln(">")
  self deep := deep + 1
  args foreach(arg, 
    content := self doMessage(arg)
    if(content type == "Sequence",
      writeln(tab(deep), content)
    ) 
  )
  self deep := deep - 1
  writeln(tab(deep), "</", call message name, ">")
)

OperatorTable addAssignOperator(":", "atPutNumber")
curlyBrackets := method(
  r := Map clone
  call message arguments foreach(arg,
    r doMessage(arg)
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
  chapter({"no" : 2, "title" : "Jerry"},
    "I am Jerry"
  )
  chapter("I am nobody")
)