# Tutorial 10 - Ajax

In the [last tutorial][1] we were happy enough with the reached results
in terms of [separation of concerns][2] and functional programming
style. As we said in [Tutorial 4][3] the very first reason why JS
adoption became so intense had to do with its ability to easily support
client-side validation of HTML forms. Then, due the enrichment  the **ajax* term quickly
became a buzzword all over the places in very short time. In this
tutorial we're going to approach ajax programming in CLJS.

# Introduction

To keep things simple enough we're going to stay with our boring
shopping calculator form as a use case to be used for our ajax
implementation in CLJS.

What we'd to do is to move the calculation from the client side code
(i.e. CLJS) to the server side code (i.e. CLJ) and then let the former
askes the latter to produce the result.


As everybody knows this kind of client-server distribution of work could
be very useful for more reasons:

* the
In this chapter we provide a simple tutorial to build Server-Client
applications using Clojure on the server-side and ClojureScript on the
client-side. To this aim we exploit the
[shoreleave-remote-ring library][1].

As first step we need to import the *Shoreleave-Remote-Ring*
dependencies, just adding to `project.clj`

```Clojure

             :dependencies [...
                           [com.cemerick/shoreleave-remote-ring "0.0.2"]
                           [shoreleave/shoreleave-remote "0.2.2"]]
```

Our goal now is to transfer on the server-side the *calculation* and
*reset* routines of the shopping-calculator form introduced in the
previous tutorials, as depicted in the following diagram

![Shopping-diagram][2]


## The Server-Side

On the server side (Clojure), we need to define the remote funcitons
`calculate` and `reset`

```Clojure

        (ns modern-cljs.core
                (:use compojure.core)
                (:require [cemerick.shoreleave.rpc :as rpc]
                              [compojure.handler :as handler]
                              [compojure.route :as route]))

        (rpc/defremote calculate-remote
                [quantity price tax discount]
                        (-> (* quantity price)
                        (* (+ 1 (/ tax 100)))
                        (- discount)))

        (rpc/defremote reset-remote
                []
                ["1" "1.00" "0.0" "0.00" "0.00"])
```


The only thing that we have to do is to add the middleware `app` which
is able to hook the server and link it to the client, which can now
access to them.

```Clojure

        (defroutes app-routes
                (GET "/" [] "<p>Hello from compojure</p>")
                ; to server static pages saved in resources/public directory
                (route/resources "/")
                ; when the static resource does not exist
                (route/not-found "Page non found"))

        (def handler
                (handler/site app-routes))

        (def app (-> #'handler
             rpc/wrap-rpc
             handler/site))
```

Last, we assign the middleware to *Ring* in the project file, that is

```Clojure

          ...
          ; ring tasks configuration
          :ring {:handler modern-cljs.core/app}
```

## The Client-Side

Since now client and server are linked, we can access to the remote
functions defined in the server just being careful to invoke those
functions correctly, that is, using the `remote-callback` function
provided by the *shoreleave-remote-ring* library.

```Clojure

        (ns modern-cljs.shop-client
                (:require-macros [hiccups.core :as hsc]
                                 [shoreleave.remotes.macros :as macros])
        (:use [cljs.reader :only [read-string]])
                (:require [domina :as dom]
                              [domina.events :as evts]
                  [domina.xpath :as xdom]
                  [hiccups.runtime :as hsrt]
                  [shoreleave.remotes.http-rpc :as rpc]))

        (defn calculate []
                (let [field-ids ["quantity" "price" "tax" "discount"]
                          quantity (read-string (dom/value (dom/by-id "quantity")))
                          price (read-string (dom/value (dom/by-id "price")))
                          tax (read-string (dom/value (dom/by-id "tax")))
                          discount (read-string (dom/value (dom/by-id "discount")))
                          total (dom/by-id "total")]
                  (rpc/remote-callback :calculate-remote
                          [quantity price tax discount]
                             #(dom/set-value! total (.toFixed % 2)))))

        (defn reset []
                (let [fields-ids ["quantity" "price" "tax" "discount" "total"]
                          fields-els (map dom/by-id fields-ids)]
                  (rpc/remote-callback :reset-remote []
                       #(doall (map dom/set-value! fields-els %)))))
```















[1]: https://github.com/shoreleave/shoreleave-remote-ring.git
[2]: https://raw.github.com/magomimmo/modern-cljs/tut-server/doc/images/shop-diag.png
