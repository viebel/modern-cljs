(ns modern-cljs.core
  (:require [modern-cljs.validation :refer [validate-form]]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [resources not-found]]
            [compojure.handler :refer [api site]]
            [cemerick.shoreleave.rpc :refer [defremote wrap-rpc]]))
                       
;; defroutes macro defines a function that chains individual route
;; functions together. The request map is passed to each function in
;; turn, until a non-nil response is returned.
(defroutes app-routes
  ;; to serve document root address
  (GET "/" [] "<p>Hello from compojure</p>")
  ;; when JavaScript are disabled, one POST for each login page 
  (POST "/public/login.html" [email password] (validate-form email password))
  (POST "/public/login-dbg.html" [email password] (validate-form email password))
  (POST "/public/login-pre.html" [email password] (validate-form email password))
  ;; to server static pages saved in /resources directory
  (resources "/" {:root "."})
  ;; if page is not found
  (not-found "Page non found"))

;; site function create an handler suitable for a standard website,
;; adding a bunch of standard ring middleware to app-route:
(def handler
  (api app-routes))

(def middleware (-> (var handler)
                    (wrap-rpc)
                    (site)))

