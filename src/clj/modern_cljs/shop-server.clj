(ns modern-cljs.shop-server
  (:use compojure.core)
  (:require [cemerick.shoreleave.rpc :as rpc]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] "<p>Hello from compojure</p>")
  (route/resources "/")
  (route/not-found "Page non found"))

(def handler
  (handler/site app-routes))

(rpc/defremote ^{:remote-name :calculate-remote} calculate-remote
  [quantity price tax discount]
   (-> (* quantity price)
       (* (+ 1 (/ tax 100)))
       (- discount)
       (.toFixed 2)))

(rpc/defremote ^{:remote-name :reset-remote} reset-remote
  []
  ["1" "1.00" "0.0" "0.00" "0.00"])

(def app (-> #'handler
             rpc/wrap-rpc
             handler/site))