(def project 'avulias-botti)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"dev"}
          :dependencies   '[[org.clojure/clojure "1.8.0" :scope "provided"]
                            [com.stuartsierra/component "0.3.1"]
                            [compojure "1.5.0"]
                            [environ "1.1.0"]
                            [javax.servlet/servlet-api "2.5"]
                            [morse "0.2.4" :exclusions [venantius/ultra]]
                            [reloaded.repl "0.2.3"]
                            [ring/ring-jetty-adapter "1.5.0"]
                            [ring/ring-json "0.4.0"]])

(require '[reloaded.repl :refer [system init start stop go reset reset-all]]
         'user)

(task-options!
 aot {:namespace   '#{avulias-botti.server}}
 pom {:project     project
      :version     version
      :description "Avulias Telegram-botti"
      :url         "https://github.com/miikka/avulias-botti"
      :scm         {:url "https://github.com/miikka/avulias-botti"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}}
 jar {:main        'avulias-botti.server
      :file        "avulias-botti-standalone.jar"})

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot)
          (pom)
          (uber)
          (jar)
          (sift :include #{#"avulias-botti-standalone.jar"})
          (target :dir dir))))

(require '[avulias-botti.tasks :as tasks])
(deftask setup-webhook [] (tasks/setup-webhook))
