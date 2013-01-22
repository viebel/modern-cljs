(ns modern-cljs.templates
  (:require-macros [enfocus.macros :refer [deftemplate content]])
  (:require [enfocus.core :as enfcore]))

(def roles-map
  {#{:modern-cljs.core/user} "User"
   #{:modern-cljs.core/admin} "Administrator"})

(deftemplate welcome-page "../private/templates/welcome-page.html"
  [login-status]
  [:b#welcome] (content (str "Hi " (login-status :username)))
  [:div#role] (content (str "You are logged as " (roles-map (login-status :roles)))))
