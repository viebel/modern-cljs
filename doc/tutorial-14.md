# Tutorial 14 - A friend is more valuable of a treasure

In the [latest tutorial][1] we were able to reach the following decent
objectives:

* follow the Don't Repeat Yourself (DRY) principle by sharing the code
  of the `Login Form` validators that are commons between the
  server-side and the client-side; 
* adhere to the progressive enhancement strategy by defining and
  remotising a server-side only validator.

To complete the use of the `Login Form` as a reference sample for our long
journey in the CLJS/CLJ land, we need to fill few residual gaps.

# Introduction

In this tutorial we're going to fill two residual gaps for completing 
kind of security. To face this new topic, which again is crossing the
border between the client and the server, we'll use the [Friend][5]
library. Useless to say that had been donated to us by
[Chas Emerick][6].

We'll try to understand what we're doing, so be concentrated for a
while.

# First, we had the User

An user could be *Anonymous* or *Known* to the application. Obviously
the most interesting users are those that are both *Anonymous* and
*Known*, because they're the mass. They are *Anoynmous* in the sense
that they didn't give us any explicit information regarding
themself. They are *Known*, because we could have attached an ID
(e.g. cookie) to them. And those IDs allow us to follow them until they
are in our shop and eventually recognize them if they came back later.

Anyway, here we're not going to talk about this type of user, we're
going to talk about a type of user which is already registerd in our
shop. The user can be end-user or an employee of the shop. He could also
be a supplier of the shop. In few words, any user has at least a role in
the shop. There are things that a role is authorised to do in the shop
and things that she's not authorised to do, but others are.

## User Credential

To understand if an entering user are who are saying to be, we ask them
to prodive us some sort of ID.  The weakest been just an email and a
passord. In few words, we ask them their credential.

Ok, let's first create a repository of user's IDs. Create a file named
`db.clj` under the `src/clj/modern_clj` directory and fill it with the
following content:

```clojure
(ns modern-cljs.db)

(def users {"giacomo" {:email "giacomo.cosenza@sinapsi.com"
                       :password "root1"
                       :roles #{::admin}}
            "mimmo" {:email "mimmo.cosenza@gmail.com"
                     :password "user1"
                     :roles #{::user}}})
```

We're not interested here in setting up a real db, so we only defined
the `users` symbol as a map of users already registered with out web
application. Each user is represented as a map and has three properties:
an `:email`, a `:password` and a `:roles`.

It's not very smart to record the passwords of the users as plain text
in a db, even if it allows a more fluent password recovery procedure
when your user has forgotten her password. The security has this
property: the more you want the more you complicate the user
experience. And the programmer's life too.

What we need is a way to protect the users passwords by using a kind of
encryption immediately before we store them in the repository. We love
so much the DRY principle that we want to generalize: *Don't Repeat the
Others* (DRO) principle. Tn the clojurian community the DRO principle
often translates into the Don't Repeat [Emerick][5] (DRE)
principle. Recentely, his [Friend][6] library became wide used and
commented by clojurians looking for a structured and prebaked approach
with security. [Friend][6] library already offers an `hash-bcrypt`
function doing the work we're looking for.

Add the [Friend][6] lib to the `project.clj` declaration

```clojure
;;; project.clj

...
...

  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.5"]
                 [hiccups "0.2.0"]
                 [com.cemerick/shoreleave-remote-ring "0.0.2"]
                 [shoreleave/shoreleave-remote "0.2.2"]
                 [com.cemerick/valip "0.3.2"]
                 [com.cemerick/friend "0.1.3"]
                 [com.h2database/h2 "1.3.170"]]
...
...
```

Now modify the `db.clj` file by adding the call to the
`hash-bcrypt` function from the `friend.credentials` namespace.

```clojure
(ns modern-cljs.db
  (:require [cemerick.friend.credential :refer [hash-bcrypt]]))


(def users {"giacomo" {:email "giacomo.cosenza@sinapsi.com"
                       :password (hash-bcrypt "root1")
                       :roles #{::admin}}
            "mimmo" {:email "mimmo.cosenza@gmail.com"
                     :password (hash-bcrypt "user1")
                     :roles #{::user}}})

```

> NOTE 1: The `roles` properties has been represented as a set of
> keywords, where each keyword represents an individual role.
>
> Spiegare perché le ::keyword?

Ok. We now have a dummy db of users. It registered their email, password
and roles properties. For security reasons the passwords have been
encrypted before to be recorded. Nice. But what this user, credentials
and roles are used for?

## Resourses

As we said there zones of the shop where not everyone is permitted to
have access to. A shop supervisor can generally has access to more shop
resources than a newly engaged helper. And a registered customer has
access to different shop resources too.

So, what we now need is a way to represent the resources of the shop and
to say which roles have access to them.

There are a lot of way to represent resources on the web, being the REST
the most standard way. In this tutorial of the series we don't want to
introduce new topics to talk about, so we stay with a representation of
resources based on a directory structure.

Create a directoy named `admin` under the `resources` directory `$ mkdir
resources/admin`.

We now have an administration resourse that we want to be protected from
being accessed by anyone. How can we inform the application about our
desire?

Enter the [ring][]/[compojure][] libs again. Open the `core.clj` file
and add a new route which requires an authorization to be accessed from
a user.

```clojure
(ns modern-cljs.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [resources not-found]]
            [compojure.handler :refer [site]]
            [modern-cljs.login :refer [authenticate-user]]
            [cemerik.friend :refer [authorize]]))

(defroutes app-routes
  (GET "/" [] "<p>Hello from compojure</p>")
  (GET "/admin" request
       (authorize #{::admin} "This page can only be accessed by authorized users"))
  (POST "/login" [email password] (authenticate-user email password))
  (resources "/")
  (not-found "Page non found"))
```

> NOTE 2: Remember to require the `cemerick.friend` namespace.

But what what does it means `authorize`? Here is its doc:

```clojure
"Macro that only allows the evaluation of the given body of code if the
currently-identified user agent has a role that isa? one of the roles in
the provided set.  Otherwise, control will be thrown up to the
unauthorized-handler configured in the `authenticate` middleware.

The exception that causes this change in control flow carries a map of
data describing the authorization failure; you can optionally provide an
auxillary map that is merged to it as the first form of the body of code
wrapped by `authorize`.

Note that this macro depends upon the *identity* var being bound to the
current user's authentications.  This will work fine in e.g. agent sends
and futures and such, but will fall down in places where binding
conveyance don't apply (e.g. lazy sequences, direct java.lang.Thread
usages, etc)."
```


It's a macro which only allows
Here we're implicitely saying that to access the `/admin` URI a user has
to be first authenticated and then autorized has a member of the
`::admin` role.


Here is where the [Friend][] lib comes in the picture. It has two
important concepts: *workflow* and *credential*. We're not in nouns
reign, so we can use *verbs* as tools to model our world. `workflow` and
`credential` are two functions (i.e. verbs)




First, enter the [ring][]/[compujure][] libraries again. Open the
`core.clj` file and add a new compojure route to the `app-routes`
defined in a previous [tutorial][].

```clojure

```


Every user has a role in the shop. To be recognized as a user playing an
adminisatrative role in the shop, we could ask them more reliable ID
that just a name an a passoword.


## H2 DB

As we said more times, this is not a CLJ tutorial, he's a CLJS tutorial,
but we love clojure on the server-side too, so let's cress the border
and create a DB. We're going to use [H2 DB][7] in embedded mode. There
are more reasons for that:

* H2 DB is just a jar file, so it could be easily embedded within our
  clojurian application;
* It's a relational db and it is representative of any SQL compliant;

First we have to add HB DB to our project.clj. Open it and add H2 to the
project declaration as usual:


Now we have to create the new DB in which to create the user
table. Create a new directory named `db` under the `src/clj/modern_clj`
directory and finally create the `admin.clj` file with the following
content

```clojure
;;; admin.clj

```
## User Credential



*

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
