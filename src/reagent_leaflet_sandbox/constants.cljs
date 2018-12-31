(ns reagent-leaflet-sandbox.constants)

(def basemap-url
  (str "https://server.arcgisonline.com/arcgis/rest/services/World_Imagery/"
       "MapServer/tile/{z}/{y}/{x}"))
(def basemap-attribution "Tiles &copy; Esri")
(def max-basemap-zoom 19)
(def initial-basemap-zoom 15)

(defonce layers {:building-permits {:name "Building permits" :id 1}
                 :plumbing-permits {:name "Plumbing permits" :id 2}
                 :complaints {:name "Complaints" :id 0}})

(defonce years (reverse (range 2012 2020)))
