(ns ^:figwheel-no-load reagent-leaflet-sandbox.dev
  (:require
    [reagent-leaflet-sandbox.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
