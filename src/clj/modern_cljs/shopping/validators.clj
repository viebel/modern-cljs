(ns modern-cljs.shopping.validators
  (:require [valip.core :refer [validate]]
            [valip.predicates :refer [present? decimal-string? integer-string?]]))

(defn validate-form [quantity price tax discount]
  (validate {:quantity quantity :price price :tax tax :discount discount}
            [:quantity present? "Quantity cannot be empty and must be an integer."]
            [:quantity integer-string? "Quantity must be an integer."]
            [:price present? "Price per Unit cannot be empty."] 
            [:price decimal-string? "Price per Unit must be a number."]
            [:tax present? "Tax Rate cannot be empty."] 
            [:tax decimal-string? "Tax Rate must be a number."]
            [:discount present? "Discount cannot be empty."] 
            [:discount decimal-string? "Discount must be a number."]))
