(ns modern-cljs.remotes
  (:require [modern-cljs.login.java.validators :as v]
            [modern-cljs.database :refer [load-credential-fn]]
            [cemerick.shoreleave.rpc :refer [defremote]]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))
;; Shopping calculator remote. Legacy of older tutorials
(defremote calculate [quantity price tax discount]
  (-> (* quantity price)
      (* (+ 1 (/ tax 100)))
      (- discount)))

;; Remote function for email domain checking
(defremote email-domain-errors [email]
  (v/email-domain-errors email))

;; Remote authentication
(defremote authentication-remote [email password]
  (let [user-credentials {:username email :password password}]
    (creds/bcrypt-credential-fn load-credential-fn user-credentials)))

