$ irb
irb(main):001:0> numbers = {1 => 'one', 2 => 'two'}
=> {1=>"one", 2=>"two"}
irb(main):002:0> numbers[1]
=> "one"
irb(main):003:0> numbers[2]
=> "two"
irb(main):004:0> stuff = {:array => [1, 2, 3], :string => 'Hi, mom!'}
=> {:array=>[1, 2, 3], :string=>"Hi, mom!"}
irb(main):005:0> stuff[:string]
=> "Hi, mom!"
irb(main):006:0> 'string'.object_id
=> -1072617718
irb(main):007:0> 'string'.object_id
=> -1072621378
irb(main):008:0> :string.object_id
=> 54648
irb(main):009:0> :string.object_id
=> 54648
irb(main):010:0> :guiwuu.object_id
=> 228968
irb(main):011:0> quit

$ irb
irb(main):001:0> :guiwuu.object_id
=> 228808
irb(main):002:0> :string.object_id
=> 54648
irb(main):003:0> def tell_the_truth(options={})
irb(main):004:1>   if options[:profession] == :laywer
irb(main):005:2>     'it could be believed that this is almost certainly not false.'
irb(main):006:2>   else
irb(main):007:2*     true
irb(main):008:2>   end
irb(main):009:1> end
=> nil
irb(main):010:0> tell_the_truth
=> true
irb(main):011:0> tell_the_truth :profession => :laywer
=> "it could be believed that this is almost certainly not false."
irb(main):012:0> quit
