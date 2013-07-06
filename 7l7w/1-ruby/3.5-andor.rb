$ irb
irb(main):001:0> true and false
=> false
irb(main):002:0> true or false
=> true
irb(main):003:0> false && false
=> false
irb(main):004:0> true && this_will_cause_an_error
NameError: undefined local variable or method `this_will_cause_an_error' for main:Object
        from (irb):4
        from /cygdrive/c/Ruby200-x64/bin/irb:12:in `<main>'
irb(main):005:0> false && this_will_not_cause_an_error
=> false
irb(main):006:0> true or this_will_not_cause_an_error
=> true
irb(main):007:0> true || this_will_not_cause_an_error
=> true
irb(main):008:0> true | this_will_cause_an_error
NameError: undefined local variable or method `this_will_cause_an_error' for main:Object
        from (irb):8
        from /cygdrive/c/Ruby200-x64/bin/irb:12:in `<main>'
irb(main):009:0> true | false
=> true
irb(main):010:0> quit
