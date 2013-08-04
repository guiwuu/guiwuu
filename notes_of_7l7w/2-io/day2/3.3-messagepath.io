Io 20110905
Io> unless := method(
...   (call sender doMessage(call message argAt(0))) ifFalse(
...   call sender doMessage(call message argAt(1))) ifTrue(
...   call sender doMessage(call message argAt(2)))
... )
==> method(
    (call sender doMessage(call message argAt(0))) ifFalse(call sender doMessage
(call message argAt(1))) ifTrue(call sender doMessage(call message argAt(2)))
)
Io> westley := Object clone
==>  Object_0x254fb78:

Io> westley trueLove := true
==> true
Io> westley princessButtercup unless(trueLove, ("It is false" println), ("It is
true" println))

  Exception: Object does not respond to 'princessButtercup'
  ---------
  Object princessButtercup             Command Line 1

Io> westley princessButtercup := method(call sender)
==> method(
    call sender
)
Io> westley princessButtercup unless(trueLove, ("It is false" println), ("It is
true" println))

  Exception: Object does not respond to 'trueLove'
  ---------
  Object trueLove                      Command Line 1
  Object unless                        Command Line 1

Io> westley slotNames
==> list(princessButtercup, trueLove)
Io> westley princessButtercup unless(westley trueLove, ("It is false" println),
("It is true" println))
It is true
==> true
Io> exit
