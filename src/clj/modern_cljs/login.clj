(ns modern-cljs.login
  (:require [modern-cljs.login.validators :refer [user-credential-errors]]
            [modern-cljs.login.java.validators :refer [email-domain-errors]]
            [modern-cljs.templates :refer [welcome-page error-page templ-render]]
            [modern-cljs.remotes :refer [authentication-remote]]))

(defn sign-in [login-status]
  (if (boolean login-status)
    (templ-render welcome-page login-status)
    (templ-render error-page  "Authentication Failed." nil)))

(defn concat-error-message [errors-map]
  {:top-message (nth (errors-map :email) 0)
   :bottom-message (nth (errors-map :password) 0)})

(defn authenticate-user [email password]
  (if-let [errors (merge (email-domain-errors email) (user-credential-errors email password))]
    (let [{e-errs-msg :top-message p-errs-msg :bottom-message} (concat-error-message errors)]
      (templ-render error-page e-errs-msg p-errs-msg))
      (sign-in (authentication-remote email password))))
