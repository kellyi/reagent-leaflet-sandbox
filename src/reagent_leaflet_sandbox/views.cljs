(ns reagent-leaflet-sandbox.views
  (:require [reagent.core :as r]
            [reitit.frontend.easy :as rfe]
            [reagent-leaflet-sandbox.store :as store]
            [reagent-leaflet-sandbox.leaflet-map :as leaflet-map]))

(defn navigation-bar
  []
  [:header.navbar.bg-primary
   [:section.navbar-section.p-2
    [:a.btn.btn-link.text-secondary
     {:href (rfe/href ::main)} "Main"]
    [:a.btn.btn-link.text-secondary
     {:href (rfe/href ::settings)} "Settings"]]])

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
