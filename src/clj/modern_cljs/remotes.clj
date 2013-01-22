(ns modern-cljs.remotes
  (:require [cemerick.shoreleave.rpc :refer [defremote wrap-rpc]]))

;; remote calculator for the shopping form
(defremote calculate [quantity price tax discount]
  (-> (* quantity price)
      (* (+ 1 (/ tax 100)))
      (- discount)))