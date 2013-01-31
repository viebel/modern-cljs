(ns modern-cljs.remotes
  (:require [modern-cljs.login.java.validators :as v]
            [modern-cljs.database :refer [users]]
            [cemerick.shoreleave.rpc :refer [defremote]]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))

(defremote calculate [quantity price tax discount]
  (-> (* quantity price)
      (* (+ 1 (/ tax 100)))
      (- discount)))

(defremote email-domain-errors [email]
  (v/email-domain-errors email))

(defremote authentication-remote [email password]
  (let [user-credentials {:username email :password password}]
    (creds/bcrypt-credential-fn #(users %) user-credentials)))

