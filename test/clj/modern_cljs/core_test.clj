(ns modern-cljs.core-test
  (:use [clojure.test]
        [modern-cljs.shopping.validators]
        [modern-cljs.remotes]
        [modern-cljs.templates])
  (:require [hiccup.core :refer [html]]
            [net.cgrand.enlive-html :refer [append html-snippet set-attr]]))

(deftest validator 
  (is (= nil 
         (validate-form "1" "2.1" "3.1" "4")))
  (is (= {:quantity ["Quantity must be an integer."]}
         (validate-form "foo" "1" "1" "1")))
  (is (= {:price ["Price per Unit must be a number."]}
         (validate-form "1" "foo" "1" "1")))
  (is (= {:tax ["Tax Rate must be a number."]
          :discount ["Discount cannot be empty." "Discount must be a number."]}
         (validate-form "1" "1" "foo" "")))
  (is (= {:discount ["Discount cannot be empty." "Discount must be a number."]
          :tax ["Tax Rate cannot be empty." "Tax Rate must be a number."]
          :price ["Price per Unit cannot be empty." "Price per Unit must be a number."]
          :quantity ["Quantity cannot be empty and must be an integer." "Quantity must be an integer."]}
         (validate-form "" "" "" ""))))

(deftest shopping-calculator
  (is (= 1.0 (calculate "1" "1.00" "0.0" "0.0")))
  (is (= 6.0672 (calculate "12" "1.00" "0.56" "6.0")))
  (is (= 4.5275 (calculate "11" "0.50" "0.50" "1.0"))))

(run-tests 'modern-cljs.core-test)
