(ns rss.temp
  (:require [clojure.xml :as xml])
  (:import  [java.io ByteArrayInputStream]))

(def instr "<record><parent><name>Kartik</name><age>35</age><children><child><name>Prisha</name><age>3</age></child></children></parent></record>")

(defrecord parent [name age children])
(defrecord child [name age])


(defn parse-parent [x]
  println x)

(defn get-content [x]
  (first (:content x)))

(defn get-tag [x]
  (:tag x))

(defn parse-xml [rss]
  "Parses given XML URL"
  (let [rss-data (ByteArrayInputStream. (.getBytes rss "UTF-8"))]
  (for [x (xml-seq (xml/parse rss-data))
        :when (= :record (:tag x))]
    (get-content x))))

(def parent-content (parse-xml instr))

;(map first (map :content (first (map :content (flatten parent-content)))))

(defn test-fn [x]
  (if (= x 1)
    [x x]
    [x (test-fn (dec x))]))

(defn test-zip [x]
  (println x)
    (loop [tag (get-tag x) cnt (get-content x) acc []]
      (println tag cnt)
    (if (instance? java.lang.String cnt)
      (conj acc [tag cnt])
      (conj acc [tag (test-zip (get-content cnt))]))))


;({:tag :parent, :attrs nil, :content [{:tag :name, :attrs nil, :content ["Kartik"]} {:tag :age, :attrs nil, :content ["35"]} {:tag :children, :attrs nil, :content [{:tag :child, :attrs nil, :content [{:tag :name, :attrs nil, :content ["Prisha"]} {:tag :age, :attrs nil, :content ["3"]}]}]}]})
;rss.temp=> (map get-content (parse-xml instr))
;({:tag :name, :attrs nil, :content ["Kartik"]})
;rss.temp=> (map get-content (parse-xml instr))
;({:tag :name, :attrs nil, :content ["Kartik"]})
;rss.temp=> (map get-content (map get-content (parse-xml instr)))