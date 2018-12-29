(ns reagent-leaflet-sandbox.views
  (:require [reagent.core :as r]
            [reagent-leaflet-sandbox.leaflet-map :as leaflet-map]))

(defn main
  []
  [:div.main-container
   [leaflet-map/leaflet-map]])
