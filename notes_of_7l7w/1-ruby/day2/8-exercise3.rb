f = File.open('8-exercise3.rb')
no = 0
f.each_line do |line|
	no = no + 1
	puts "\t#{no} #{line}" if line.include?("e")
end
