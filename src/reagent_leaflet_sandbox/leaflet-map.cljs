(ns reagent-leaflet-sandbox.leaflet-map
  (:require [reagent.core :as r]
            [reagent.dom.server :refer [render-to-string]]
            [reagent-leaflet-sandbox.utils :as utils]
            [reagent-leaflet-sandbox.constants :as constants]
            [reagent-leaflet-sandbox.actions :as actions]
            [reagent-leaflet-sandbox.store :as store]
            [reagent-leaflet-sandbox.popup-cards :as cards]))

(defonce leaflet-map-id "reagent-leaflet-map")
(defonce zoomend "zoomend")
(defonce moveend "moveend")
(def leaflet-map-ref (r/atom nil))
(def geojson-layer (r/atom nil))

(defn set-leaflet-map-view
  [lat lng zoom]
  (when
   (utils/some-are-not-nil? '(lat lng @leaflet-map-ref))
    (.setView @leaflet-map-ref #js [lat lng] zoom)))

(defn handle-zoom-end
  []
  (when
   (not (nil? @leaflet-map-ref))
    (actions/store-zoom-level-change (.getZoom @leaflet-map-ref))))

(defn handle-move-end
  []
  (when
   (not (nil? @leaflet-map-ref))
    (actions/store-bounds-change (.getBounds @leaflet-map-ref))))

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
      (.on @leaflet-map-ref zoomend handle-zoom-end)
      (.on @leaflet-map-ref moveend handle-move-end)
      (actions/store-bounds-change (.getBounds @leaflet-map-ref)))))

(defn maybe-remove-geojson-layer
  []
  (cond
    (nil? @geojson-layer) nil
    (nil? @leaflet-map-ref) nil
    (not (.hasLayer @leaflet-map-ref @geojson-layer)) (reset! geojson-layer nil)
    :else
    (do
      (.removeLayer @leaflet-map-ref @geojson-layer)
      (reset! geojson-layer nil))))

(defn create-circle-marker
  [feature latlng]
  (let [marker-options (clj->js {"radius" 8
                                 "fillColor" "#ff7800"
                                 "opacity" 1
                                 "stroke" false
                                 "fillOpacity" 0.8})]
    (.circleMarker js/L latlng (clj->js marker-options))))

(defonce create-card-for-layer {"0" cards/create-complaint-card
                                "1" cards/create-building-card
                                "2" cards/create-plumbing-card})

(defn create-element-for-popup
  [feature]
  (let [create-card-for-feature
        (get create-card-for-layer (str @store/layer-selection-cursor))]
    (render-to-string (create-card-for-feature feature))))

(defn create-popups-for-layer
  [feature layer]
  (.bindPopup layer (create-element-for-popup feature)))

(defn create-geojson-layer
  [data]
  (let [options (clj->js {"pointToLayer" create-circle-marker
                          "onEachFeature" create-popups-for-layer})]
    (.geoJSON js/L (clj->js data) options)))

(defn maybe-add-new-geojson-layer
  [data]
  (when
   (utils/some-are-not-nil? '(@leaflet-map-ref data))
    (let [new-geojson-layer (create-geojson-layer data)]
      (do
        (reset! geojson-layer new-geojson-layer)
        (.addTo @geojson-layer @leaflet-map-ref)))))

(defn leaflet-map-did-update
  [this prev-props]
  (let [data (:data (r/props this))]
    (do
      (maybe-remove-geojson-layer)
      (maybe-add-new-geojson-layer data))))

(defn leaflet-map-render
  []
  [:div.map-container {:id leaflet-map-id}])

(defn leaflet-map-component
  []
  (r/create-class {:reagent-render leaflet-map-render
                   :component-did-mount leaflet-map-did-mount
                   :component-did-update leaflet-map-did-update}))

(defn leaflet-map
  []
  [leaflet-map-component {:data @store/data-data-cursor}])
