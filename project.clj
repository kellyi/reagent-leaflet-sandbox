(defproject reagent-leaflet-sandbox "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main ^:skip-aot build.add-hash

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.439"]
                 [reagent "0.8.1"]
                 [alandipert/storage-atom "1.2.4"]
                 [cljs-ajax "0.8.0"]
                 [binaryage/oops "0.6.4"]
                 [metosin/reitit-spec "0.2.10"]
                 [metosin/reitit-schema "0.2.10"]
                 [metosin/reitit-frontend "0.2.10"]]

  :plugins [[lein-cljsbuild "1.1.5"]
            [lein-figwheel "0.5.16"]
            [lein-kibit "0.1.6"]
            [lein-bikeshed "0.2.0"]
            [lein-cljfmt "0.6.3"]]

  :clean-targets ^{:protect false}
  [:target-path
   [:cljsbuild :builds :app :compiler :output-dir]
   [:cljsbuild :builds :app :compiler :output-to]]

  :resource-paths ["public"]

  :figwheel {:http-server-root "."
             :nrepl-port 7002
             :nrepl-middleware [cider.piggieback/wrap-cljs-repl]
             :css-dirs ["public/css"]}

  :cljsbuild {:builds {:app
                       {:source-paths ["src" "env/dev/cljs"]
                        :compiler
                        {:main "reagent-leaflet-sandbox.dev"
                         :output-to "public/js/app.js"
                         :output-dir "public/js/out"
                         :asset-path   "js/out"
                         :source-map true
                         :optimizations :none
                         :externs ["externs.js"]
                         :pretty-print  true
                         :preloads [devtools.preload]
                         :external-config {:devtools/config {:features-to-install :all}}}
                        :figwheel
                        {:on-jsload "reagent-leaflet-sandbox.core/mount-root"}}
                       :release
                       {:source-paths ["src" "env/prod/cljs"]
                        :compiler
                        {:output-to "public/js/app.js"
                         :output-dir "public/js/release"
                         :asset-path   "js/out"
                         :optimizations :advanced
                         :fingerprint true
                         :closure-defines {"goog.DEBUG" false}
                         :externs ["externs.js"]
                         :pretty-print false}}}}


  :aliases {"package" ["do" "clean" ["cljsbuild" "once" "release"] "add-hash"]
            "lint" ["do" ["cljfmt" "fix"] "bikeshed" "kibit"]
            "add-hash" ["run"]}

  :profiles {:dev {:source-paths ["src" "env/dev/clj"]
                   :dependencies [[binaryage/devtools "0.9.10"]
                                  [figwheel-sidecar "0.5.16"]]}})
