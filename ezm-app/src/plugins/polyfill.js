/**
 * @description scrollTo/scrollBy的polyfill，IE9+
 * @author zhangxinxu(.com)
 * @created 2019-07-27
 */

if (!window.scrollTo) {
    window.scrollTo = function (x, y) {
        window.pageXOffset = x;
        window.pageYOffset = y;
    };
}
if (!window.scrollBy) {
    window.scrollBy = function (x, y) {
        window.pageXOffset += x;
        window.pageYOffset += y;
    };
}
if (!document.body.scrollTo) {
    Element.prototype.scrollTo = function (x, y) {
        this.scrollLeft = x;
        this.scrollTop = y;
    };
}
if (!document.body.scrollBy) {
    Element.prototype.scrollBy = function (x, y) {
        this.scrollLeft += x;
        this.scrollTop += y;
    };
}