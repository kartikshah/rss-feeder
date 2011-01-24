(ns rss.feed
  (:require [clj-http.client :as client]))

(defn get-feed [url]
  (:body (client/get url)))

(defn get-feeds [urllist]
  (pmap get-feed urllist))

;(str "http://feeds.delicious.com/v2/rss/popular/" tag)
;http://feeds.delicious.com/v2/rss/popular/programming
