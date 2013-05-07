#!/usr/bin/env python

import sys, getopt
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.patches as patches
from matplotlib.patches import Polygon
from pylab import *

cdict = { '0' : 'y',
          '1' : 'g',
          '2' : 'b',
          '3' : 'r',
          '4' : 'c'
          }

def plot_points(fig):
    ax = fig.add_subplot(122)
    ax1 = fig.add_subplot(121)
    n = int(sys.stdin.readline())
    x = []
    y = []
    color = []

    for line in range(0,n):
        line = sys.stdin.readline()
        data = line.split()
        x.append(float(data[0]))
        y.append(float(data[1]))
        color.append(cdict[data[2]])

    ax.scatter(x,y,c=color)
    ax1.scatter(x,y,c=color)

    return

def plot_triangles(fig):
    n = sys.stdin.readline()
    ax = fig.add_subplot(122)
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

def main(argv):
    try:
        opts, args = getopt.getopt(argv,"pta")
    except getopt.GetoptError:
        sys.exit(2)

    fig = plt.figure()
    for opt, arg in opts:
        if opt == '-p':
            plot_points(fig)
        elif opt == '-t':
            plot_triangles(fig)
        elif opt == '-a':
            plot_all()
    plt.show()
    sys.exit()


if __name__ == "__main__":
   main(sys.argv[1:])
