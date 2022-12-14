(defproject situacao-metro-sp "0.1.0-SNAPSHOT"
  :description "Alimenta tópicos no Kafka sobre a situação das linhas do metrô de SP"
  :url "https://github.com/RezendeComZ/situacao-metro-sp"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [cheshire "5.11.0"]
                 [hickory "0.7.1"]
                 [clj-http "3.12.3"]]
  :main ^:skip-aot situacao-metro-sp.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
