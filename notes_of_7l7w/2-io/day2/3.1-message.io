Io 20110905
Io> postOffice := Object clone
==>  Object_0x26851e0:

Io> postOffice packageSender := method(call sender)
==> method(
    call sender
)
Io> mailer := Object clone
==>  Object_0x2441688:

Io> mailer deliver := method(postOffice packageSender)
==> method(
    postOffice packageSender
)
Io> mailer deliver
==>  Object_0x2441688:
  deliver          = method(...)

Io> postOffice messageTarget := method(call target)
==> method(
    call target
)
Io> postOffice messageTarget
==>  Object_0x26851e0:
  messageTarget    = method(...)
  packageSender    = method(...)

Io> postOffice messageArgs := method(call message arguments)
==> method(
    call message arguments
)
Io> postOffice messageName := method(call message name)
==> method(
    call message name
)
Io> postOffice messageArgs("one", 2, :three)
==> list("one", 2, : three)
Io> postOffice messageName
==> messageName
Io> exit
