(ns modern-cljs.shopping
  (:require-macros [hiccups.core :refer [html]])
  (:require [domina :refer [by-id by-class value set-value! insert-after! destroy!]]
            [domina.events :refer [listen! prevent-default]]
            [hiccups.runtime :as hiccupsrt]
            [modern-cljs.shopping.validators :refer [validate-form]]
            [shoreleave.remotes.http-rpc :refer [remote-callback]]))

(defn handle-response [errors field value]
  (if-let [error (field errors)]
    (insert-after! (by-id (name field)) (html [:div [:span.error (first error)]]))
    (set-value! (by-id (name field)) value)))

(defn calculate [evt]
  (prevent-default evt)
  (destroy! (by-class "error"))
  (let [quantity (value (by-id "quantity"))
        price (value (by-id "price"))
        tax (value (by-id "tax"))
        discount (value (by-id "discount"))
        errors (validate-form quantity price tax discount)]
    (doall (map #(handle-response errors %1 %2) [:quantity :price :tax :discount] [quantity price tax discount]))
    (when-not errors
      (remote-callback :calculate
                       [quantity price tax discount]
                       #(set-value! (by-id "total") (.toFixed % 2))))))

(defn ^:export init []
  (when (and js/document
             (aget js/document "getElementById"))
    (listen! (by-id "calc") :click (fn [evt] (calculate evt)))))

