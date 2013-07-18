a = (1..16).to_a

puts "print four in line by each"
a.each do |i|
	print "\t#{i}"
	print "\n" if (i % 4 == 0)
end

puts "\nprint four in line by each_slice"
a.each_slice(4) do |line|
	line.each {|i| print "\t#{i}"}
	puts
end
