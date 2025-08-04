import itertools

import numpy
import seaborn
from matplotlib import pyplot


def main() -> None:
    D = 128
    T = 51
    data = numpy.zeros((T, D), dtype=float)
    for (t, d) in itertools.product(range(T), range(D)):
        f = numpy.sin if d % 2 == 0 else numpy.cos
        omega = 1 / (10_000 ** (2 * numpy.floor(d / 2) / D))
        data[t, d] = f(omega * t)
    h = seaborn.heatmap(
        data.T,
        vmin=-1.0,
        cbar_kws={'ticks': [-1.0, -0.5, 0.0, 0.5, 1.0]}
    )
    h.set_xlabel('t (position)')
    h.set_ylabel('i (dimension)')
    xticklabels = [t for t in range(T) if t % 10 == 0]
    h.set_xticks([x + 0.5 for x in xticklabels])
    h.set_xticklabels(xticklabels)
    yticklabels = [1] + [y for y in range(D + 1) if y % 16 == 0 and y > 0]
    h.set_yticks([y - 0.5 for y in yticklabels])
    h.set_yticklabels(yticklabels)
    pyplot.savefig('../images/heatmap.svg', bbox_inches='tight', transparent=True)
    pyplot.show()


if __name__ == '__main__':
    main()
