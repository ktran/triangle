import sys
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.patches import Polygon

n = int(sys.stdin.readline())
x = []
y = []
color = []

cdict = { '0' : 'y',
          '1' : 'g',
          '2' : 'b',
          '3' : 'r',
          '4' : 'c'
          }

for line in range(0,n):
    line = sys.stdin.readline()
    data = line.split()
    x.append(float(data[0]))
    y.append(float(data[1]))
    color.append(cdict[data[2]])

plt.scatter(x,y,c=color)
plt.show()





