(ns reagent-leaflet-sandbox.actions
  (:require [ajax.core :refer [POST]]
            [reagent-leaflet-sandbox.constants :as constants]
            [reagent-leaflet-sandbox.store :as store]
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
    (do
      (reset! store/auth-fetching-cursor false)
      (reset! store/url-cursor url))))

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
  []
  (do
    (reset! store/data-fetching-cursor false)))

(defn fetch-data
  []
  (do
    (reset! store/data-error-cursor false)
    (reset! store/data-fetching-cursor true)
    (complete-fetch-data)))
