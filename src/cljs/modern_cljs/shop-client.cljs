(ns modern-cljs.shop-client
  (:require-macros [hiccups.core :as hsc]
                   [shoreleave.remotes.macros :as macros])
  (:use [cljs.reader :only [read-string]])
  (:require [domina :as dom]
            [domina.events :as evts]
            [domina.xpath :as xdom]
            [hiccups.runtime :as hsrt]
            [shoreleave.remotes.http-rpc :as rpc]))

(defn calculate []
  (let [field-ids ["quantity" "price" "tax" "discount"]
        quantity (read-string (dom/value (dom/by-id "quantity")))
        price (read-string (dom/value (dom/by-id "price")))
        tax (read-string (dom/value (dom/by-id "tax")))
        discount (read-string (dom/value (dom/by-id "discount")))
        total (dom/by-id "total")]
    (rpc/remote-callback :calculate-remote
                         [quantity price tax discount]
                         #(dom/set-value! total (.toFixed % 2)))))

(defn reset []
  (let [fields-ids ["quantity" "price" "tax" "discount" "total"]
        fields-els (map dom/by-id fields-ids)]
  (rpc/remote-callback :reset-remote []
                       #(doall (map dom/set-value! fields-els %)))))

(defn ^:export buttons-handler []
  (when (and js/document
             (aget js/document "getElementById"))
    (doall (evts/listen! (dom/by-id "buttonCalculate") :click calculate)
        (evts/listen! (dom/by-id "buttonReset") :click  reset))))
