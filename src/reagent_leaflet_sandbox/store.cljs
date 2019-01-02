(ns reagent-leaflet-sandbox.store
  (:require [reagent.core :as r]
            [alandipert.storage-atom :refer [local-storage]]
            [reagent-leaflet-sandbox.constants :as constants]))

(defonce unstored-app-state
  (r/atom {:routing {:match nil}
           :auth {:fetching false
                  :error false}
           :data {:fetching false
                  :error false
                  :data nil}
           :zoom constants/initial-basemap-zoom
           :bounds nil}))

(defonce stored-app-state
  (local-storage
   (r/atom {:layer-id 1
            :year 2019
            :token ""
            :url ""})
   :stored-state))

(defonce map-zoom-cursor (r/cursor unstored-app-state [:zoom]))
(defonce route-match-cursor (r/cursor unstored-app-state [:routing :match]))
(defonce layer-selection-cursor (r/cursor stored-app-state [:layer-id]))
(defonce year-selection-cursor (r/cursor stored-app-state [:year]))
(defonce bounds-selection-cursor (r/cursor unstored-app-state [:bounds]))

(defonce auth-fetching-cursor (r/cursor unstored-app-state [:auth :fetching]))
(defonce auth-error-cursor (r/cursor unstored-app-state [:auth :error]))
(defonce token-cursor (r/cursor stored-app-state [:token]))
(defonce url-cursor (r/cursor stored-app-state [:url]))

(defonce data-fetching-cursor (r/cursor unstored-app-state [:data :fetching]))
(defonce data-error-cursor (r/cursor unstored-app-state [:data :error]))
(defonce data-data-cursor (r/cursor unstored-app-state [:data :data]))

(defn log-state
  [logger atom prev next]
  (do
    (.log js/console logger)
    (.log js/console atom)))

(def logger
  (when js/goog.DEBUG
    (add-watch stored-app-state :stored-state-logger log-state)
    (add-watch unstored-app-state :unstored-state-logger log-state)))
