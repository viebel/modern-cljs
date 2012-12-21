(ns modern-cljs.reset
  (:require-macros [hiccups.core :as hsc])
  (:require [domina :as dom]
            [domina.events :as evts]
            [domina.xpath :as xdom]
            [hiccups.runtime :as hsrt]))

(defn resetform []
  (dom/set-value! (dom/by-id "quantity") "1")
  (dom/set-value! (dom/by-id "price") "1.00")
  (dom/set-value! (dom/by-id "total") "0.00")
  (dom/set-value! (dom/by-id "tax") "0.0")
  (dom/set-value! (dom/by-id "discount") "0.00")  
  false)

(defn appendinfor []
  (dom/append! (xdom/xpath "//body/form")  (hsc/html [:div {:id "txtres"} "Click to reset"])))

(defn removeinfor []
  (dom/destroy! (dom/by-id "txtres")))

(defn addresetlauncher []
  (doall
   [(evts/listen! (dom/by-id "reset") :mouseover (fn [evt] (appendinfor)))
    (evts/listen! (dom/by-id "reset") :mouseout (fn [evt] (removeinfor)))
    (evts/listen! (dom/by-id "reset") :click  (fn [evt] (resetform)))]))

;; the same as the previous sample
(defn ^:export init []
  (if (and js/document
           (.-getElementById js/document))
     (addresetlauncher)))
