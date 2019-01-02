(ns reagent-leaflet-sandbox.actions
  (:require [ajax.core :refer [GET POST]]
            [reagent-leaflet-sandbox.constants :as constants]
            [reagent-leaflet-sandbox.store :as store]
            [reagent-leaflet-sandbox.utils :as utils]))

(defn store-zoom-level-change
  [zoom-level]
  (reset! store/map-zoom-cursor (js->clj zoom-level)))

(defn get-lat-lng-from-position
  [{lat "lat" lng "lng"}]
  {:lat lat :lng lng})

(defn store-bounds-change
  [bounds]
  (let [clj-bounds (js->clj bounds)
        southwest (get clj-bounds "_southWest")
        northeast (get clj-bounds "_northEast")]
    (reset! store/bounds-selection-cursor
            {:southwest (get-lat-lng-from-position southwest)
             :northeast (get-lat-lng-from-position northeast)})))

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

(defn handle-update-token
  [e]
  (do
    (as-> e event
      (utils/get-value event)
      (reset! store/token-cursor event))
    (reset! store/url-cursor "")
    (reset! store/auth-error-cursor false)))

(defn fail-submit-form
  [err]
  (do
    (.warn js/console err)
    (reset! store/auth-fetching-cursor false)
    (reset! store/auth-error-cursor true)))

(defn complete-submit-form
  [data]
  (let [url (get data "url")]
    (reset! store/auth-fetching-cursor false)
    (reset! store/url-cursor url)))

(defn submit-form
  []
  (do
    (reset! store/auth-error-cursor false)
    (reset! store/auth-fetching-cursor true)
    (POST constants/auth-url {:params {:token @store/token-cursor}
                              :handler complete-submit-form
                              :error-handler fail-submit-form
                              :format :json
                              :response-format :json})))

(defn fail-fetch-data
  [err]
  (do
    (.warn js/console err)
    (reset! store/data-fetching-cursor false)
    (reset! store/data-error-cursor true)))

(defn complete-fetch-data
  [data]
  (do
    (reset! store/data-fetching-cursor false)
    (reset! store/data-data-cursor data)))

(defn fetch-data
  []
  (let [query-string (utils/create-query-string
                      {:bbox @store/bounds-selection-cursor
                       :year @store/year-selection-cursor
                       :layer @store/layer-selection-cursor})]
    (reset! store/data-error-cursor false)
    (reset! store/data-fetching-cursor true)
    (GET (str @store/url-cursor query-string)
      {:response-format :json
       :error-handler fail-fetch-data
       :handler complete-fetch-data})))
