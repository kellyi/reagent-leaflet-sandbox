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
  (cond
    (nil? lat) nil
    (nil? lng) nil
    (nil? @leaflet-map-ref) nil
    :else (.setView @leaflet-map-ref #js [lat lng] zoom)))

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
      (.on @leaflet-map-ref "zoomend"
           (fn
             []
             (actions/store-zoom-level-change
              (.getZoom @leaflet-map-ref)))))))

(defn leaflet-map-render
  []
  [:div.map-container {:id leaflet-map-id}])

(defn leaflet-map
  []
  (r/create-class {:reagent-render leaflet-map-render
                   :component-did-mount leaflet-map-did-mount}))
