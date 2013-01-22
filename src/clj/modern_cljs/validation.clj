(ns modern-cljs.validation
  (:require [modern-cljs.templates :refer [welcome-page templ-render top-message bottom-message double-message]]
            [modern-cljs.remotes :refer [authentication-remote]]))

(def ^:dynamic *password-re* #"^(?=.*\d).{4,8}$")

(def ^:dynamic *email-re* #"^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$")

(defn sign-in [login-status]
  (if login-status
    (templ-render welcome-page login-status)
    (templ-render top-message  "Authentication Failed, wrong email or password")))

(defn validate-email [email]
  (empty? (re-matches *email-re* email)))

(defn validate-password [password]
  (empty? (re-matches *password-re* password)))

(defn validate-form [email password]
  (if (and (empty? email) (empty? password))
    (templ-render bottom-message "Please complete the form")
    (let [email-error (validate-email email)
          password-error (validate-password password)] 
      (if (and (not email-error) (not password-error))
        (sign-in (authentication-remote email password))
        (if (and email-error (not password-error))
          (templ-render top-message "Wrong email")
          (if (and (not email-error) password-error)
            (templ-render bottom-message "Wrong password")
            (templ-render double-message "Wrong email" "Wrong password")))))))