(ns modern-cljs.templates
  (:require-macros [enfocus.macros :refer [deftemplate content]])
  (:require [enfocus.core :as enfcore]))

;; Welcome page template
(deftemplate welcome-page "welcome-page.html"
  [login-status]
  [:b#welcome] (content (str "Hi " (login-status :username)))
  [:div#role] (content (str "You are logged as " (login-status :role))))
