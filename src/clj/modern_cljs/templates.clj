(ns modern-cljs.templates
  (:require [modern-cljs.snippets :refer [welcome-form snip-render]]
            [hiccup.core :as hiccup]
            [net.cgrand.enlive-html :refer [deftemplate content append prepend html-snippet substitute]]))

;; Templates' rederer. It return a string with the HTML code
(defn templ-render
  ([template arg] (apply str (template arg)))
  ([template arg1 arg2] (apply str (template arg1 arg2))))

;; Error messages
(deftemplate error-page "public/login.html"
  [top-msg bottom-msg]
  [:form#loginForm] (prepend (html-snippet (hiccup/html [:div.help top-msg])))
  [:form#loginForm] (append (html-snippet (hiccup/html [:div.help bottom-msg]))))

;; Welcome page for logged user.
(deftemplate welcome-page "public/login.html"
  [login-status]
  [:form#loginForm] (substitute (html-snippet (snip-render welcome-form (login-status :username) (login-status :role)))))
