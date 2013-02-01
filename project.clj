(defproject modern-cljs "0.1.0-SNAPSHOT"
  :description "A series of tutorials to guide you in creating and setting up ClojureScript projects."
  :url "https://github.com/magomimmo/modern-cljs.git"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"

  ;; clojure source code path
  :source-paths ["src/clj" "resources"]

  :dependencies [[org.clojure/clojure "1.4.0"]
                 ;; Routing DSL for Ring CLJ
                 [compojure "1.1.5"]
                 ;; HTML renderers for CLJ and CLJS
                 [hiccup "1.0.2"]
                 [hiccups "0.2.0"]
                 ;; Server-Client rpc
                 [com.cemerick/shoreleave-remote-ring "0.0.2"]
                 [shoreleave/shoreleave-remote "0.2.2"]
                 ;; Validation library for CLJ and CLJS
                 [com.cemerick/valip "0.3.2"]
                 ;; Authorization and authentication for Ring CLJ
                 [com.cemerick/friend "0.1.3"]
                 ;; HTML templating for CLJ and CLJS
                 [enlive "1.0.1"]
                 [enfocus "1.0.0-beta2"]
                 ;; Database connection and handling to h2
                 [korma "0.3.0-RC2"]
                 [com.h2database/h2 "1.3.164"]]
                 
  :plugins [[lein-cljsbuild "0.2.10"]
            [lein-ring "0.8.2"]]

  ;; enable cljsbuild tasks support
  ;; :hooks [leiningen.cljsbuild]

  ;; ring tasks configuration with database initialization and destruction
  :ring {;; Server-Client handler
         :handler modern-cljs.core/middleware
         ;; Database initialization
         :init modern-cljs.database/create-db-connection
         ;; Drop the db
         :destroy modern-cljs.database/drop-db} 

  ;; cljsbuild tasks configuration
  :cljsbuild {:crossovers [valip.core valip.predicates modern-cljs.login.validators]
              :builds
              {:dev
               {;; clojurescript source code path
                :source-path "src/cljs"
                ;; Google Closure Compiler options
                :compiler {;; the name of emitted JS script file
                           :output-to "resources/public/js/modern_dbg.js"
                           ;; minimum optimization
                           :optimizations :whitespace
                           ;; prettyfying emitted JS
                           :pretty-print true}}
               :pre-prod
               {;; same path as above
                :source-path "src/cljs"
                :compiler {;; different JS output name
                           :output-to "resources/public/js/modern_pre.js"
                           ;; simple optimization
                           :optimizations :simple}}
               :prod
               {;; same path as above
                :source-path "src/cljs"
                :compiler {;; different JS output name
                           :output-to "resources/public/js/modern.js"
                           ;; advanced optimization
                           :optimizations :advanced}}}})
