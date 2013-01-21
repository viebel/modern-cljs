(ns modern-cljs.core
  (:require [modern-cljs.snippets :refer [welcome-page render]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [resources not-found]]
            [compojure.handler :refer [api site]]
            [cemerick.shoreleave.rpc :refer [defremote wrap-rpc]]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))
                       
;; a dummy in-memory user db
(def users {"mimmo.cosenza@gmail.com" {:username "Mimmo"
                                       :password (creds/hash-bcrypt "mimmo1")
                                       :roles #{::admin}}
            "giacomo.cosenza@sinapsi.com" {:username "Giacomo"
                                           :password (creds/hash-bcrypt "mimmo1")
                                           :roles #{::user}}})

;; Credential function which. It takes as input a the email and return
;; the map of the credentials associated with the email.
(defn load-credentials-fn [email]
  (users email))

;; Remote function for authentication
(defremote authentication-remote [email password]
  (let [user-credentials {:username email :password password}]
    (creds/bcrypt-credential-fn load-credentials-fn user-credentials)))

(defremote welcome-page-remote [login-status]
  (render (welcome-page login-status)))

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

;; definition of the middleware. We wrap the handler and add the
;; remote functions.
(def middleware
  (-> (var handler)
      (wrap-rpc)
      (site)))