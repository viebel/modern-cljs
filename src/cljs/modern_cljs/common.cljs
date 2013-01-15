(ns modern-cljs.common
  (:require [domina :as dom]))

(defn ^:export init-button [button-id onload-fn]
  (if (and js/document
           (aget js/document "getElementById"))
    (let [button (dom/by-id button-id)]
      (dom/set-attr! button "type" "button")
      (aset button "onclick" onload-fn))))