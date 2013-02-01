(ns modern-cljs.database
  (:require [clojure.string :refer [lower-case upper-case]]
            [clojure.java.jdbc :refer [create-table with-connection transaction]]
            [clojure.java.io :refer [file delete-file]]
            [korma.core :refer [defentity table database insert values select where]]
            [korma.db :refer [defdb]]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))

;; Connection specifications for the database 
(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2"
              :subname "resources/private/database/users"
              :user "sa"
              :password ""
              :delimiters ""
              :naming {:keys lower-case
                       :fields upper-case}})

;; Database relative path, as created by korma
(def ^:dynamic *db-file-name* (str (db-spec :subname) "." (db-spec :subprotocol) ".db"))

;; Conneting the database
(defdb users db-spec)
(defentity userstable)

;; Creating the database table
(defn create-db-table []
  (do
    (create-table "userstable"
                  [:email "VARCHAR2(50)" "UNIQUE"]
                  [:password "VARCHAR2(65)"]
                  [:role "VARCHAR2(15)"]
                  [:username "VARCHAR2(20)"])))

;; Clean up the database
(defn drop-db []
  (let [db-file (file *db-file-name*)]
    (delete-file db-file true)))

;; Start up initialization of the database. Some users are added.
(defn create-db-connection []
  (drop-db)
  (with-connection db-spec (transaction (create-db-table)))
  (insert userstable
          (values [{:email "mimmo.cosenza@gmail.com" 
                    :username "Mimmo"
                    :password (creds/hash-bcrypt "mimmo1")
                    :role "Administrator"}
                   {:email "giacomo.cosenza@sinapsi.com" 
                    :username "Giacomo"
                    :password (creds/hash-bcrypt "jack1")
                    :role "User"}])))

;; Query that load a user's credentials.
(defn load-credential-fn [email]
  (first (select userstable (where {:email email}))))