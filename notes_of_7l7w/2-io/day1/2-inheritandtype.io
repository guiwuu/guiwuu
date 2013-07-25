Io 20110905
Io> Vehicle := Object clone
==>  Vehicle_0x27251e0:
  type             = "Vehicle"

Io> Vehicle description := "Something to take you far away"
==> Something to take you far away
Io> Car := Vehicle clone
==>  Car_0x2728450:
  type             = "Car"

Io> Car slotNames
==> list(type)
Io> Car type
==> Car
Io> Car description
==> Something to take you far away
Io> ferrari := Car clone
==>  Car_0x26b8460:

Io> ferrari slotNames
==> list()
Io> ferrari type
==> Car
Io> Ferrari := Car clone
==>  Ferrari_0x26b5640:
  type             = "Ferrari"

Io> Ferrari type
==> Ferrari
Io> Ferrari slotNames
==> list(type)
Io> ferrari slotNames
==> list()
Io> exit
