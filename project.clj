(defproject modern-cljs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
<<<<<<< HEAD
  ; clojure source code path
  :source-paths ["src/clj"]
=======

  ;; clojure source code path
  :source-paths ["src/clj"]

>>>>>>> 9b6721a60e72613c72c2d3375546c441f46a5c0d
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.3"]
<<<<<<< HEAD
                 ; domina dependency
                 [domina "1.0.0"]
                 ; hiccups dependency
                 [hiccups "0.1.1"]
                 ; c2 dependency
                 [com.keminglabs/c2 "0.2.1"]
                 ; shoreleave dependencies
                 [com.cemerick/shoreleave-remote-ring "0.0.2"]
                 [shoreleave/shoreleave-remote "0.2.2"]]
  :plugins [; cljsbuild plugin
            [lein-cljsbuild "0.2.9"]
            ; ring plugin
            [lein-ring "0.7.5"]]
  ; ring tasks configuration
  :ring {:handler modern-cljs.core/app}	
  ; cljsbuild tasks configuration
=======
                 [domina "1.0.0"]
                 [hiccups "0.1.1"]]

  :plugins [[lein-cljsbuild "0.2.10"]
            [lein-ring "0.7.5"]]

  ;; ring tasks configuration
  :ring {:handler modern-cljs.core/handler}

  ;; cljsbuild tasks configuration
>>>>>>> 9b6721a60e72613c72c2d3375546c441f46a5c0d
  :cljsbuild {:builds
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
                           :optimizations :whitespace}}
               :prod
               {;; same path as above
                :source-path "src/cljs"

                :compiler {;; different JS output name
                           :output-to "resources/public/js/modern.js"
<<<<<<< HEAD
                           ;advanced optimization
=======

                           ;; advanced optimization
>>>>>>> 9b6721a60e72613c72c2d3375546c441f46a5c0d
                           :optimizations :advanced}}
               }})
