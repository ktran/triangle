import sys
import numpy as np
import matplotlib
import matplotlib.pyplot as plt
import matplotlib.patches as patches
from matplotlib.collections import PatchCollection

n = sys.stdin.readline()
fig = plt.figure()
ax = fig.add_subplot(111)

cdict = { '0' : 'y',
          '1' : 'g',
          '2' : 'b',
          '3' : 'r',
          '4' : 'c'
          }

for line in range(0,int(n)):
    line = sys.stdin.readline()
    data = line.split()

    poly = patches.Polygon([[float(data[0]),float(data[1])], 
                    [float(data[2]), float(data[3])], 
                    [float(data[4]), float(data[5])]],
                    closed=True, color=cdict[data[6]])
    ax.add_patch(poly)

ax.set_xlim(0,10) 
ax.set_ylim(0,10) 
plt.show()




