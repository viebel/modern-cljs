(ns modern-cljs.shop-client
  (:require-macros [hiccups.core :as hsc]
                   [shoreleave.remotes.macros :as macros])
  (:require [domina :as dom]
            [domina.events :as evts]
            [domina.xpath :as xdom]
            [hiccups.runtime :as hsrt]
            [shoreleave.remotes.http-rpc :as rpc]))

(defn calculate []
  (let [quantity (dom/value (dom/by-id "quantity"))
        price (dom/value (dom/by-id "price"))
        tax (dom/value (dom/by-id "tax"))
        discount (dom/value (dom/by-id "discount"))
        total (dom/by-id "total")]
    (rpc/remote-callback :calculate-remote
                         [quantity price tax discount]
                         #(dom/set-value! total %))))

(defn reset []
  (let [fields-ids ["quantity" "price" "tax" "discount" "total"]
        fields-els (map dom/by-id fields-id)]
  (rpc/remote-callback :reset-remote []
                       #(dorun (map dom/set-value! % fields-els)))))

(defn calculate-handler []
  (evts/listen! (dom/by-id "calculate") :click calculate-handler))

(defn reset-handler []
    (evts/listen! (dom/by-id "reset") :click  reset-handler))
