Io 20110905
Io> true clone
==> true
Io> false clone
==> false
Io> nil clone
==> nil
Io> Highlander := Object clone
==>  Highlander_0x26db1f0:
  type             = "Highlander"

Io> Highlander clone := Highlander
==>  Highlander_0x26db1f0:
  clone            = Highlander_0x26db1f0
  type             = "Highlander"

Io> Highlander clone
==>  Highlander_0x26db1f0:
  clone            = Highlander_0x26db1f0
  type             = "Highlander"

Io> fred := Highlander clone
==>  Highlander_0x26db1f0:
  clone            = Highlander_0x26db1f0
  type             = "Highlander"

Io> mike := Highlander clone
==>  Highlander_0x26db1f0:
  clone            = Highlander_0x26db1f0
  type             = "Highlander"

Io> fred == mike
==> true
Io> one := Object clone
==>  Object_0x270c4b0:

Io> two := Object clone
==>  Object_0x270e0d0:

Io> one == two
==> false
Io> Object clone := "hosed"
==>
IOVM:
        Received signal. Setting interrupt flag.

  current coroutine

Exception: CFunction defined for type Object but called on type Sequence


Coroutine Exception loop detected
