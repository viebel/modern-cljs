(ns modern-cljs.core
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [resources not-found]]
            [compojure.handler :refer [site]]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credential :as cred])))

;; a dummy i-memory user db
(def users {"root" {:username "mimmo.cosenza@gmail.com"
                    :password "mimmo1"
                    :roles #{::admin}}
            "mimmo" {:username "giacomo.cosenza@sinapsi.com"
                     :password "mimmo1"
                     :roles @{::user}}})


;; defroutes macro defines a function that chains individual route
;; functions together. The request map is passed to each function in
;; turn, until a non-nil response is returned.
(defroutes app-routes
  ; to serve document root address
  (GET "/" [] "<p>Hello from compojure</p>")
  ; to server static pages saved in resources/public directory
  (resources "/")
  ; if page is not found
  (not-found "Page non found"))

;; site function create an handler suitable for a standard website,
;; adding a bunch of standard ring middleware to app-route:
(def handler
  (site app-routes))
