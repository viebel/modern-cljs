(ns modern-cljs.login
  (:require-macros [shoreleave.remotes.macros :as shore-macros]
                   [enfocus.macros :as em])
  (:require [domina :as dom]
            [enfocus.core :as enf-core]
            [shoreleave.remotes.http-rpc :as rpc]))

(em/deftemplate error-msg "error-message.html"
  [msg]
  ["#errorMessage"] (em/content msg))

(defn reset-fields!
  []
  (em/at js/document
      ["#errorContainer"] (em/substitute "")
      ["#email"] (em/set-prop :value "")
      ["#password"] (em/set-prop :value "")))

(defn sign-action
  [login-status]
  (if (not (login-status :log-error))
    (shore-macros/rpc (welcome (login-status :username)) [welcome-page]
                      (em/at js/document
                             ["html"] (em/content welcome-page)))
    (em/at js/document ["#legend"]
           (em/after (error-msg "Authentication failed.")))))

(defn validate-usr
  [email password]
  (let [regex-mail #"^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$"]
    (if (not (re-matches regex-mail email))
      (em/at js/document ["#email"]
          (em/after (error-msg "Please enter a valid email")))
      (shore-macros/rpc (authentication email password) [login-status] (sign-action login-status)))))

(defn validate-form []
  (let [email (em/from (em/select ["#email"]) (em/get-prop :value))
        password (em/from (em/select ["#password"]) (em/get-prop :value))
        email-error (empty? email)
        password-error (empty? password)
        form-error (and email-error password-error)]
    (reset-fields!)
    (if form-error
      (em/at js/document ["#legend"]
            (em/after (error-msg "Please complete the form")))
      (if password-error
        (em/at js/document ["#password"]
               (em/after (error-msg "Please enter a password")))
        (validate-usr email password))))
  false)

(defn ^:export init
  []
  (if (and js/document
           (aget js/document "getElementById"))
    (let [button (dom/by-id "submit")]
      (aset button "onclick" validate-form))))
