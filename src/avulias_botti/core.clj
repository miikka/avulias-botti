(ns avulias-botti.core
  (:require
   [com.stuartsierra.component :as component]
   [environ.core :refer [env]]
   [morse.api :as api]
   [morse.handlers :as handlers]
   [morse.polling :as polling]
   [clojure.java.io :as io]))

(def +token+ (env :telegram-token))

(defn huoltomies [chat-id]
  (api/send-text +token+ chat-id "Satakuntatalon huoltopäivystys puh. 050-2861"))

(defn apua [chat-id]
  (api/send-text +token+ chat-id
                 "Osaan seuraavat asiat: /anssikela, /huoltomies, /lansimetro, /logorauma"))

(defn logo-rauma [chat-id]
  (api/send-sticker +token+ chat-id (io/file "resources/rauma.webp")))

(defn reply-to [message body]
  (let [chat-id (-> message :chat :id)
        options {:parse_mode "Markdown"}]
    (api/send-text +token+ chat-id options body)))

(defn anssi-kela [message]
  (let [name (-> message :from :first_name)]
    (reply-to message (format "%s, Anssi Kela -nimesi on _%s Kela_." name name))))

(defn lansimetro [message]
  (reply-to message "Onko Länsimetro valmis? *Joo!*"))

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

(defrecord LongPolling [channel]
  component/Lifecycle
  (start [component]
    (assoc component :channel (start)))
  (stop [component]
    (stop channel)
    (assoc component :channel nil)))

(defn new-system [] (component/system-map :polling (map->LongPolling {})))
