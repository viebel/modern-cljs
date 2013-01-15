(ns modern-cljs.login
  (:require-macros [shoreleave.remotes.macros :as shore-macros]
                   [enfocus.macros :refer [at from set-attr set-prop get-prop content select]])
  (:require [enfocus.core :as enf-core]
            [shoreleave.remotes.http-rpc :as rpc]))

(defn reset-fields!
  []
  (at js/document
      ["#emailError"] (set-attr :style "display: none")
      ["#loginError"] (set-attr :style "display: none")
      ["#formError"] (set-attr :style "display: none")
      ["#email"] (set-prop :value "")
      ["#password"] (set-prop :value "")))

(defn sign-action
  [login-status]
  (if (not (login-status :log-error))
    
    (shore-macros/rpc (welcome (login-status :username)) [welcome-page]
                      (at js/document
                          ["html"] (content welcome-page)))
    (at js/document
        ["#loginError"] (set-attr :style "display: block"))))

(defn validate-usr
  [email password]
  (let [regex-mail #"^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$"]
    (if (not (re-matches regex-mail email))
      (at js/document
          ["#emailError"] (set-attr :style "display: block"))
      (shore-macros/rpc (authentication email password) [login-status] (sign-action login-status)))))

(defn ^:export validate-form []
  (let [email (from (select ["#email"]) (get-prop :value))
        password (from (select ["#password"]) (get-prop :value))]
    (reset-fields!)
    (if (and (> (count email) 0)
             (> (count password) 0))
      (validate-usr email password)
      (at js/document
          ["#formError"] (set-attr :style "display: block"))))) 

