(ns modern-cljs.db
  (:require [cemerick.friend.credential :refer [hash-bcrypt]]))


(def users {"giacomo" {:email "giacomo.cosenza@sinapsi.com"
                       :password (hash-bcrypt "root1")
                       :roles #{::admin}}
            "mimmo" {:email "mimmo.cosenza@gmail.com"
                     :password (hash-bcrypt "user1")
                     :roles #{::user}}})
