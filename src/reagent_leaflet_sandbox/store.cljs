(ns reagent-leaflet-sandbox.store
  (:require [reagent.core :as r]
            [alandipert.storage-atom :refer [local-storage]]
            [reagent-leaflet-sandbox.constants :as constants]))

(defonce unstored-app-state
  (r/atom {:routing {:match nil}}))

(defonce stored-app-state
  (local-storage
   (r/atom {:zoom constants/initial-basemap-zoom})
   :stored-state))

(defonce map-zoom-cursor (r/cursor stored-app-state [:zoom]))
(defonce route-match-cursor (r/cursor unstored-app-state [:routing :match]))

(defn log-state
  [logger atom prev next]
  (do
    (.log js/console logger)
    (.log js/console atom)))

(def logger
  (when ^boolean js/goog.DEBUG
    (do
      (add-watch stored-app-state :stored-state-logger log-state)
      (add-watch unstored-app-state :unstored-state-logger log-state))))
