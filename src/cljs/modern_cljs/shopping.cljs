(ns modern-cljs.shopping
<<<<<<< HEAD
  (:require-macros [hiccups.core :as hsc])
  (:require [domina :as dom]
            [domina.events :as evts]
            [domina.xpath :as xdom]
            [hiccups.runtime :as hsrt]))
=======
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
            [domina.events :as ev]))
>>>>>>> 9b6721a60e72613c72c2d3375546c441f46a5c0d

(defn calculate []
  (let [quantity (dom/value (dom/by-id "quantity"))
        price (dom/value (dom/by-id "price"))
        tax (dom/value (dom/by-id "tax"))
        discount (dom/value (dom/by-id "discount"))]
    (dom/set-value! (dom/by-id "total") (-> (* quantity price)
<<<<<<< HEAD
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
=======
                                            (* (+ 1 (/ tax 100)))
                                            (- discount)
                                            (.toFixed 2)))))

(defn add-help []
  (dom/append! (dom/by-id "shoppingForm")
               (h/html [:div.help "Click to calculate"])))

(defn remove-help []
  (dom/destroy! (dom/by-class "help")))

(defn ^:export init []
  (when (and js/document
             (aget js/document "getElementById"))
    (ev/listen! (dom/by-id "calc") :click calculate)
    (ev/listen! (dom/by-id "calc") :mouseover add-help)
    (ev/listen! (dom/by-id "calc") :mouseout remove-help)))
>>>>>>> 9b6721a60e72613c72c2d3375546c441f46a5c0d
