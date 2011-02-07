# RSS Feeder

## Usage

### Help on the available commands
`java -jar rss-filter.jar -h`

### Filter rss feed
`java -jar rss-filter.jar -k <URL> <keyword>`


## Build project
`lein uberjar rss-filter.jar`

##Examples
`java -jar rss-filter.jar -k "http://feeds.delicious.com/v2/rss/popular/programming" "Programming"
