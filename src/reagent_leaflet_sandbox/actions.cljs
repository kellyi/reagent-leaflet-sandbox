(ns reagent-leaflet-sandbox.actions
  (:require [reagent-leaflet-sandbox.store :as store]))

(defn store-zoom-level-change
  [zoom-level]
  (reset! store/map-zoom-cursor zoom-level))