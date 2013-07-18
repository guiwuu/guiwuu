class Tree
	attr_accessor :children, :node_name

	def initialize(name, children=[])
		@children = children
		@node_name = name
	end

	def initialize(root={})
		return [] if root.none?
		root.each do |key, value|
			@node_name = key
			@children = []
			value.each do |key, value|
				tree = Tree.new({key => value})
				children.push(tree)
			end
		end
	end

	def visit_all(&block)
		visit &block
		children.each {|c| c.visit_all &block}
	end

	def visit_all_wide(&block)
		visit &block
		children.each do |c| 
			c.visit &block
		end
		children.each do |c| 
			c.children.each do |d|
				d.visit_all_wide &block
			end
		end
	end

	def visit(&block)
		block.call self
	end
end

m = {'grandpa' => {'dad' => {'child 1' => {}, 'child 2' => {}}, 'uncle' => {'child 3' => {}, 'child 4' => {}}}}
ruby_tree = Tree.new(m)

puts "Visiting a node"
ruby_tree.visit {|node| puts node.node_name}

puts "\nvisiting entire tree"
ruby_tree.visit_all {|node| puts node.node_name}

puts "\nvisiting entire tree widely"
ruby_tree.visit_all_wide {|node| puts node.node_name}
