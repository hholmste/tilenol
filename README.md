When playing around with generating pretty things based on tiles, I've come to realize I need something to help me reason about tiles outside of the Processing IDE. In steps Tilenol :)

## Features

### Transition-alphabet

Clients are able to provide a theoretically unlimited set of possible transitions, where a tile connects with its neighbour if they have the same transition-character.

A transition-character can be any Java Object. Tilenol will only care about reference-equality when checking if tiles fit eachother. This way, clients may choose to embed logic or data (or both) into the transitions.

If a client does not provide a transition-alphabet, one will be provided. This default alphabet only contains two characters, connected and unconnected.

Any alphabet with only two characters is considered a "connective" alphabet. Some generation-algorithm will only work with connective alphabets.


## Future features

**Board-border-rules** A client should be able to say how border-tiles behave. Possible behaviour: Borders are assumed to contain further tiles, so border-tiles can be whatever they want. Borders are assumed to be closed, so border-tiles can only have null-connections pointing out of the board (when transition-alphabets are implemented, we need some way of marking a particular transition-character as the null-connector). Borders are circular, so border-tiles must connect to the matching tile on the other side of the board; this could be north-south, east-west, or both axes.

**Three dimensions** A board can have depth, and a tile will have up/down connections.

**Flooding** A client should be able to ask the board to get every tile connected to a particular position; the client may have to provide a dictionary of legal transitions to be able to expand what connected means.

**Transition-weights** The transition-alphabet can come with a list of weights, which will alter the frequency of the use of transitions. For example, if you have a three-letter alphabet and a weight-list looking like [1, 1, 2], then the third letter will be used roughly as often as the two others combined.

