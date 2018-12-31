[![Build Status](https://travis-ci.org/kellyi/reagent-leaflet-sandbox.svg?branch=master)](https://travis-ci.org/kellyi/reagent-leaflet-sandbox)

# reagent-leaflet-sandbox

Sandbox for trying out ClojureScript, Reagent & Leaflet.

## Requirements

- Leiningen

## Setup

```sh
lein deps
```

## Development

To start a server & REPL run:

```sh
lein figwheel
```

This will start a server on port `3449`.

### CIDER

To start the server & a CIDER REPL from within Emacs, open one of the project's
`cljs` files in a buffer then run:

```emacs
cider-jack-in-cljs
```

This will also start a server on port `3449`.

Note that you can use either `lein figwheel` or `cider-jack-in-cljs` but not
both.

## Tests

```sh
lein lint
```

## Distribution

```sh
lein package
```
