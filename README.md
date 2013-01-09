# modern-cljs

A series of tutorials to guide you in creating and setting up
[ClojureScript][1] (CLJS) projects.

# Introduction

This series of tutorials will guide you in creating, setting up and
running simple CLJS projects. The series follows a progressive
enhancement of projects themselves.

Assuming you already have installed [leiningen 2][2], to run the last
available tutorial without coding:

1. `$ git clone https://github.com/magomimmo/modern-cljs.git`
2. `$ cd modern-cljs`
3. ` $ lein ring server`
4. open a new terminal and cd in modern-cljs
5. `$ lein cljsbuild once`
6. `$ lein trampoline cljsbuild repl-listen`
7. visit [login-dbg.html][3] and/or [shopping-dbg.html][4]
8. play with the repl connected to the browser

> NOTE: If you want to access the code of any single tutorial because
> you don't want to `copy&paste` it or you don't want to write it
> yourself, do as follows:
>
> * `$ git clone https://github.com/magomimmo/modern-cljs.git`
> * `$ cd modern-cljs`
> * `$ git checkout tutorial-01 # for tutorial 1, tutorial-02 for tutorial 2 etc.`
>
> That said, I suggest coding yourself the content of the tutorials. In
> my experience is always the best choice if you are not already fluent
> in the programming language you have under your fingers.

## [Tutorial 1 - The basic][5]

In the first tutorial you are going to create and configure a very basic
CLJS project.

## [Tutorial 2 - Browser CLJS REPL (bREPL)][6]

In this tutorial you are going to set up a browser connected CLJS REPL
(bRepl) using an external http-server.

## [Tutorial 3 - CLJ based http-server][7]

In this tutorial you are going to substitute the external http-server
with [ring][8], a CLJ based http-server.

## [Tutorial 4 - Modern ClojureScript][9]

In this tutorial we start having some fun with CLJS form validation, by
porting from JS to CLJS the login form example of
[Modern Javascript: Development and design][10] by [Larry Ullman][11].

## [Tutorial 5 - Introducing Domina][12]

In this tutorial we're going to use [domina library][13] to make our
login form validation more clojure-ish.

## [Tutorial 6 - Easy made Complex and Simple made Easy][14]

In this tutorial we're going to investigate and solve in two different
ways the not so nice issue we met in the last tutorial.

##  [Tutorial 7 - On being doubly aggressive][15]

In this tutorial we're going to explore CLJS/CLS compilation modes by
using the usual `lein-cljsbuild` plugin of `leiningen`, but we'll
discover a trouble we do not know how to manage yet.

## [Tutorial 8 - Introducing Domina events][16]

In this Tutorial we're going to introduce domina events which, by
wrapping Google Closure Library event management, allows to follow a
more clojure-ish approach in handing DOM events.

## [Tutorial 9 - DOM Manipulation][17]

<<<<<<< HEAD
In this tutorial we're going to illustrate the clojure testing tool using the
patched CLJS compiler as a true and real-life case.

## [Tutorial 10 - It's better to be safe than sorry - Part 2][19]

In this tutorial we're going to finish the work started in the previus
one by introducing mocks to manage the tests of CLJS compiler in the
mutable world of the file system.

## [Tutorial 11 - Handling events using Domina+Hiccups or c2][20]

In this tutorial we get a brief insight in event handling by means of
[domina library][13] and [hiccups][21] or the  [c2 library][22].

# Appendices

In this section of the series I'll collect any content that would have
weighted down any individual tutorials.

## Appendix A - TBD

TO BE DONE

## Appendix B - TBD

TO BE DONE

## Appendix C - TBD

TO BE DONE

## Appendix X - TBD

TO BE DONE
=======
In this tutorial we'are going to face the need to programmatically
manipulate DOM elements as a result of the occurrence of some DOM
events.
>>>>>>> 9b6721a60e72613c72c2d3375546c441f46a5c0d

# License

Copyright © Mimmo Cosenza, 2012-2013. Released under the Eclipse Public
License, the same as Clojure.

[1]: https://github.com/clojure/clojurescript.git
[2]: https://github.com/technomancy/leiningen
[3]: http://localhost:3000/login-dbg.html
[4]: http://localhost:3000/shopping-dbg.html
[5]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-01.md
[6]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-02.md
[7]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-03.md
[8]: https://github.com/mmcgrana/ring.git
[9]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-04.md
[10]: http://www.larryullman.com/books/modern-javascript-develop-and-design/
[11]: http://www.larryullman.com/
[12]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-05.md
[13]: https://github.com/levand/domina
[14]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-06.md
<<<<<<< HEAD
[15]: http://localhost:3000/shopping-dbg.html
[16]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-07.md
[17]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-08.md
[18]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-09.md
[19]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-10.md
[20]: https://github.com/magomimmo/modern-cljs/blob/tut-11/doc/tutorial-11.md
[21]: https://github.com/teropa/hiccups
[22]: https://github.com/lynaghk/c2.git
=======
[15]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-07.md
[16]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-08.md
[17]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-09.md
>>>>>>> 9b6721a60e72613c72c2d3375546c441f46a5c0d
