class Numeric
	def inches
		self
	end

	def feet
		self * 12.inches
	end

	def yards
		self * 3.feet
	end

	def miles
		self * 5280.feet
	end

	def back
		self * -1
	end

	def forward
		self
	end
end

puts 10.miles.back
puts 2.feet.forward
num1 = 11
num2 = num1.yards.back
puts num1.object_id 
puts num2.object_id
puts num1 == 11