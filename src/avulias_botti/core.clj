(ns avulias-botti.core
  (:require
   [environ.core :refer [env]]
   [morse.api :as api]
   [morse.handlers :as handlers]
   [morse.polling :as polling]
   [clojure.java.io :as io]))

(def +token+ (env :telegram-token))

(defn send-sticker [token chat-id sticker]
  (api/assert-file-type sticker ["webp"])
  (api/send-file token chat-id {} sticker "/sendSticker" "sticker" "sticker.webp"))

(defn huoltomies [chat-id]
  (api/send-text +token+ chat-id "Satakuntatalon huoltopäivystys puh. 050-2861"))

(defn apua [chat-id]
  (api/send-text +token+ chat-id
                 "Osaan seuraavat asiat: /anssikela, /huoltomies, /logorauma"))

(defn logo-rauma [chat-id]
  (send-sticker +token+ chat-id (io/file "resources/rauma.webp")))

(defn reply-to [message body]
  (let [chat-id (-> message :chat :id)
        options {:parse_mode "Markdown"}]
    (api/send-text +token+ chat-id options body)))

(defn anssi-kela [message]
  (let [name (-> message :from :first_name)]
    (reply-to message (format "%s, Anssi Kela -nimesi on _%s Kela_." name name))))

(defn lansimetro [message]
  (reply-to message "Onko Länsimetro valmis? *Ei*."))

(handlers/defhandler bot-api
  (handlers/command "huoltomies" {{chat-id :id} :chat} (huoltomies chat-id))
  (handlers/command "apua" {{chat-id :id} :chat} (apua chat-id))
  (handlers/command "help" {{chat-id :id} :chat} (apua chat-id))
  (handlers/command "logorauma" {{chat-id :id} :chat} (logo-rauma chat-id))
  (handlers/command "anssikela" message (anssi-kela message))
  (handlers/command "lansimetro" message (lansimetro message))
  (handlers/message message (println "Got message" message)))

(defn start []
  (polling/start +token+ bot-api))

(defn stop [channel]
  (polling/stop channel))
