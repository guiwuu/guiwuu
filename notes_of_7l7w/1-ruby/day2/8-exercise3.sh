$ ruby 8-exercise3.rb 
        1 f = File.open('8-exercise3.rb')
        3 f.each_line do |line|
        5       puts "\t#{no} #{line}" if line.include?("e")
        6 end
