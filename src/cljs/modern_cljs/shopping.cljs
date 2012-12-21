(ns modern-cljs.shopping
  (:require-macros [hiccups.core :as hsc])
  (:require [domina :as dom]
            [domina.events :as evts]
            [domina.xpath :as xdom]
            [hiccups.runtime :as hsrt]))

(defn calculate []
  (let [quantity (dom/value (dom/by-id "quantity"))
        price (dom/value (dom/by-id "price"))
        tax (dom/value (dom/by-id "tax"))
        discount (dom/value (dom/by-id "discount"))]
    (dom/set-value! (dom/by-id "total") (-> (* quantity price)
                                    (* (+ 1 (/ tax 100)))
                                    (- discount)
                                    (.toFixed 2)))
    false))

(defn appendinfo []
  (dom/append! (xdom/xpath "//body/form") (hsc/html [:div {:id "txtcalc"} "Click to calculate"])))

(defn removeinfo []
  (dom/destroy! (dom/by-id "txtcalc")))

(defn addcalclauncher []
  (doall
   [(evts/listen! (dom/by-id "calculate") :mouseover (fn [evt] (appendinfo)))
    (evts/listen! (dom/by-id "calculate") :mouseout (fn [evt] (removeinfo)))
    (evts/listen! (dom/by-id "calculate") :click  (fn [evt] (calculate)))]))

;; the same as the previous sample
(defn ^:export init []
  (if (and js/document
           (.-getElementById js/document))
    (addcalclauncher)))
