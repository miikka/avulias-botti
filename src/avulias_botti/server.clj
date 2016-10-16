(ns avulias-botti.server
  (:require [avulias-botti.core :as core]
            [environ.core :refer [env]]
            [compojure.core :refer [defroutes ANY POST]]
            [compojure.handler :refer [api]]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.util.response :refer [response]])
  (:gen-class))

(defn hook [body]
  (core/bot-api body)
  (response "OK"))

(defroutes app
  (POST "/webhook/:id/" {{id :id} :params body :body}
       (when (= id (env :webhook-id))
         (hook body)))
  (ANY "*" [] (route/not-found "Not found :(")))

(defn -main [& [port]]
  (if (= port "--check")
    (prn ":ok")
    (let [port (Integer. (or port (env :port) 5000))]
      (run-jetty (-> (api #'app) (wrap-json-body {:keywords? true})) {:port port}))))
