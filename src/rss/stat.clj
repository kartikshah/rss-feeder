(ns rss.stat)


; -> rss-item.get-title-in-list -> filter-words -> group-words -> count-words -> sort-words
; -> keywords -> find-in-title

(def keywords ["Clojure" "Groovy" "Python"])

(defn find-items [rss-item-list keyword]
  (filter #(.contains (first (:title %)) keyword) (flatten rss-item-list)))

(defn find-items-keywords [rss-item-list keyword-list]
  (flatten (map #(find-items rss-item-list %) keyword-list)))

; Find RSS Item by Keywords
; Find RSS Items by predicate
; RSS-ITEM-COLL.FindByPredicate(Predicate)
; (find Predicate RSS-ITEM-COLL)

; return-map-entry <- map.findByValue(key keyword-in-value)


