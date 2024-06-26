(ns go-rest-cucumber-tests.utils
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn datatable->maps
  "Converts a Cucumber DataTable to a list of maps."
  [datatable]
  (let [rows (.raw datatable)
        headers (map keyword (first rows))
        data (rest rows)]
    (map #(zipmap headers %) data)))

(defn load-edn
  "Load edn from an io/reader source (filename or io/resource)."
  [source]
  (try
    (with-open [r (io/reader (str "resources/" source))]
      (edn/read (java.io.PushbackReader. r)))

    (catch java.io.IOException e
      (printf "Couldn't open '%s': %s\n" source (.getMessage e)))
    (catch RuntimeException e
      (printf "Error parsing edn file '%s': %s\n" source (.getMessage e)))))

(def api-token
  (-> (load-edn "secrets.edn") :api-token))

(defn equal-collections? [coll1 coll2]
  "Compare collections respecting duplicates and ignoring order of the elements"
  (= (frequencies coll1) (frequencies coll2)))
