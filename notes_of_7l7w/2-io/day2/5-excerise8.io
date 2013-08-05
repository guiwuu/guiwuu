number := Random value(1,100) floor
10 repeat(
  "Please guess a number between 1 and 100:" println
  guess := File standardInput readLine asNumber
  if(guess > number,
    "Guess is bigger!" println,
    if(guess < number,
      "Guess is smaller!" println,
      "U got me!" println
      break
    )
  )
)
