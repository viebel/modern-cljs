(ns modern-cljs.templates
  (:require [modern-cljs.snippets :refer [roles-map]]
            [hiccup.core :as hiccup]
            [net.cgrand.enlive-html :refer [deftemplate content append prepend html-snippet]]))

(defn templ-render
  ([template arg] (apply str (template arg)))
  ([template arg1 arg2] (apply str (template arg1 arg2))))

(deftemplate top-message "templates/login.html"
  [msg]
  [:form#loginForm] (prepend (html-snippet (hiccup/html [:div.help msg]))))

(deftemplate bottom-message "templates/login.html"
  [msg]
  [:form#loginForm] (append (html-snippet (hiccup/html [:div.help msg]))))

(deftemplate double-message "templates/login.html"
  [top-msg bottom-msg]
  [:form#loginForm] (prepend (html-snippet (hiccup/html [:div.help top-msg])))
  [:form#loginForm] (append (html-snippet (hiccup/html [:div.help bottom-msg]))))

(deftemplate welcome-page "templates/welcome-page-server.html"
  [login-status]
  [:b#welcome] (content (str "Hi " (login-status :username)))
  [:div#role] (content (str "You are logged as " (roles-map (login-status :roles)))))