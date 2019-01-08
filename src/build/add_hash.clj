(ns build.add-hash
 (:gen-class))

(def index-path "./public/index.html")
(def two-hundred-path "./public/200.html")
(def manifest-path "./public/js/manifest.edn")
(def app-js-path "public/js/app.js")
(def app-js-file-to-replace "js/app.js")
(def replacement-regex
  (java.util.regex.Pattern/compile app-js-file-to-replace))

(defn -main
  "Add hash from the latest package bundle to the html files"
  [& args]
  (let [manifest (read-string (slurp manifest-path))
        hashed-filename (subs (get manifest app-js-path) 7)

        index-file (slurp index-path)
        adjusted-index (clojure.string/replace index-file
                                               replacement-regex
                                               hashed-filename)]
    (do
      (spit index-path adjusted-index)
      (spit two-hundred-path adjusted-index))))

