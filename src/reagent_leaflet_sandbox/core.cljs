(ns reagent-leaflet-sandbox.core
  (:require
   [reagent.core :as r]
   [reagent-leaflet-sandbox.views :as views]))

(defn mount-root []
  (r/render [views/main] (.getElementById js/document "root")))

(defn init! []
  (mount-root))
