(ns modern-cljs.core
  (:use compojure.core)
  (:require [cemerick.shoreleave.rpc :as rpc]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] "<p>Hello from compojure</p>")
  ; to server static pages saved in resources/public directory
  (route/resources "/")
  ; when the static resource does not exist
  (route/not-found "Page non found"))

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