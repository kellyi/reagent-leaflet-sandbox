(ns reagent-leaflet-sandbox.popup-cards
  (:require [reagent.core :as r]
            [oops.core :refer [oget]]))

(defonce building "Building")
(defonce plumbing "Plumbing")

(defn make-address-string
  [address unit zip]
  (cond
    (nil? unit) (str address ", " zip)
    :else (str address ", " unit ", " zip)))

(defn make-ms-timestamp-readable
  [ts]
  (cond
    (not (nil? ts)) (.toLocaleDateString (js/Date. ts))
    :else nil))

(defn create-non-complaint-card
  [title feature]
  (let [permit-issue-date (oget feature "properties.PERMITISSUEDATE")
        address (oget feature "properties.ADDRESS")
        unit (oget feature "properties.UNIT")
        zip (oget feature "properties.ZIP")
        status (oget feature "properties.PERMITSTATUS")
        permit-desc (oget feature "properties.DESCRIPTIONOFWORK")
        type-of-work (oget feature "properties.TYPEOFWORK")
        readable-issue-date (make-ms-timestamp-readable permit-issue-date)]
    [:div.card
     [:div.card-header
      [:div.card-title.h5 (str title " Permit")]
      [:div.card-subtitle.text-gray readable-issue-date]]
     [:div.card-body
      [:div.text-bold "Address"]
      [:div.text-normal (make-address-string address unit zip)]]
     [:div.card-body
      [:div.text-bold "Description"]
      [:div.text-normal permit-desc]]
     [:div.card-body
      [:div.text-bold "Type of Work"]
      [:div.text-normal type-of-work]]
     [:div.card-body
      [:div.text-bold "Status"]
      [:div.text-normal status]]]))

(def create-building-card (partial create-non-complaint-card building))
(def create-plumbing-card (partial create-non-complaint-card plumbing))

(defn create-complaint-card
  [feature]
  (let [call-date (oget feature "properties.SR_CALLDATE")
        address (oget feature "properties.ADDRESS")
        unit (oget feature "properties.UNIT")
        zip (oget feature "properties.ZIP")
        cust-comments (oget feature "properties.CUSTCOMMENTS")
        call-comments (oget feature "properties.CALLCOMMENTS")
        resolution-date (oget feature "properties.SR_RESOLUTIONDATE")
        readable-call-date (make-ms-timestamp-readable call-date)
        readable-resolution-date (make-ms-timestamp-readable resolution-date)]
    [:div.card
     [:div.card-header
      [:div.card-title.h5 "Complaint"]
      [:div.card-subtitle.text-gray readable-call-date]]
     [:div.card-body
      [:div.text-bold "Address"]
      [:div.text-normal (make-address-string address unit zip)]]
     [:div.card-body
      [:div.text-bold "Comments"]
      (when (not (nil? cust-comments))
        [:div.text-normal cust-comments])
      (when (not (nil? call-comments))
        [:div.text-normal call-comments])]
     (when (not (nil? readable-resolution-date))
       [:div.card-body
        [:div.text-bold "Resolution Date"]
        [:div.text-normal readable-resolution-date]])]))
