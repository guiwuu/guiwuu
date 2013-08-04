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
Io> unless(1 == 2, write("One is not two\n"), write("one is two\n"))
One is not two
==> false
Io> exit
