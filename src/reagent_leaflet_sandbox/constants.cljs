(ns reagent-leaflet-sandbox.constants)

(def basemap-url
  (str "https://server.arcgisonline.com/arcgis/rest/services/World_Imagery/"
       "MapServer/tile/{z}/{y}/{x}"))
(def basemap-attribution "Tiles &copy; Esri")
(def max-basemap-zoom 19)
(def initial-basemap-zoom 15)
