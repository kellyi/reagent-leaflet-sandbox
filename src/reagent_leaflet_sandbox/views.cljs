(ns reagent-leaflet-sandbox.views
  (:require [reagent.core :as r]
            [reitit.frontend.easy :as rfe]
            [reagent-leaflet-sandbox.actions :as actions]
            [reagent-leaflet-sandbox.constants :as constants]
            [reagent-leaflet-sandbox.store :as store]
            [reagent-leaflet-sandbox.utils :as utils]
            [reagent-leaflet-sandbox.leaflet-map :as leaflet-map]))

(defn layer-selector
  []
  (let [opts (map (fn
                    [{id :id name :name}]
                    ^{:key id} [:option {:id id :value id} name])
                  (vals constants/layers))]
    [:div.form-group.m-1
     [:select.form-select.text-dark {:on-change actions/select-layer
                                     :value @store/layer-selection-cursor}
      opts]]))

(defn year-selector
  []
  (let [opts (map (fn
                    [year]
                    ^{:key year} [:option {:id year :value year} year])
                  constants/years)]
    [:div.form-group.m-1
     [:select.form-select.text-dark {:on-change actions/select-year
                                     :value @store/year-selection-cursor}
      opts]]))

(defn navigation-bar
  []
  [:header.navbar.bg-primary
   [:section.navbar-section.p-2
    [:a.btn.btn-link.text-secondary
     {:href (rfe/href ::main)} "Main"]
    [:a.btn.btn-link.text-secondary
     {:href (rfe/href ::settings)} "Settings"]]
   [:section.navbar-section.p-2
    [:div.input-group.input-inline
     [layer-selector]
     [year-selector]]]])

(defn settings
  []
  [:p.p-2 "Settings"])

(def routes
  [["/"
    {:name ::main
     :view leaflet-map/leaflet-map}]

   ["/settings"
    {:name ::settings
     :view settings}]])

(defn main
  []
  [:div.main-container
   [navigation-bar]
   (if @store/route-match-cursor
     (let [view (:view (:data @store/route-match-cursor))]
       [view @store/route-match-cursor]))])
