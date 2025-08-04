const path = require('path');
const escape = require('escape-html');
const { readFile, glob } = require('node:fs/promises');
const { codeToHtml } = require('shiki');
const { HtmlValidate } = require('html-validate');

module.exports = function(eleventyConfig) {

  eleventyConfig.addGlobalData('permalink', () => {
    return (data) => {
      let filename = path.parse(data.page.inputPath).base;
      return filename.startsWith('_') ? false : `${data.page.filePathStem}.${data.page.outputFileExtension}`;
    };
  });

  const theme = 'github-dark-high-contrast';
  eleventyConfig.addAsyncShortcode('highlight', async (code, language) => {
    return await codeToHtml(code, {
      lang: language,
      theme: theme
    });
  });

  eleventyConfig.addAsyncShortcode('includeSourceCode', async (filename, language) => {
    const code = await readFile(filename, 'utf-8');
    const escaped = escape(code);
    return await codeToHtml(escaped, {
      lang: language,
      theme: theme
    });
  });

  eleventyConfig.on('afterBuild', async () => {
    const configData = await readFile('.htmlvalidate.json', 'utf-8');
    const config = JSON.parse(configData);
    const validator = new HtmlValidate(config);
    for await (const filename of glob('dist/**/*.html')) {
      const html = await readFile(filename, 'utf-8');
      const report = await validator.validateString(html, filename);

      if (!report.valid) {
        for (const result of report.results) {
          const lines = (result.source ?? '').split('\n');
          for (const message of result.messages) {
            if (message.severity >= 1) {
              type = message.severity == 2 ? '\x1b[31m[error]\x1b[0m' : '\x1b[33m[warn]\x1b[0m';
              s = `
                ${type}
                ${filename}
                line ${message.line}
                (${message.ruleId})
                ${message.message}
                ${lines[message.line - 1]}
              `
              .trim()
              .replace(/(\s+)|\n/g, ' ');
              console.log(s);
            }
          }
        }
        process.exit(1);
      }
    }
  });

  return {
    templateFormats: ['njk'],
    htmlTemplateEngine: 'njk',
    dataTemplateEngine: 'njk',
    dir: {
      input: 'nunjucks',
      output: 'dist'
    }
  };

};
