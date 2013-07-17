$ irb
irb(main):001:0> 3.times {puts 'hiya there, kiddo'}
hiya there, kiddo
hiya there, kiddo
hiya there, kiddo
=> 3
irb(main):002:0> 3.times do
irb(main):003:1*   puts 'hiya there, kiddo'
irb(main):004:1> end
hiya there, kiddo
hiya there, kiddo
hiya there, kiddo
=> 3

irb(main):005:0> animals = ['lions and ', 'tigers and', 'bears', 'oh my']
=> ["lions and ", "tigers and", "bears", "oh my"]
irb(main):006:0> animals.each {|a| puts a}
lions and 
tigers and
bears
oh my
=> ["lions and ", "tigers and", "bears", "oh my"]

irb(main):007:0> class Fixnum
irb(main):008:1>  def my_times
irb(main):009:2>     i = self
irb(main):010:2>     while i > 0
irb(main):011:3>       i = i - 1
irb(main):012:3>       yield
irb(main):013:3>     end
irb(main):014:2>   end
irb(main):015:1> end
=> nil
irb(main):016:0> 3.my_times {puts 'mangy moose'}
mangy moose
mangy moose
mangy moose
=> nil

irb(main):017:0> def call_back(&block)
irb(main):018:1>   block.call
irb(main):019:1> end
=> nil
irb(main):020:0> def pass_block(&block)
irb(main):021:1>   call_back(&block)
irb(main):022:1> end
=> nil
irb(main):023:0> pass_block {puts 'Hello, block'}
Hello, block
=> nil

irb(main):024:0> execute_at_noon {puts 'Beep beep... time to get up'}
NoMethodError: undefined method `execute_at_noon' for main:Object
        from (irb):24
        from /usr/bin/irb:12:in `<main>'

irb(main):025:0> def in_case_of_emergency
irb(main):026:1>   yield if emergency?
irb(main):027:1> end
=> nil
irb(main):028:0> in_case_of_emergency do
irb(main):029:1*   use_credit_card
irb(main):030:1>   panic
irb(main):031:1> end
NoMethodError: undefined method `emergency?' for main:Object
        from (irb):26:in `in_case_of_emergency'
        from (irb):28
        from /usr/bin/irb:12:in `<main>'

irb(main):032:0> def within_a_transaction
irb(main):033:1>   begin_transaction
irb(main):034:1>   yield
irb(main):035:1>   end_transaction
irb(main):036:1> end
=> nil
irb(main):037:0> within_a_transaction do
irb(main):038:1*   things_that
irb(main):039:1>   must_happen_together
irb(main):040:1> end
NameError: undefined local variable or method `begin_transaction' for main:Object
        from (irb):33:in `within_a_transaction'
        from (irb):37
        from /usr/bin/irb:12:in `<main>'
irb(main):041:0> quit
