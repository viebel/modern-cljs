(ns modern-cljs.validation
  (:require [modern-cljs.templates :refer [welcome-page templ-render top-message bottom-message double-message]]
            [modern-cljs.remotes :refer [authentication-remote]]
            [modern-cljs.database :refer [users]]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))


(def ^:dynamic *password-re* #"^(?=.*\d).{4,8}$")

(def ^:dynamic *email-re* #"^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$")

;; the map of the credentials associated with the email.
(defn load-credentials-fn [email]
  (users email))

(defn authentication [email password]
  (let [user-credentials {:username email :password password}]
    (creds/bcrypt-credential-fn load-credentials-fn user-credentials)))

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
        (sign-in (authentication email password))
        (if (and email-error (not password-error))
          (templ-render top-message "Wrong email")
          (if (and (not email-error) password-error)
            (templ-render bottom-message "Wrong password")
            (templ-render double-message "Wrong email" "Wrong password")))))))