When playing around with generating pretty things based on tiles, I've come to realize I need something to help me reason about tiles outside of the Processing IDE. In steps Tilenol :)

## Features

### Transition-alphabet

Clients are able to provide a theoretically unlimited set of possible transitions, where a tile connects with its neighbour if they have the same transition-character.

A transition-character can be any Java Object. Tilenol will only care about reference-equality when checking if tiles fit eachother. This way, clients may choose to embed logic or data (or both) into the transitions.

If a client does not provide a transition-alphabet, one will be provided. This default alphabet only contains two characters, connected and unconnected.

Any alphabet with only two characters is considered a "connective" alphabet. Some generation-algorithm will only work with connective alphabets.

### Border-rules

Clients are able to select rules for how to treat the edges of the board.

#### Wrapping

The board may wrap north-south and/or east-west, or it may not wrap at all. If the board wraps, then normal transition rules are used across the wrap. If the board does not wrap, border rules are used instead at the edges of the board.

#### Legal borders

Clients can provide a set of legal border-transitions, i.e. what transitions are considered matches when a tile points outside the board. If no transitions are provided, the board will assume that all transitions are allowed. The list of legal transitions must be a sub-set of the transition-alphabet.

### Navigator

The board can present clients with a Navigator that can help with figuring out legal moves around the board.

## Future features

**Three dimensions** A board can have depth, and a tile will have up/down connections.

**Flooding** A client should be able to ask the board to get every tile connected to a particular position; the client may have to provide a dictionary of legal transitions to be able to expand what connected means.

**Transition-weights** The transition-alphabet can come with a list of weights, which will alter the frequency of the use of transitions. For example, if you have a three-letter alphabet and a weight-list looking like [1, 1, 2], then the third letter will be used roughly as often as the two others combined.

**Exploration** Pick a tile and a set of legal transitions. An agent will start walking between tiles, only following the legal transitions, until it has visited all reachable tiles. Returns an ordered list of tiles that represents the path the agent took. This path will probably contain duplicates.

**Reachability** Pick two tiles and a set of legal transitions. Returns true iff it is possible to reach one tile from another.

**Cost of movement** Alongside the list of transitions, a client may also supply a list of transition-costs.

