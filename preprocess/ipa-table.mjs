import fs from 'fs';

let rows = [];
let symbols = [];
JSON.parse(fs.readFileSync('data/phonetic-alphabets.json', 'utf-8')).alphabets.forEach(alphabet => {
  symbols.push(alphabet.symbol);
  rows.push(
    `<tr>
      <td class="ipa">
        ${alphabet.symbol}
      </td>
      <td>
        ${alphabet.name}
      </td>
      <td>
        ${alphabet.vim.length > 0 ? '<kbd>' + alphabet.vim.join("</kbd> <kbd>") + '</kbd>' : ''}
      </td>
      <td class="monospace">
        ${alphabet.tipa}
      </td>
      <td>
          ${alphabet.compose.length > 0 ? '<kbd>' + alphabet.compose.join("</kbd> <kbd>") + '</kbd>' : ''}
      </td>
      <td>
        ${alphabet.xsampa.length > 0 ? '<kbd>' + alphabet.xsampa.join("</kbd> <kbd>") + '</kbd>' : ''}
      </td>
    </tr>`
  );
});

const table = `
  <table class="table table-bordered table-striped">
    <thead>
      <tr>
        <th>Symbol</th><th>Name</th><th>Vim</th><th>TIPA</th><th>Compose</th><th>X-SAMPA</th>
      </tr>
    </thead>
  `
  + rows.join('')
  + '</table>'
fs.writeFileSync(`nunjucks/posts/_include/_ipa-table.njk`, table);
