Builder := Object clone
Builder deep := 0
Builder forward := method(
  self tab(deep)
  writeln("<", call message name, ">")
  self deep := deep + 1
  call message arguments foreach(arg,
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
Builder ul(
    li("Io"),
    li("Lua","Lol"),
    ul(
      li("JavaScript")
    )
)