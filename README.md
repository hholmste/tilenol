When playing around with generating pretty things based on tiles, I've come to realize I need something to help me reason about tiles outside of the Processing IDE. In steps Tilenol :)

Future features
===============

Transition-alphabet. Currently tiles are either connected or not. Instead, clients should be able to provide a practically unlimited set of possible transitions, where a tile connects with its neighbour if they have the same transition-character.

Board-border-rules. A client should be able to say how border-tiles behave. Possible behaviour: Borders are assumed to contain further tiles, so border-tiles can be whatever they want. Borders are assumed to be closed, so border-tiles can only have null-connections pointing out of the board (when transition-alphabets are implemented, we need some way of marking a particular transition-character as the null-connector). Borders are circular, so border-tiles must connect to the matching tile on the other side of the board; this could be north-south, east-west, or both axes.

Three dimensions. A board can have depth, and a tile will have up/down connections.

Flooding. A client should be able to ask the board to get every tile connected to a particular position; the client may have to provide a dictionary of legal transitions to be able to expand what connected means.

