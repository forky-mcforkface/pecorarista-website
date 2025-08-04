.PHONY: release clean

DIST := dist
IMAGES := $(addprefix $(DIST)/static/images/, $(notdir $(wildcard images/*)))
VIDEOS := $(addprefix $(DIST)/static/videos/, $(notdir $(wildcard videos/*)))
DOCS := $(addprefix $(DIST)/static/docs/, $(notdir $(wildcard docs/*)))
VECTOR_IMAGES := $(addprefix $(DIST)/static/images/, $(notdir $(wildcard latex/*.svg)))
FONTS := $(addprefix $(DIST)/static/fonts/, $(notdir $(wildcard fonts/*)))
POST_SOURCES := $(wildcard nunjucks/posts/*.njk)
POST_TARGETS := $(addprefix $(DIST)/posts/, $(notdir $(POST_SOURCES:.njk=.html)))
MAIN_PAGE_SOURCES := $(wildcard nunjucks/*.njk)
MAIN_PAGE_TARGETS := $(addprefix $(DIST)/, $(notdir $(MAIN_PAGE_SOURCES:.njk=.html)))

release: $(DIST)/CNAME \
	$(DIST)/favicon.ico \
	$(IMAGES) \
	$(VIDEOS) \
	$(DOCS) \
	$(VECTOR_IMAGES) \
	$(FONTS) \
	$(DIST)/static/css/main.css \
	$(DIST)/static/js/mathjax.js \
	$(DIST)/static/js/main.js \
	$(MAIN_PAGE_TARGETS) \
	$(POST_PAGE_TARGETS)

$(DIST)/CNAME: CNAME
	@mkdir -p $(@D)
	@cp $< $@

$(DIST)/favicon.ico: favicon/favicon.ico
	@mkdir -p $(@D)
	@cp $< $@

$(DIST)/static/images/%: images/%
	@mkdir -p $(@D)
	@cp $< $@

$(DIST)/static/images/%.svg: latex/%.svg
	@mkdir -p $(@D)
	@cp $< $@

$(DIST)/static/videos/%: videos/%
	@mkdir -p $(@D)
	@cp $< $@

$(DIST)/static/docs/%: docs/%
	@mkdir -p $(@D)
	@cp $< $@

$(DIST)/static/fonts/%: fonts/%
	@mkdir -p $(@D)
	@cp $< $@

$(DIST)/static/js/%: javascripts/%
	@mkdir -p $(@D)
	@cp $< $@

$(DIST)/static/css/main.css: scss/main.scss scss/_lang.scss scss/_lists.scss scss/_navbar.scss scss/_toc.scss scss/_variables.scss scss/_collapsible.scss
	@mkdir -p $(@D)
	@npx stylelint 'scss/*.scss'
	@npx sass $< $@

nunjucks/posts/_include/_ipa-table.njk: data/phonetic-alphabets.json
	@npx node preprocess/ipa-table.mjs

$(MAIN_PAGE_TARGETS) $(POST_TARGETS): $(MAIN_PAGE_SOURCES) $(POST_SOURCES)
	@npx @11ty/eleventy > /dev/null

clean:
	@rm -rf $(DIST)
