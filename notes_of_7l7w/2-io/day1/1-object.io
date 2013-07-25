Io 20110905
Io> "Hi ho, Io" print
Hi ho, Io==> Hi ho, Io
Io> Vehicle := Object clone
==>  Vehicle_0x2746ae0:
  type             = "Vehicle"

Io> Vehicle description := "Something to take you places"
==> Something to take you places
Io> Vehicle description = "Something to take you far away"
==> Something to take you far away
Io> Vehicle nonexistingSlot = "This won't work"

  Exception: Slot nonexistingSlot not found. Must define slot using := operator
before updating.
  ---------
  message 'updateSlot' in 'Command Line' on line 1

Io> Vehicle description
==> Something to take you far away
Io> Vehicle slotNames
==> list(description, type)
Io> Vehicle type
==> Vehicle
Io> Object type
==> Object
Io> exit
