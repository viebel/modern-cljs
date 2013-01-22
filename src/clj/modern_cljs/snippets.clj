(ns modern-cljs.snippets
  (:require [net.cgrand.enlive-html :refer [emit* defsnippet content]]))

(def roles-map
  {#{:modern-cljs.core/user} "User"
   #{:modern-cljs.core/admin} "Administrator"})

(defn snip-render [snippet args]
  (apply str (emit* (snippet args))))

(defsnippet welcome-page "templates/welcome-page.html" [:form#welcomeForm]
  [login-status]
  [:b#welcome] (content (str "Hi " (login-status :username)))
  [:div#role] (content (str "You are logged as " (roles-map (login-status :roles)))))

