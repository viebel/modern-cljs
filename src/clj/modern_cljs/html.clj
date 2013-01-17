(ns modern-cljs.html
  (:require [net.cgrand.enlive-html :as enlive-html]))

(enlive-html/deftemplate error-message 
  "templates/error-message.html" [msg]
  [:font#errorMessage]  (enlive-html/content msg))

(enlive-html/deftemplate top-message "templates/login-page.html"
  [msg]
  [:legend#legend] (enlive-html/after (enlive-html/html-snippet (apply str (error-message msg)))))

(enlive-html/deftemplate email-message "templates/login-page.html"
  [msg]
  [:input#email] (enlive-html/after (enlive-html/html-snippet (apply str (error-message msg)))))

(enlive-html/deftemplate password-message "templates/login-page.html"
  [msg]
  [:input#password] (enlive-html/after (enlive-html/html-snippet (apply str (error-message msg)))))

(enlive-html/deftemplate welcome-page "templates/welcome-page.html"
  [username]
  [:b#welcome] (enlive-html/content (str "Hi " username)))

(defn send-welcome-page
  [username]
  (apply str (welcome-page username)))

(defn build-pages
  ;; Build the html page to be sent on the submit request of the login form 
  [submit-request]
  (let [form-error (submit-request :form-error)
        email-error (submit-request :email-error)
        password-error (submit-request :password-error)
        log-error (submit-request :log-error)
        username (submit-request :username)]
    (if form-error
      (apply str (top-message "Please complete the form"))
      (if password-error
        (apply str (password-message "Please insert a valid password"))
        (if email-error
          (apply str (email-message "Please insert a valid email"))
          (if log-error
            (apply str (top-message "Authentication failed."))
            (apply str (welcome-page username))))))))
