(ns modern-cljs.connect
  (:require [clojure.browser.repl :as repl]))

;; Addressing the  BREPL connection.
(repl/connect "http://localhost:9000/repl")
