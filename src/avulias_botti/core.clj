(ns avulias-botti.core
  (:require
   [environ.core :refer [env]]
   [morse.api :as api]
   [morse.handlers :as handlers]
   [morse.polling :as polling]))

(def +token+ (env :telegram-token))

(defn huoltomies [chat-id]
  (api/send-text +token+ chat-id "Satakuntatalon huoltopäivystys puh. 050-2861"))

(defn apua [chat-id]
  (api/send-text +token+ chat-id "Osaan seuraavat asiat: /huoltomies"))

(handlers/defhandler bot-api
  (handlers/command "huoltomies" {{chat-id :id} :chat} (huoltomies chat-id))
  (handlers/command "apua" {{chat-id :id} :chat} (apua chat-id))
  (handlers/command "help" {{chat-id :id} :chat} (apua chat-id))
  (handlers/message message (println "Got messagke" message)))


(defn start []
  (polling/start +token+ bot-api))

(defn stop [channel]
  (polling/stop channel))
