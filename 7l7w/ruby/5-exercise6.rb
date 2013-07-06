answer = rand(10)
puts 'Guess what I thought, please input a number between 0~9:'
guess = gets.to_i
until guess == answer
  if (guess > answer)
    puts 'Oops, it is bigger than mine, please input again:'
  else
    puts 'Oops, it is smaller than mine, please input again:'
  end
  guess = gets.to_i
end
puts 'U got me!'
