(ns modern-cljs.login.java.validators
  (:require [valip.core :refer [validate]]
            [valip.java.predicates :refer [valid-email-domain?]]))

;; Check whether the domain of the supplied email has a MX DNS entry
(defn email-domain-errors [email]
  (validate {:email email}
            [:email valid-email-domain? "The domain of the email doesn't exist."]))
