(ns reagent-leaflet-sandbox.utils)

(defn get-value
  [e]
  (.. e -target -value))

(defn make-esri-geom-from-bbox
  [{southwest :southwest northeast :northeast}]
  (let [xmin (:lng southwest)
        ymin (:lat southwest)
        xmax (:lng northeast)
        ymax (:lat northeast)]
    (str "&geometryType=esriGeometryEnvelope"
         "&geometry=" xmin "," ymin "," xmax "," ymax)))

(defonce outfields
  {"0"
   (str "ADDRESS%2CUNIT%2CZIP%2CCUSTCOMMENTS%2CCALLCOMMENTS%2CSR_CALLDATE%2C"
        "SR_RESOLUTIONDATE")
   "1"
   (str "ADDRESS%2CUNIT%2CZIP%2CPERMITNUMBER%2CPERMITTYPE%2CPERMITDESCRIPTION"
        "%2CTYPEOFWORK%2CDESCRIPTIONOFWORK%2CPERMITISSUEDATE%2CFINALLEDDATE"
        "%2CPERMITSTATUS%2CSTATUS")
   "2"
   (str "ADDRESS%2CUNIT%2CZIP%2CPERMITTYPE%2CPERMITDESCRIPTION%2C"
        "PERMITISSUEDATE%2CFINALLEDDATE%2CPERMITSTATUS%2CSTATUS%2CTYPEOFWORK"
        "%2CDESCRIPTIONOFWORK")})

(defn create-query-string
  [{bbox :bbox layer :layer year :year}]
  (str "/" layer "/" "query?where=1=1"
       "&returnGeometry=true" "&inSR=4326"
       "&f=pgeojson" "&outSR=4326"
       "&outFields=" (get outfields (str layer))
       (make-esri-geom-from-bbox bbox)))

(defn some-are-not-nil?
  [l]
  (some (comp not nil?) l))
