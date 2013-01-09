(ns modern-cljs.core
  (:use compojure.core)
  (:require [cemerick.shoreleave.rpc :as rpc]
            [compojure.handler :as handler]
            [compojure.route :as route]))

;; defroutes macro defines a function that chains individual route
;; functions together. The request map is passed to each function in
;; turn, until a non-nil response is returned.
(defroutes app-routes
  ; to serve document root address
  (GET "/" [] "<p>Hello from compojure</p>")
  ; to server static pages saved in resources/public directory
  (route/resources "/")
<<<<<<< HEAD
  ; when the static resource does not exist
=======
  ; if page is not found
>>>>>>> 9b6721a60e72613c72c2d3375546c441f46a5c0d
  (route/not-found "Page non found"))

;; site function create an handler suitable for a standard website,
;; adding a bunch of standard ring middleware to app-route:
(def handler
  (handler/site app-routes))

(rpc/defremote calculate-remote
  [quantity price tax discount]
  (-> (* quantity price)
      (* (+ 1 (/ tax 100)))
      (- discount)))

(rpc/defremote reset-remote
  []
  ["1" "1.00" "0.0" "0.00" "0.00"])

(def app (-> #'handler
             rpc/wrap-rpc
             handler/site))
