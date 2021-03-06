(ns rss.data
  (:require [clojure.xml :as xml])
  (:import  [java.io ByteArrayInputStream]))

(defrecord RssChannel [title description link last-build-date pub-date items])
(defrecord RssItem [title description link guid pub-date])

(defn create-rss-channel [{:keys [title pubDate description link lastBuildDate]} items]
  (RssChannel. title description link lastBuildDate pubDate items))

(defn create-item [{:keys [title description link guid pubDate]}]
    (RssItem. title description link guid pubDate))

(defn zipmap-tag-content [item]
  (zipmap (map :tag item) (map :content item)))

(defn parse-item [item-data]
  (create-item (zipmap-tag-content item-data)))

(defn parse-items [items-data]
  (map parse-item items-data))

(defn parse-channel [channel-content]
  (loop [x (:content channel-content)]
    (create-rss-channel
      (filter #(not= :item (key %)) (zipmap-tag-content x))
      (parse-items (map :content (filter #(= :item (:tag %)) x))))
      ))

; Parses RSS Feed
(defn parse-rss [rss]
  "Parses given RSS URL"
  (let [rss-data (ByteArrayInputStream. (.getBytes rss "UTF-8"))]
  (for [x (xml-seq (xml/parse rss-data))
        :when (= :rss (:tag x))]
    (map parse-channel (:content x)))))


