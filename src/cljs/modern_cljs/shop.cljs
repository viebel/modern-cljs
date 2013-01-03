(ns modern-cljs.shop
  (:use-macros [c2.util :only [bind!]])
  (:require [c2.core :as c2]
            [c2.dom :as dom]
            [c2.event :as event]))

(defn calculate []
  (let [quantity (dom/val "#quantity")
        price (dom/val "#price")
        tax (dom/val "#tax")
        discount (dom/val "#discount")]
    (dom/val "#total" (-> (* quantity price)
                           (* (+ 1 (/ tax 100)))
                           (- discount)
                           (.toFixed 2)))))

(defn reset-form []
  (let [fields ["#quantity" "#price" "#tax" "#discount" "#total"]
        init ["1" "1.00" "0.0" "0.0" "0.00"]]
  (dorun (map dom/val fields init))))

(defn add-info [el name]
  (dom/append! el [:div {:id name} (str "Click to " name)]))

(defn remove-info [el]
  (dom/remove! el))

(bind! "#shoppingForm"
       [:form
        [:legend "Shopping Calculator"]
        [:fieldset
         [:div [:label {:for "quantity"} "Quantity"
                [:input#quantity {:type "number"
                                  :name "quantity"
                                  :value "1"
                                  :min "1"
                                  :required true}]]]
         [:div [:label {:for "price"} "Price Per Unit"
                [:input#price {:type "text"
                               :name "price"
                               :value "1.00"
                               :required true}]]]
         [:div [:label {:for "tax"} "Tax Rate (%)"
                [:input#tax {:type "text"
                             :name "tax"
                             :value "0.0"
                             :requried true}]]]
         [:div [:label {:for "discount"} "Discount"
                [:input#discount {:type "text"
                                  :name "discount"
                                  :value "0.0"
                                  :required true}]]]
         [:div [:label {:for "total"} "Total"
                [:input#total {:type "text"
                               :name "total"
                               :value "0.00"
                               :required true}]]]
         [:div [:input#calculateButton {:type "button"
                                         :value "Calculate"}]]
         [:div [:input#resetButton {:type "button"
                               :value "Reset"}]]]])

(event/on-raw "#calculateButton" :click calculate)
(event/on-raw "#calculateButton" :mouseover (fn [] (add-info "#shoppingForm" "calculate")))
(event/on-raw "#calculateButton" :mouseout (fn [] (remove-info "#calculate")))

(event/on-raw "#resetButton" :click reset-form)
(event/on-raw "#resetButton" :mouseover (fn [] (add-info "#shoppingForm" "reset")))
(event/on-raw "#resetButton" :mouseout (fn [] (remove-info "#reset")))
