(defproject go-rest-cucumber-tests "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [slingshot "0.12.2"]
                 [cheshire "5.13.0"]
                 [clj-http "3.13.0"]]
  :plugins [[lein-cucumber "1.0.2"]]
  :repl-options {:init-ns go-rest-cucumber-tests.core})
