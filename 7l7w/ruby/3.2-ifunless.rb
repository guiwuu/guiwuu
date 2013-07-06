$ irb
irb(main):001:0> x = 4
=> 4
irb(main):008:0> puts 'This appears to be false.' unless x == 4
=> nil
irb(main):009:0> puts 'This appears to be true.' if x == 4
This appears to be true.
=> nil
irb(main):010:0> if x == 4
irb(main):011:1>   puts 'This appears to be true.'
irb(main):012:1> end
This appears to be true.
=> nil
irb(main):013:0> unless x == 4
irb(main):014:1>   puts 'This appears to be false'
irb(main):015:1> else
irb(main):016:1*   puts 'This appears to be true.'
irb(main):017:1> end
This appears to be true.
=> nil
irb(main):019:0> puts 'This appears to be true.' if not true
=> nil
irb(main):020:0> puts 'This appears to be true.' if !true
=> nil
irb(main):021:0> puts 'This appears to be true.' if true
This appears to be true.
=> nil
irb(main):022:0> puts 'This appears to be true.' unless true
=> nil
irb(main):023:0> puts 'This appears to be true.' unless false
This appears to be true.
=> nil
irb(main):024:0> puts 'This appears to be true.' unless not true
This appears to be true.
=> nil
irb(main):025:0> puts 'This appears to be true.' unless !true
This appears to be true.
=> nil
irb(main):026:0> quit
single line condition: order.calculate_tax unless order.nil?
