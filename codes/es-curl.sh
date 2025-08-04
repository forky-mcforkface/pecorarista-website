curl -X PUT \
    --header 'Content-Type: application/json'  \
    'http://localhost:9200/documents' \
    --data @index.json
