curl -X POST \
    --header "Content-Type: application/json"
    "http://localhost:9200/_bulk" \
    --data @data.jsonl
