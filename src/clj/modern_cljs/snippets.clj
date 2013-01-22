(ns modern-cljs.snippets
  (:require [net.cgrand.enlive-html :refer [emit* defsnippet content html-resource]]))

(def roles-map
  {#{:modern-cljs.database/user} "User"
   #{:modern-cljs.database/admin} "Administrator"})

(defn snip-render [snippet arg1 arg2]
  (apply str (emit* (snippet arg1 arg2))))

(defsnippet welcome-form "private/templates/welcome-page.html" [:form#welcomeForm]
  [username role]
  [:b#welcome] (content (str "Hi " username))
  [:div#role] (content (str "You are logged as " (roles-map role))))
