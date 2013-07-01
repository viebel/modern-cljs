(ns modern-cljs.templates
  (:require [hiccup.core :refer [html]]
            [modern-cljs.remotes :refer [calculate]]
            [net.cgrand.enlive-html :refer [deftemplate append html-snippet set-attr]]))

(defn handle-response [errors value]
  (if errors
    (append (html-snippet (html [:div [:span.error (first errors)]])))
    (set-attr :value value)))

(deftemplate shopping-form "public/shopping-dbg.html"
  [quantity price tax discount errors] 
  [:input#quantity] (handle-response (:quantity errors) quantity)
  [:input#price] (handle-response (:price errors) price)
  [:input#tax] (handle-response (:tax errors) tax)
  [:input#discount] (handle-response (:discount errors) discount)
  [:input#total] (if errors
                   (set-attr :value "0.00")
                   (set-attr :value (calculate quantity price tax discount))))
