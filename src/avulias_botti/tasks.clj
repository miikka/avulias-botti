(ns avulias-botti.tasks
  (:require
   [environ.core :refer [env]]
   [morse.api :as api]))


(defn setup-webhook []
  (api/set-webhook (env :telegram-token)
                   (str "https://avulias-botti.herokuapp.com/webhook/"
                        (env :webhook-id) "/")))
