(ns rss.print)

(defn print-rss-item [rss-items fields]
  (println (map #(select-keys % fields) rss-items)))