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
  (api/send-text +token+ chat-id "Osaan seuraavat asiat: /huoltomies"))

(defn logo-rauma [chat-id]
  (send-sticker +token+ chat-id (io/file "resources/rauma.webp")))

(handlers/defhandler bot-api
  (handlers/command "huoltomies" {{chat-id :id} :chat} (huoltomies chat-id))
  (handlers/command "apua" {{chat-id :id} :chat} (apua chat-id))
  (handlers/command "help" {{chat-id :id} :chat} (apua chat-id))
  (handlers/command "logorauma" {{chat-id :id} :chat} (logo-rauma chat-id))
  (handlers/message message (println "Got message" message)))

(defn start []
  (polling/start +token+ bot-api))

(defn stop [channel]
  (polling/stop channel))
