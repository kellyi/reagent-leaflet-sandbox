(ns reagent-leaflet-sandbox.leaflet-map
  (:require [reagent.core :as r]
            [reagent-leaflet-sandbox.utils :as utils]
            [reagent-leaflet-sandbox.constants :as constants]
            [reagent-leaflet-sandbox.actions :as actions]
            [reagent-leaflet-sandbox.store :as store]))

(def leaflet-map-id "reagent-leaflet-map")
(def leaflet-map-ref (r/atom nil))

(defn set-leaflet-map-view
  [lat lng zoom]
  (when
   (some (comp not nil?) '(lat lng @leaflet-map-ref))
    (.setView @leaflet-map-ref #js [lat lng] zoom)))

(defn handle-zoom-end
  []
  (when
   (not (nil? @leaflet-map-ref))
    (actions/store-zoom-level-change (.getZoom @leaflet-map-ref))))

(defn leaflet-map-did-mount
  []
  (let [leaflet-map-component (.map js/L leaflet-map-id)]
    (do
      (reset! leaflet-map-ref leaflet-map-component)
      (set-leaflet-map-view 39.9524 -75.1636 @store/map-zoom-cursor)
      (.addTo (.tileLayer js/L constants/basemap-url
                          (clj->js {:attribution constants/basemap-attribution
                                    :maxZoom constants/max-basemap-zoom}))
              @leaflet-map-ref)
      (.on @leaflet-map-ref "zoomend" handle-zoom-end))))

(defn leaflet-map-render
  []
  [:div.map-container {:id leaflet-map-id}])

(defn leaflet-map
  []
  (r/create-class {:reagent-render leaflet-map-render
                   :component-did-mount leaflet-map-did-mount}))
