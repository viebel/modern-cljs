(ns modern-cljs.remotes
  (:require [modern-cljs.database :refer [users]]
            [cemerick.shoreleave.rpc :refer [defremote]]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))

;; remote calculator for the shopping form
(defremote calculate [quantity price tax discount]
  (-> (* quantity price)
      (* (+ 1 (/ tax 100)))
      (- discount)))

;; the map of the credentials associated with the email.
(defn load-credentials-fn [email]
  (users email))

;; Remote function for authentication
(defremote authentication-remote [email password]
  (let [user-credentials {:username email :password password}]
    (creds/bcrypt-credential-fn load-credentials-fn user-credentials)))
