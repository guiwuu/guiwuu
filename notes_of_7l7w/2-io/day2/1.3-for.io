Io 20110905
Io> for(i, 1, 11, i println);"This one goes up to 11" println
1
2
3
4
5
6
7
8
9
10
11
This one goes up to 11
==> This one goes up to 11
Io> for(i, 1, 11, 2, i println);"This one goes up to 11" println
1
3
5
7
9
11
This one goes up to 11
==> This one goes up to 11
Io> for(i, 1, 2, 1, i println, "extra argument")
1
2
==> 2
Io> for(i, 1, 2, i println, "extra argument")
2
==> extra argument
Io> exit
