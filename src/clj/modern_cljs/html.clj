(ns modern-cljs.html
  (:require [net.cgrand.enlive-html :as enlive-html]))

(enlive-html/deftemplate login-page "templates/login-page.html"
  [error-map]
  [:font#emailError] (enlive-html/set-attr :style (error-map :email))
  [:font#loginError] (enlive-html/set-attr :style (error-map :login))
  [:font#formError] (enlive-html/set-attr :style (error-map :form)))

(enlive-html/deftemplate welcome-page "templates/welcome-page.html"
  [username]
  [:b#welcome] (enlive-html/content (str "Hi " username)))

(defn status->error-map
  ;; Build a map which set the error messages in the login-page.html template.
  [status]
  (let [converter {true "block", false "display: none"}
        form-error (status :form-error)
        email-error (status :email-error)
        log-error (status :log-error)]
    {:form (converter form-error)
     :email (converter (and (not form-error) email-error)) 
     :login (converter (and (not form-error) (not email-error) log-error))}))

(defn send-welcome-page
  [username]
  (apply str (welcome-page username)))

(defn build-pages
  ;; Build the html page to be sent on the submit request of the login form 
  [submit-request]
  (let [error-map (status->error-map submit-request)]
    (if (empty? (submit-request :username))
      (apply str (login-page error-map))
      (apply str (welcome-page (submit-request :username))))))
