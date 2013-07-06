$ irb
irb(main):001:0> puts 'This appears to be true.' if 1
This appears to be true.
=> nil
irb(main):002:0> puts 'This appears to be true.' if 'random string'
(irb):2: warning: string literal in condition
This appears to be true.
=> nil
irb(main):003:0> puts 'This appears to be true.' if 0
This appears to be true.
=> nil
irb(main):004:0> puts 'This appears to be true.' if true
This appears to be true.
=> nil
irb(main):005:0> puts 'This appears to be true.' if false
=> nil
irb(main):006:0> puts 'This appears to be true.' if nil
=> nil
irb(main):007:0> quit
