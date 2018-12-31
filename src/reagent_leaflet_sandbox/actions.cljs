(ns reagent-leaflet-sandbox.actions
  (:require [reagent-leaflet-sandbox.store :as store]
            [reagent-leaflet-sandbox.utils :as utils]))

(defn store-zoom-level-change
  [zoom-level]
  (reset! store/map-zoom-cursor zoom-level))

(defn update-route-match
  [m]
  (reset! store/route-match-cursor m))

(defn select-layer
  [e]
  (as-> e event
    (utils/get-value event)
    (reset! store/layer-selection-cursor event)))

(defn select-year
  [e]
  (as-> e event
    (utils/get-value event)
    (reset! store/year-selection-cursor event)))
