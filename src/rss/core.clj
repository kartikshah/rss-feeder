(ns rss.core
  (:gen-class)
  (:require [rss.stat :as stat])
  (:require [rss.data :as data])
  (:require [rss.feed :as feed])
  (:require [rss.print :as print])
  (:use [clojure.contrib command-line]))

; Utility function to wrap output
(defn execute-command [func display-fields & arguments]
  (print/print-rss-item (apply func (map str (first arguments))) display-fields))

(defn get-rss-items [url]
  (flatten (data/parse-rss (feed/get-feed url))))

; wrapper functions for each command line option
(defn filter-by-keyword-wrapper [url kw]
  (let [rss-item-list (get-rss-items url)]
    (stat/find-items (map :items rss-item-list)
                    kw)))

(defn filter-by-keywordlist-wrapper [url kw-list]
  (let [rss-item-list (get-rss-items url)]
    (stat/find-items-keywords (map :items rss-item-list))
                            kw-list))

; Main function for command line access
(defn -main [& args]
  (let [fields [:link :title]]
    (with-command-line args
      "Usage: rss-filter [-k | -ks ] "
      [[filter-by-keyword? k? "<URL> <Keyword> Filter RSS feed by given keyword" false]
       ;[filter-by-keywordlist? ks? "<URL> <[keyword list]> Filter RSS feed by give keyword list" false]
       arguments]
      (let [switch-fn #(cond
                         filter-by-keyword? (execute-command filter-by-keyword-wrapper fields arguments)
                         ;filter-by-keywordlist? (execute-command find-items-keywords fields arguments)
                         :else (println "Invalid Usage: java -jar rss-filter -h"))]
           (switch-fn)))))




