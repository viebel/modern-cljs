(ns modern-cljs.validation
  (:require [modern-cljs.templates :refer [welcome-page templ-render top-message bottom-message double-message]]))

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