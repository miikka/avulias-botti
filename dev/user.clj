(ns user
  (:require [reloaded.repl :refer [system init start stop go reset reset-all]]
            [avulias-botti.core :as bot :refer [new-system]]))

(reloaded.repl/set-init! #(new-system))
