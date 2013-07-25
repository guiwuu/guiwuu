Io 20110905
Io> Vehicle := Object clone
==>  Vehicle_0x27b51e0:
  type             = "Vehicle"

Io> Vehicle description := "Something to take you far away"
==> Something to take you far away
Io> Car := Vehicle clone
==>  Car_0x27b8450:
  type             = "Car"

Io> ferrari := Car clone
==>  Car_0x2572c48:

Io> method("So, you've come for an argument." println)
==> method(
    "So, you've come for an argument." println
)
Io> method() type
==> Block
Io> Car drive := method("Vroom" println)
==> method(
    "Vroom" println
)
Io> ferrari drive
Vroom
==> Vroom
Io> ferrari getSlot("drive")
==> method(
    "Vroom" println
)
Io> ferrari getSlot("type")
==> Car
Io> ferrari proto
==>  Car_0x27b8450:
  drive            = method(...)
  type             = "Car"

Io> Car proto
==>  Vehicle_0x27b51e0:
  description      = "Something to take you far away"
  type             = "Vehicle"

Io> Lobby
==>  Object_0x666818:
  Car              = Car_0x27b8450
  Lobby            = Object_0x666818
  Protos           = Object_0x6667b8
  Vehicle          = Vehicle_0x27b51e0
  _                = Object_0x666818
  exit             = method(...)
  ferrari          = Car_0x2572c48
  forward          = method(...)
  set_             = method(...)

Io> exit
