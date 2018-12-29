(ns reagent-leaflet-sandbox.store
  (:require [reagent.core :as r]
            [alandipert.storage-atom :refer [local-storage]]
            [reagent-leaflet-sandbox.constants :as constants]))

(def unstored-app-state
  (r/atom {:hello "world"}))

(def stored-app-state
  (local-storage
   (r/atom {:zoom constants/initial-basemap-zoom})
   :stored-state))

(def map-zoom-cursor (r/cursor stored-app-state [:zoom]))
