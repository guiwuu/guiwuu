Jumd
====

Jumd is a java UMD tool, can be used to parse or make a UMD file. UMD is a widely-used mobile ebook format in China. But there isn't an official document to describe it. So I refer to some resources in Internet, many thanks to them:

1. [http://wenku.baidu.com/view/1555a5db50e2524de5187e49.html](http://wenku.baidu.com/view/1555a5db50e2524de5187e49.html)
2. [http://cnbeta2004.blog.163.com/blog/static/602313402010722459914/](http://cnbeta2004.blog.163.com/blog/static/602313402010722459914/)
3. [http://code.google.com/p/umd-builder/](http://code.google.com/p/umd-builder/)

All codes are moved from [googlecode](https://jumd.googlecode.com) to [github](https://github.com/guiwuu/jumd), you are free to use them. If you have any question, please feel free to [email](guiwuu@gmail.com) me and I will be very appreciate.

Features
--------

1. Parse an umd file to a java object, com.guiwuu.jumd.Umd, and write the object to a umd file
2. Convert a text umd file to a text file(utf-16le encoded), also my initial purpose to write jumd
3. Extract a text umd file to a folder with indepented cover(jpg) and chapter contents(utf-16le encoded) files

Roadmap
-------

1. Add support for comic umd file - 0.02 (postpone)
2. Add a command line interface - 0.05 (postpone)
3. Improve performance of parsing and making umd file - 1.00 (postpone)
4. Add a swing ui, maybe a editor - 2.00 (postpone)

Changelog
---------

###0.01(2011-03-11) first version

* Parse and build umd file
* Convert content of text umd file to text
* Extract all elements of text umd file to folder