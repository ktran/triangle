#!/usr/bin/env python
import argparse
import sys
import numpy
import random

directory='/home/ktran/IdeaProjects/triangle/testdata/'
filename='data'

def main(argv):

    parser = argparse.ArgumentParser(description='Generates test instances.')
    parser.add_argument('size', metavar='S', type=int, default=30,
                        help='Size of the problem instance.')
    parser.add_argument('-l', '--lower', metavar='L', type=int, 
                        default=10, help='Lower bound of coordinate.')
    parser.add_argument('-u', '--upper', metavar='U', type=int, 
                        default=0, help='Upper bound of coordinate.')
    parser.add_argument('-n', '--number', metavar='N', type=int, 
                        default=1,help='Number of instances to generate.')
    parser.add_argument('-p', '--prefix', metavar='P', type=str, 
                        help='Prefix of file to store instance in.')
    args = parser.parse_args()
    
    for i in range(0,args.number):
        generated = generate(args.size, args.lower, args.upper, 4)
        writeToFile(directory + filename + str(i), generated)

def writeToFile(filename, data):
    f = open(filename, 'w')
    f.write(str(len(data)) + '\n')
    for coordinate in data:
        f.write(str(coordinate[0]) + ' ' + str(coordinate[1]) + ' '
                + str(coordinate[2]) + '\n')
    
def generate(size, lower, upper, colors):
    data = []

    for i in range(0, size):
        x = (round(random.uniform(lower, upper), 6))
        y = (round(random.uniform(lower, upper), 6))
        c = (random.randint(0,colors))
        data.append((x,y,c))

    return data
    

if __name__ == "__main__":
   main(sys.argv[1:])
