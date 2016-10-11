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

(def logorauma-text (str
  "```text\n"
  "|\\_-^-_/|\n"
  "| r | r |\n"
  "|-------|\n"
  "| r | r |\n"
  " `~~v~~´\n"
  "```"))

(defn logorauma [chat-id]
  (api/send-text +token+ chat-id {:parse_mode "Markdown"} logorauma-text))

(handlers/defhandler bot-api
  (handlers/command "huoltomies" {{chat-id :id} :chat} (huoltomies chat-id))
  (handlers/command "apua" {{chat-id :id} :chat} (apua chat-id))
  (handlers/command "logorauma" {{chat-id :id} :chat} (logorauma chat-id))
  (handlers/command "help" {{chat-id :id} :chat} (apua chat-id))
  (handlers/message message (println "Got message" message)))


(defn start []
  (polling/start +token+ bot-api))

(defn stop [channel]
  (polling/stop channel))
