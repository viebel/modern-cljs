(ns modern-cljs.login
  (:require [modern-cljs.login.validators :refer [user-credential-errors]]
            [modern-cljs.login.java.validators :refer [email-domain-errors]]
            [modern-cljs.templates :refer [welcome-page bottom-message top-message templ-render]]
            [modern-cljs.remotes :refer [authentication-remote]]))

(defn sign-in [login-status]
  (if (boolean login-status)
    (templ-render welcome-page login-status)
    (templ-render top-message  "Authentication Failed, wrong email or password")))

(defn authenticate-user [email password]
  (let [creds-errs (user-credential-errors email password)
        email-dom-errs (email-domain-errors email)]
    (if (or (boolean creds-errs) (boolean email-dom-errs))
      (templ-render bottom-message "Please complete the form.")
      (sign-in (authentication-remote email password)))))