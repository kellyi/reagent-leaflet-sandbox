(ns reagent-leaflet-sandbox.core
  (:require
   [reagent.core :as r]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]
   [reitit.coercion :as rc]
   [reitit.coercion.schema :as rsc]
   [reagent-leaflet-sandbox.actions :as actions]
   [reagent-leaflet-sandbox.views :as views]))

(defn mount-root
  []
  (r/render [views/main] (.getElementById js/document "root")))

(defn init! []
  (rfe/start!
   (rf/router views/routes {:data {:coercion rsc/coercion}})
   actions/update-route-match
   {:use-fragment ^boolean js/goog.DEBUG})
  (mount-root))
