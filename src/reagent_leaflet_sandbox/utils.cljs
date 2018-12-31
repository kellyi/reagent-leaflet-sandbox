(ns reagent-leaflet-sandbox.utils)

(defn get-value
  [e]
  (.. e -target -value))
