Io 20110905
Io> elvis := Map clone
==>  Map_0x266a318:

Io> elvis atPut("home", "Graceland")
==>  Map_0x266a318:

Io> elvis at("home")
==> Graceland
Io> elvis atPut("style", "rock and roll")
==>  Map_0x266a318:

Io> elvis asObject
==>  Object_0x2582cd0:
  home             = "Graceland"
  style            = "rock and roll"

Io> elvis asList
==> list(list(style, rock and roll), list(home, Graceland))
Io> elvis keys
==> list(style, home)
Io> elvis size
==> 2
Io> exit
