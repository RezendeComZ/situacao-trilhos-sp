(ns situacao-metro-sp.cptm
  (:require [clj-http.client :as http]
            [clojure.string :as string]
            [hickory.core :as html]))

(def CPTM-URL      "https://www.cptm.sp.gov.br")
(def LINHAS-CPTM [{:nome "LILÁS"
                   :numero 5}
                  {:nome "RUBI"
                   :numero 7}
                  {:nome "DIAMANTE"
                   :numero 8}
                  {:nome "ESMERALDA"
                   :numero 9}
                  {:nome "TURQUESA"
                   :numero 10}
                  {:nome "CORAL"
                   :numero 11}
                  {:nome "SAFIRA"
                   :numero 12}
                  {:nome "JADE"
                   :numero 13}])

(def vetor-html-cptm
  (try
    (let [content (http/get CPTM-URL)
          body    (:body content)
          parse   (html/as-hiccup (html/parse body))]
      (flatten (vec parse)))
    (catch Exception e
      (prn "@@@@ " (type e))
      (let [msg-erro {:msg-erro (str "Erro ao capturar HTML: " e)}]
        (prn msg-erro)
        msg-erro))))

(defn situacao-linha-cptm [linha]
  (try
    (nth vetor-html-cptm (+ (.indexOf vetor-html-cptm linha) 3))
    (catch Exception e
      (let [msg-erro {:msg-erro (str "Erro ao converter o HTML da CPTM: " e)}]
        (prn msg-erro)
        msg-erro))))

(defn mapa-unica-linha-cptm [linha]
  (let [nome                (:nome linha)
        nome-lowercase      (string/lower-case nome)
        situacao-linha-cptm (situacao-linha-cptm nome)]
    {:nome     (str (first nome) (apply str (rest nome-lowercase)))
     :numero   (:numero linha)
     :situacao (if (:msg-erro situacao-linha-cptm)
                 "Erro ao obter status da linha"
                 situacao-linha-cptm)}))

(def mapa-todas-linhas-cptm
  (merge (map mapa-unica-linha-cptm LINHAS-CPTM)))