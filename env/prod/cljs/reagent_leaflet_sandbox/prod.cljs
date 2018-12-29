(ns reagent-leaflet-sandbox.prod
  (:require
    [reagent-leaflet-sandbox.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
