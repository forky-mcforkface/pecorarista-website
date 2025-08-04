pandoc \
    --from=markdown \
    --to=latex \
    --template=vocabulary.tex \
    --lua-filter=filter.lua \
    --standalone \
    --output=words.tex \
    words.md
latexmk words.tex
