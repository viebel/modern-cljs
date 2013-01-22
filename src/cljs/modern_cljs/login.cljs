(ns modern-cljs.login
  (:require-macros [hiccups.core :refer [html]]
                   [shoreleave.remotes.macros :as shore-macros])
  (:require [modern-cljs.templates :refer [welcome-page]]
            [shoreleave.remotes.http-rpc :as rpc]
            [domina :refer [by-id by-class value append! prepend! destroy! log swap-content!]]
            [domina.events :refer [listen! prevent-default dispatch!]]))

(def ^:dynamic *password-re* #"^(?=.*\d).{4,8}$")

(def ^:dynamic *email-re* #"^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$")

(defn sign-in [login-status]
  (destroy! (by-class "help"))
  (destroy! (by-class "error"))
  (if login-status
    (swap-content! (by-id "loginForm") (welcome-page login-status))
    (prepend! (by-id "loginForm") (html [:div.help.email "Authentication Failed, wrong email or password"]))))

(defn validate-email [email]
  (destroy! (by-class "error"))
  (destroy! (by-class "email"))
  (if (not (re-matches *email-re* (value email)))
    (do
      (prepend! (by-id "loginForm") (html [:div.help.email "Wrong email"]))
      false)
    true))

(defn validate-password [password]
  (destroy! (by-class "error"))
  (destroy! (by-class "password"))
  (if (not (re-matches *password-re* (value password)))
    (do
      (append! (by-id "loginForm") (html [:div.help.password "Wrong password"]))
      false)
    true))

(defn validate-form [evt]
  (let [email (by-id "email")
        password (by-id "password")
        email-val (value email)
        password-val (value password)]
    ;(prevent-default evt)
    (if (or (empty? email-val) (empty? password-val))
      (do
        (destroy! (by-class "help"))
        (destroy! (by-class "error"))
        (append! (by-id "loginForm") (html [:div.error "Please complete the form"]))
        (prevent-default evt))
      (if (and (validate-email email)
               (validate-password password))
        (dispatch! (by-id "loginForm") :submit {})
        false))))

(defn ^:export init []
  (if (and js/document
           (aget js/document "getElementById"))
    (let [email (by-id "email")
          password (by-id "password")]
      (listen! (by-id "submit") :click (fn [evt] (validate-form evt)))
      (listen! email :blur (fn [evt] (validate-email email)))
      (listen! password :blur (fn [evt] (validate-password password))))))
