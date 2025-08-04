curl -X POST \
    --header "Content-Type: application/json"  \
    "http://localhost:8080/documents" \
    --data '{"uri": "http://example.com/article/123", "title": "更新"}'
