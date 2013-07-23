class Row
	attr_accessor :headers, :datas

	def initialize headers, datas
		@headers = headers
		@datas = datas
	end

	def method_missing name, *args
		header = name.to_s
		i = headers.index(header)
		datas[i]
	end
end

module ActsAsCsv

	def self.included(base)
		base.extend ClassMethods
	end

	module ClassMethods
		def acts_as_csv
			include InstanceMethods
		end
	end

	module InstanceMethods

		def read
			filename = self.class.to_s.downcase + '.txt'
			file = File.new(filename)
			@headers = file.gets.chomp.split(', ')

			@csv_contents = []
			file.each do |row|
				r = Row.new(@headers, row.chomp.split(', '))
				@csv_contents << r
			end
		end

		attr_accessor :headers, :csv_contents

		def initialize
			read			
		end

		def each &block
			@csv_contents.each &block
		end

	end

end

class ExerciseCsv
	include ActsAsCsv
	acts_as_csv
end

csv = ExerciseCsv.new
csv.each {|row| puts row.one}