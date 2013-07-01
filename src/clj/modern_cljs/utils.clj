(ns modern-cljs.utils
  (:require [clojure.string :as str]
            [cljs.reader :refer [read-string]])
  (:refer-clojure :exclude [read-string]))

(defn parse-int [s]
  (if (re-matches #"\s*[+-]?\d+\s*" s)
    (read-string s)))

(defn parse-double [s]
  (if (re-matches #"\s*[+-]?\d+(\.\d+(M|M|N)?)?\s*" s)
    (read-string s)))

(defn parse-number [s]
  (if (and (string? s)
           (re-matches #"\s*[+-]?\d+(\.\d+M|M|N)?\s*" s))
    (read-string s)))
