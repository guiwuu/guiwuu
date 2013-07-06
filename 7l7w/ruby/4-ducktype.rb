$ irb
irb(main):001:0> 4 + 'four'
TypeError: String can't be coerced into Fixnum
        from (irb):1:in `+'
        from (irb):1
        from /cygdrive/c/Ruby200-x64/bin/irb:12:in `<main>'
irb(main):002:0> 4.class
=> Fixnum
irb(main):003:0> (4.0).class
=> Float
irb(main):004:0> 4 + 4.0
=> 8.0
irb(main):005:0> def add_them_up
irb(main):006:1>   4 + 'four'
irb(main):007:1> end
=> nil
irb(main):008:0> add_them_up
TypeError: String can't be coerced into Fixnum
        from (irb):6:in `+'
        from (irb):6:in `add_them_up'
        from (irb):8
        from /cygdrive/c/Ruby200-x64/bin/irb:12:in `<main>'
irb(main):009:0> i = 0
=> 0
irb(main):010:0> a = ['100', 100.0]
=> ["100", 100.0]
irb(main):011:0> while i < 2
irb(main):012:1>   puts a[i].to_i
irb(main):013:1>   i = i + 1
irb(main):014:1> end
100
100
=> nil
irb(main):015:0> quit
