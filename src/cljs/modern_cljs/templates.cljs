(ns modern-cljs.templates
  (:require-macros [enfocus.macros :refer [deftemplate content]])
  (:require [enfocus.core :as enfcore]))

(def roles-map
  {#{:modern-cljs.database/user} "User"
   #{:modern-cljs.database/admin} "Administrator"})

(deftemplate welcome-page "welcome-page.html"
  [login-status]
  [:b#welcome] (content (str "Hi " (login-status :username)))
  [:div#role] (content (str "You are logged as " (roles-map (login-status :roles)))))
