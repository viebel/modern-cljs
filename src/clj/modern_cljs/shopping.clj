(ns modern-cljs.shopping
  (:require [modern-cljs.templates :refer [shopping-form]]
            [modern-cljs.shopping.validators :refer [validate-form]]))

(defn shopping [quantity price tax discount]
  (let [errors (validate-form quantity price tax discount)]
    (shopping-form quantity price tax discount errors)))

;; ;; parse-int
;; (defmulti parse-int type)
;; (defmethod parse-int java.lang.Long [n] n)
;; (defmethod parse-int java.lang.String [s] (Integer/parseInt s))

;; ;; parse-double
;; (defmulti parse-double type)
;; (defmethod parse-double java.lang.Double [d] d)
;; (defmethod parse-double java.lang.Long [n] (double n))
;; (defmethod parse-double java.lang.String [s] (Double/parseDouble s))

;; (defn- calculate [quantity price tax discount]
;;   (let [quantity (parse-int quantity)
;;         price (parse-double price)
;;         tax (parse-double tax)
;;         discount (parse-double discount)]
;;     (-> (* quantity price)
;;         (* (+ 1 (/ tax 100)))
;;         (- discount))))

;; (deftemplate shopping "public/shopping.html"
;;   [quantity price tax discount]
;;      [:input#quantity] (set-attr :value quantity)
;;      [:input#price] (set-attr :value price)
;;      [:input#tax] (set-attr :value tax)
;;      [:input#discount] (set-attr :value discount)
;;      [:input#total] (set-attr :value
;;                               (format "%,.2f" (calculate
;;                                               quantity
;;                                               price
;;                                               tax
;;                                               discount))))
