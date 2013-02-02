# Tutorial 14 - A friend is more valuable of a treasure

In the [latest tutorial][1] we were able to reach the following decent
objectives:

* follow the Don't Repeat Yourself (DRY) principle by sharing the rules
  of the `Login Form` validation between the server-side and the
  client-side code. To reach this result we used the [Valip][2] library
  and the `:crossovers` build option of [lein-cljsbuild][3];
* adhere to the progressive enhancement strategy by defining and
  remotising a server-side only validator. To reach this result we used
  [Valip][2] and [Shoreleave][4] libraries.

To complete the use of the `Login Form` as a reference sample for our long
journey in the CLJS/CLJ land, we need to fill few residual gaps.

# Introduction

In this tutorial we're going to add to the `modern-cljs` project some
kind of security

* introduce some kind of security in the client-server communication;
* introduce a database of user on the server-side;
* adhere to introduce a kind of html templating system to be used when the browser
  JS is disabled or when the browser doesn't support JS;
* introduce a DOM templating system to be used on the

# Next step - TBD

TO BE DONE

# License

Copyright © Mimmo Cosenza, 2012-13. Released under the Eclipse Public
License, the same as Clojure.

[1]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-13.md
[2]: https://github.com/cemerick/valip
[3]: https://github.com/emezeske/lein-cljsbuild
[4]: https://github.com/shoreleave

[3]: https://github.com/weavejester/valip
[4]: https://github.com/cemerick
[5]: https://github.com/weavejester
[6]: https://github.com/cemerick/valip/blob/master/README.md
[7]: http:/localhost:3000/login-dbg.html
[8]: https://github.com/cemerick/valip/blob/master/README.md
[9]: http://dev.clojure.org/display/design/Feature+Expressions

[11]: https://github.com/emezeske
[12]: https://github.com/emezeske/lein-cljsbuild/blob/0.3.0/doc/CROSSOVERS.md
[13]: http://clojuredocs.org/clojure_core/clojure.core/if-let
[14]:https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-12.md#first-try
[15]: https://github.com/emezeske/lein-cljsbuild
[16]: https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-10.md
[17]: http://clojuredocs.org/clojure_core/clojure.core/let#example_878

[19]: https://raw.github.com/magomimmo/modern-cljs/master/doc/images/remote-validator.png
