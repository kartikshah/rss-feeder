(ns rss.stat)

(def keywords ["Clojure" "Groovy" "Python"])

(defn find-items [rss-item-list keyword]
  (filter #(.contains (first (:title %)) keyword) (flatten rss-item-list)))

(defn find-items-keywords [rss-item-list keyword-list]
  (flatten (map #(find-items rss-item-list %) keyword-list)))




