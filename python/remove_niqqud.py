import re
from pathlib import Path


def main() -> None:
    pattern = re.compile(r'[\u05b0-\u05bc\u05c1\u05c2\u0027\u0022]')
    for i in [2]:
        with (
            Path(f'original{i}').open(mode='r') as r,
            Path(f'../nunjucks/fictional-works/_include/zohar/body{i}.html').open(mode='w') as w
        ):
            text = r.read()
            plain = re.sub(pattern, '', text)
            lines = []
            line = ''
            for ch in plain:
                if ch in [' ', ',', '.']:
                    if len(line) > 0 and line[-1] != ' ':
                        line = line + ' '
                    if ch in [',', '.']:
                        lines.append(line.strip())
                        line = ''
                else:
                    line = line + f'&#{ord(ch)};'
            for line in lines:
                w.write(line + '\n')


if __name__ == '__main__':
    main()
