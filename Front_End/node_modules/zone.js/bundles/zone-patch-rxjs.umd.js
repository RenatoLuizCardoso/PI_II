'use strict';
/**
 * @license Angular v<unknown>
 * (c) 2010-2024 Google LLC. https://angular.io/
 * License: MIT
 */
(function (global, factory) {
    typeof exports === 'object' && typeof module !== 'undefined' ? factory(require('./rxjs')) :
        typeof define === 'function' && define.amd ? define(['./rxjs'], factory) :
            (global = typeof globalThis !== 'undefined' ? globalThis : global || self, factory(global.rxjs));
})(this, (function (rxjs) {
    'use strict';
    rxjs.patchRxJs(Zone);
}));
