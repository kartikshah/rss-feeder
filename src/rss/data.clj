(ns rss.data
  (:require [clojure.xml :as xml])
  (:import  [java.io ByteArrayInputStream]))

(defrecord rss-root [channels])
(defrecord rss-channel [title description link lastbuilddate pubdate items])
(defrecord rss-item [title description link guid pubdate])

(defn create-rss-channel [map items]
  (let [title (:title map)
        pubDate (:pubDate map)
        description (:description map)
        link (:link map)
        lastBuildDate (:lastBuildDate map)]
    (rss-channel. title description link lastBuildDate pubDate items)))

(defn create-item [item-data]
  (let [description (:description item-data)
        pubDate (:pubDate item-data)
        guid (:guid item-data)
        link (:link item-data)
        title (:title item-data)
        ]
    (rss-item. title description link guid pubDate)))

(defn parse-item [item-data]
  (create-item (zipmap (map :tag item-data) (map :content item-data))))

(defn parse-items [items-data]
  (map parse-item items-data))

(defn parse-channel [channel-content]
  (loop [x (:content channel-content)]
    (create-rss-channel
      (into {} (filter #(not (= :item (key %))) (zipmap (map :tag x) (map :content x))))
      (parse-items (map :content (into [] (filter #(= :item (:tag %)) x)))))
      ))

(defn parse-rss [rss]
  (let [rss-data (ByteArrayInputStream. (.getBytes rss "UTF-8"))]
  (for [x (xml-seq (xml/parse rss-data))
        :when (= :rss (:tag x))]
    (map parse-channel (:content x)))))

