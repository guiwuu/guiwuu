Io 20110905
Io> slower := Object clone
==>  Object_0x25c51e0:

Io> faster := Object clone
==>  Object_0x25c6e30:

Io> slower start := method(wait(2); writeln("slowly"))
==> method(
    wait(2); writeln("slowly")
)
Io> faster start := method(wait(1); writeln("quickly"))
==> method(
    wait(1); writeln("quickly")
)
Io> slower start; faster start
slowly
quickly
==> nil
Io> slower @@start; faster @@start; wait(3)
quickly
slowly
==> nil
Io> exit