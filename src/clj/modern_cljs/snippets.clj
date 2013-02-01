(ns modern-cljs.snippets
  (:require [net.cgrand.enlive-html :refer [emit* defsnippet content html-resource]]))

;; Snippets' renderer, returns the HTML code as a string 
(defn snip-render [snippet arg1 arg2]
  (apply str (emit* (snippet arg1 arg2))))

;; Welcome form for logged users.
(defsnippet welcome-form "private/templates/welcome-page.html" [:form#welcomeForm]
  [username role]
  [:b#welcome] (content (str "Hi " username))
  [:div#role] (content (str "You are logged as " role)))
