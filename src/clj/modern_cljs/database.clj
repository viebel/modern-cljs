(ns modern-cljs.database
  (:require [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))

;; a dummy in-memory user db
(def users {"mimmo.cosenza@gmail.com" {:username "Mimmo"
                                       :password (creds/hash-bcrypt "mimmo1")
                                       :roles #{::admin}}
            "giacomo.cosenza@sinapsi.com" {:username "Giacomo"
                                           :password (creds/hash-bcrypt "mimmo1")
                                           :roles #{::user}}})

